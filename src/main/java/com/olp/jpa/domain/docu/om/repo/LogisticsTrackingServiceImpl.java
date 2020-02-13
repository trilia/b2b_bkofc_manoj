package com.olp.jpa.domain.docu.om.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("logisticsTrackingService")
public class LogisticsTrackingServiceImpl extends AbstractServiceImpl<LogisticsTrackingEntity, Long>
		implements LogisticsTrackingService {

	@Autowired
	@Qualifier("logisticsTrackingRepository")
	private LogisticsTrackingRepository logisticsTrackingRepository;

	@Autowired
	@Qualifier("customerRepository")
	private CustomerRepository customerRepository;

	@Autowired
	@Qualifier("salesOrderLineRepository")
	private SalesOrderLineRepository salesOrderLineRepository;

	@Autowired
	@Qualifier("logisticsRoutingService")
	private LogisticsRoutingService logisticsRoutingService;

	@Override
	protected JpaRepository<LogisticsTrackingEntity, Long> getRepository() {
		return logisticsTrackingRepository;
	}

	@Override
	protected ITextRepository<LogisticsTrackingEntity, Long> getTextRepository() {
		return logisticsTrackingRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LogisticsTrackingEntity findByTrackingCode(String trackingCode) {
		return logisticsTrackingRepository.findByTrackingCode(trackingCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LogisticsTrackingEntity findByOrderLine(String merchTenantId, String orderNumber, int partNumber,
			int lineNumber) {
		return logisticsTrackingRepository.findByOrderLine(merchTenantId, orderNumber, partNumber, lineNumber);
	}

	@Override
	protected String getAlternateKeyAsString(LogisticsTrackingEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"logisticstracking\" : \"").append(entity.getTrackingCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LogisticsTrackingEntity entity, EntityVdationType type) throws EntityValidationException {

		if (entity.getCustomerRef() != null) {
			// it is possible to have destination ce null
			CustomerEntity ce = entity.getCustomerRef(), ce2 = null;

			if (ce.getId() == null) {
				try {
					ce2 = customerRepository.findByCustomerCode(ce.getCustomerCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find Customer with code - " + ce.getCustomerCode());
				}
			} else {
				try {
					ce2 = customerRepository.findOne(ce.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find Customer with id - " + ce.getId());
				}
			}

			if (ce2 == null)
				throw new EntityValidationException("Could not find Customer using either code or id !");

			entity.setCustomerRef(ce2);
			entity.setCustomerCode(ce2.getCustomerCode());
		}

		if (entity.getOrderLineRef() != null) {
			// it is possible to have sol null
			SalesOrderLineEntity sol = entity.getOrderLineRef(), sol2 = null;

			if (sol.getId() == null) {
				try {
					sol2 = salesOrderLineRepository.findByOrderLineNumber(sol.getOrderNumber(), entity.getPartNumber(),
							entity.getLineNumber());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find salesOrderLine with orderNumber - " + sol.getOrderNumber());
				}
			} else {
				try {
					sol2 = salesOrderLineRepository.findOne(sol.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find salesOrderLine with id - " + sol.getId());
				}
			}

			if (sol2 == null)
				throw new EntityValidationException("Could not find salesOrderLine using either code or id !");

			entity.setOrderLineRef(sol2);
			entity.setOrderNumber(sol2.getOrderNumber());
			entity.setMerchTenantId(sol2.getTenantId());
		}

		List<LogisticsRoutingEntity> logisticsRoutings = entity.getLogistRoutings();
		if (logisticsRoutings != null && !logisticsRoutings.isEmpty()) {
			for (LogisticsRoutingEntity logisticsRouting : logisticsRoutings) {
				logisticsRouting.setLogistTrackingRef(entity);
				logisticsRouting.setTrackingCode(entity.getTrackingCode());
				logisticsRoutingService.validate(logisticsRouting, false, type);
			}
		}

		if (EntityVdationType.PRE_INSERT == type || EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected AbstractServiceImpl<LogisticsTrackingEntity, Long>.Outcome postProcess(int opCode,
			LogisticsTrackingEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
		case UPDATE:
		case UPDATE_BULK:
		case DELETE:
		case DELETE_BULK:
		default:
			break;
		}

		return (result);
	}

	@Override
	protected AbstractServiceImpl<LogisticsTrackingEntity, Long>.Outcome preProcess(int opCode,
			LogisticsTrackingEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
			preProcessAdd(entity);
			break;
		case UPDATE:
		case UPDATE_BULK:
			validate(entity, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
			break;
		default:
			break;
		}

		return (result);
	}

	@Override
	protected LogisticsTrackingEntity doUpdate(LogisticsTrackingEntity neu, LogisticsTrackingEntity old)
			throws EntityValidationException {

		List<Long> deletedLogisticsRouting = new ArrayList<Long>();

		if (old.getLogistRoutings() != null && !old.getLogistRoutings().isEmpty()) {
			for (LogisticsRoutingEntity oldLogisticsRouting : old.getLogistRoutings()) {
				boolean found = false;
				if (neu.getLogistRoutings() != null && !neu.getLogistRoutings().isEmpty()) {
					for (LogisticsRoutingEntity newLogisticsRouting : neu.getLogistRoutings()) {
						if (Objects.equals(newLogisticsRouting.getId(), oldLogisticsRouting.getId())) {
							found = true;
							break;
						}
					} // end for new logisticsRouting
				}
				if (!found) {
					// logisticsRouting deleted
					deletedLogisticsRouting.add(oldLogisticsRouting.getId());

				}
			}
		}

		boolean updated = false;
		if (!deletedLogisticsRouting.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getLogistRoutings() != null && !neu.getLogistRoutings().isEmpty()) {
				updateLogisticsRoutings(neu, old); // end for
													// neu.getLogisticsRoutings
			} // end if neu.getLogisticsRoutings != null

			if (!deletedLogisticsRouting.isEmpty()) {
				// dissociate LogisticsRouting
				deleteLogisticsRoutings(old.getLogistRoutings(), old, deletedLogisticsRouting);
			} // end if deleteLogisticsRoutings not empty
			old.setBaseShipCost(neu.getBaseShipCost());
			old.setCustomerCode(neu.getCustomerCode());
			old.setCustomerRef(neu.getCustomerRef());
			old.setLineNumber(neu.getLineNumber());
			old.setMerchTenantId(neu.getMerchTenantId());
			old.setNonOffsetTax(neu.getNonOffsetTax());
			old.setOffsetTax(neu.getOffsetTax());
			old.setOrderLineRef(neu.getOrderLineRef());
			old.setOrderNumber(neu.getOrderNumber());
			old.setPartNumber(neu.getPartNumber());
			old.setPostalCode(neu.getPostalCode());
			old.setPrimaryEmail(neu.getPrimaryEmail());
			old.setPrimaryPhone(neu.getPrimaryPhone());
			old.setScheduleStatus(neu.getScheduleStatus());
			old.setSecondaryEmail(neu.getSecondaryEmail());
			old.setSecondaryPhone(neu.getSecondaryPhone());
			old.setShipmentStatus(neu.getShipmentStatus());
			old.setShippingAddress(neu.getShippingAddress());
			old.setTrackingCode(neu.getTrackingCode());
			old.setTrackingStatus(neu.getTrackingStatus());
			JpaUtil.updateRevisionControl(old, true);
			// }
		}
		return (old);

	}

	private boolean checkForUpdate(LogisticsTrackingEntity neu, LogisticsTrackingEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getBaseShipCost(), old.getBaseShipCost())
				|| !Objects.equals(neu.getCustomerCode(), old.getCustomerCode())
				|| !Objects.equals(neu.getLineNumber(), old.getLineNumber())
				|| !Objects.equals(neu.getMerchTenantId(), old.getMerchTenantId())
				|| !Objects.equals(neu.getNonOffsetTax(), old.getNonOffsetTax())
				|| !Objects.equals(neu.getOffsetTax(), old.getOffsetTax())
				|| !Objects.equals(neu.getOrderNumber(), old.getOrderNumber())
				|| !Objects.equals(neu.getPartNumber(), old.getPartNumber())
				|| !Objects.equals(neu.getPostalCode(), old.getPostalCode())
				|| !Objects.equals(neu.getPrimaryEmail(), old.getPrimaryEmail())
				|| !Objects.equals(neu.getPrimaryPhone(), old.getPrimaryPhone())
				|| !Objects.equals(neu.getScheduleStatus(), old.getScheduleStatus())
				|| !Objects.equals(neu.getSecondaryPhone(), old.getSecondaryPhone())
				|| !Objects.equals(neu.getSecondaryEmail(), old.getSecondaryEmail())
				|| !Objects.equals(neu.getShipmentStatus(), old.getShipmentStatus())
				|| !Objects.equals(neu.getShippingAddress(), old.getShippingAddress())
				|| !Objects.equals(neu.getTrackingCode(), old.getTrackingCode())
				|| !Objects.equals(neu.getTrackingStatus(), old.getTrackingStatus())

		) {

			result = true;
			return (result);

		}

		if (neu.getLogistRoutings() == null || neu.getLogistRoutings().isEmpty()) {
			if (old.getLogistRoutings() != null && !old.getLogistRoutings().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getLogistRoutings() == null || old.getLogistRoutings().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getLogistRoutings().size(), old.getLogistRoutings().size())) {
					result = true;
					return (result);
				} else {
					result = checkForLogisticsRoutings(neu.getLogistRoutings(), old.getLogistRoutings());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForLogisticsRoutings(List<LogisticsRoutingEntity> newLogisticsRoutings,
			List<LogisticsRoutingEntity> oldLogisticsRoutings) {

		boolean result = false;
		Iterator<LogisticsRoutingEntity> oldLogisticsRoutingEntityIter = oldLogisticsRoutings.iterator();
		Iterator<LogisticsRoutingEntity> newLogisticsRoutingEntityIter = newLogisticsRoutings.iterator();
		LogisticsRoutingEntity oldLogisticsRouting = null;
		LogisticsRoutingEntity newLogisticsRouting = null;

		while (newLogisticsRoutingEntityIter.hasNext()) {
			newLogisticsRouting = newLogisticsRoutingEntityIter.next();
			Long newEntityId = newLogisticsRouting.getId();
			if (newEntityId != null) {
				while (oldLogisticsRoutingEntityIter.hasNext()) {
					oldLogisticsRouting = oldLogisticsRoutingEntityIter.next();
					Long oldEntityId = oldLogisticsRouting.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = logisticsRoutingService.checkForUpdate(newLogisticsRouting,
								oldLogisticsRouting);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = logisticsRoutingService.checkForUpdate(newLogisticsRouting, oldLogisticsRouting);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void updateLogisticsRoutings(LogisticsTrackingEntity neu, LogisticsTrackingEntity old)
			throws EntityValidationException {
		for (LogisticsRoutingEntity newLogisticsRouting : neu.getLogistRoutings()) {
			LogisticsRoutingEntity oldLogisticsRouting2 = null;
			boolean found = false;
			if (old.getLogistRoutings() != null && !old.getLogistRoutings().isEmpty()) {
				for (LogisticsRoutingEntity oldLogisticsRouting : old.getLogistRoutings()) {
					if (Objects.equals(newLogisticsRouting.getId(), oldLogisticsRouting.getId())) {
						oldLogisticsRouting2 = oldLogisticsRouting;
						found = true;
						break;
					}
				} // end for old.getLogistRoutings
			} // end if old.getLogistRoutings

			if (!found) {
				// new getLogistRoutings being added
				newLogisticsRouting.setLogistTrackingRef(old);
				logisticsRoutingService.validate(newLogisticsRouting, false, EntityVdationType.PRE_INSERT);
				old.getLogistRoutings().add(newLogisticsRouting);
			} else {
				boolean logisticsRoutingUpdated = logisticsRoutingService.checkForUpdate(newLogisticsRouting,
						oldLogisticsRouting2);
				if (logisticsRoutingUpdated) {
					logisticsRoutingService.update(newLogisticsRouting);
				}
			}
		}
	}

	private void deleteLogisticsRoutings(List<LogisticsRoutingEntity> logisticsRoutings, LogisticsTrackingEntity old,
			List<Long> deletedLogisticsRoutings) {
		Iterator<LogisticsRoutingEntity> logisticsCostEntityIter = old.getLogistRoutings().iterator();
		for (Long id : deletedLogisticsRoutings) {
			boolean found = false;
			LogisticsRoutingEntity oldLogisticsCost = null;
			while (logisticsCostEntityIter.hasNext()) {
				oldLogisticsCost = logisticsCostEntityIter.next();
				if (Objects.equals(id, oldLogisticsCost.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getLogistRoutings().remove(oldLogisticsCost);
				logisticsRoutingService.delete(id);
			}
		}
	}

	private void preProcessAdd(LogisticsTrackingEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(LogisticsTrackingEntity entity) throws EntityValidationException {
		/*
		 * if (!isPrivilegedContext()) throw new EntityValidationException(
		 * "Cannot delete LogisticsTracking when entity tracking code is " +
		 * entity.getTrackingCode());
		 */
	}
}
