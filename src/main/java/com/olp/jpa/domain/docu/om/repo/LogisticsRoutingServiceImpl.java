package com.olp.jpa.domain.docu.om.repo;

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
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.repo.LogisticPartnerRepository;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;
import com.olp.jpa.util.JpaUtil;

@Service("logisticsRoutingService")
public class LogisticsRoutingServiceImpl extends AbstractServiceImpl<LogisticsRoutingEntity, Long>
		implements LogisticsRoutingService {

	@Autowired
	@Qualifier("logisticsRoutingRepository")
	private LogisticsRoutingRepository logisticsRoutingRepository;

	@Autowired
	@Qualifier("logisticsTrackingRepository")
	private LogisticsTrackingRepository logisticsTrackingRepository;

	@Autowired
	@Qualifier("logisticPartnerRepository")
	private LogisticPartnerRepository logisticsPartnerRepository;

	@Override
	protected JpaRepository<LogisticsRoutingEntity, Long> getRepository() {
		return logisticsRoutingRepository;
	}

	@Override
	protected ITextRepository<LogisticsRoutingEntity, Long> getTextRepository() {
		return logisticsRoutingRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LogisticsRoutingEntity findByTrackingCode(String trackingCode, int routingSequence) {
		return logisticsRoutingRepository.findByTrackingCode(trackingCode, routingSequence);
	}

	@Override
	protected String getAlternateKeyAsString(LogisticsRoutingEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"logisticsRouting\" : \"").append(entity.getTrackingCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LogisticsRoutingEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getLogistTrackingRef() != null) {
				// it is possible to have destination lt null
				LogisticsTrackingEntity lt = entity.getLogistTrackingRef(), lt2 = null;

				if (lt.getId() == null) {
					try {
						lt2 = logisticsTrackingRepository.findByTrackingCode(lt.getTrackingCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find AccountCategory with code - " + lt.getTrackingCode());
					}
				} else {
					try {
						lt2 = logisticsTrackingRepository.findOne(lt.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find LogisticsTracking with id - " + lt.getId());
					}
				}

				if (lt2 == null)
					throw new EntityValidationException("Could not find LogisticsTracking using either code or id !");

				entity.setLogistTrackingRef(lt2);
				entity.setTrackingCode(lt2.getTrackingCode());
			}

			if (entity.getLogistPartnerRef() != null) {
				// it is possible to have destination lt null
				LogisticPartnerEntity lt = entity.getLogistPartnerRef(), lt2 = null;

				if (lt.getId() == null) {
					try {
						lt2 = logisticsPartnerRepository.findByPartnerCode(lt.getPartnerCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find logisticsPartner with code - " + lt.getPartnerCode());
					}
				} else {
					try {
						lt2 = logisticsPartnerRepository.findOne(lt.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find logisticsPartner with id - " + lt.getId());
					}
				}

				if (lt2 == null)
					throw new EntityValidationException("Could not find LogisticsTracking using either code or id !");

				entity.setLogistPartnerRef(lt2);
				entity.setLogistPartnerCode(lt2.getPartnerCode());
			}
		}
		if (EntityVdationType.PRE_INSERT == type || EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected LogisticsRoutingEntity doUpdate(LogisticsRoutingEntity neu, LogisticsRoutingEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getTrackingCode(), old.getTrackingCode()))
			throw new EntityValidationException("The Logistics Routing tracking code does not match, existing - "
					+ old.getTrackingCode() + " , new - " + neu.getTrackingCode());

		if (!Objects.equals(neu.getRoutingSequence(), old.getRoutingSequence())) {
			throw new EntityValidationException("The Logistics Routing sequence does not match, existing - "
					+ old.getRoutingSequence() + " , new - " + neu.getRoutingSequence());
		}

		if (checkForUpdate(neu, old)) {
			old.setRoutingSequence(neu.getRoutingSequence());
			old.setDestAddress(neu.getDestAddress());
			old.setDestPostalCode(neu.getDestPostalCode());
			old.setLogistPartnerCode(neu.getLogistPartnerCode());
			old.setLogistTrackingCode(neu.getLogistTrackingCode());
			old.setNonOffsetTax(neu.getNonOffsetTax());
			old.setRoutingSequence(neu.getRoutingSequence());
			old.setRoutingStatus(neu.getRoutingStatus());
			old.setScheduleStatus(neu.getScheduleStatus());
			old.setSourceAddress(neu.getSourceAddress());
			old.setSourceAddress(neu.getSourcePostalCode());
			old.setSourceAddress(neu.getTrackingCode());
			old.setLogistTrackingRef(neu.getLogistTrackingRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(LogisticsRoutingEntity neu, LogisticsRoutingEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getTrackingCode(), old.getTrackingCode())
				|| !Objects.equals(neu.getRoutingSequence(), old.getRoutingSequence())
				|| !Objects.equals(neu.getDestAddress(), old.getDestAddress())
				|| !Objects.equals(neu.getDestPostalCode(), old.getDestPostalCode())
				|| !Objects.equals(neu.getLogistPartnerCode(), old.getLogistPartnerCode())
				|| !Objects.equals(neu.getLogistTrackingCode(), old.getLogistTrackingCode())
				|| !Objects.equals(neu.getNonOffsetTax(), old.getNonOffsetTax())
				|| !Objects.equals(neu.getOffsetTax(), old.getOffsetTax())
				|| !Objects.equals(neu.getRoutingSequence(), old.getRoutingSequence())
				|| !Objects.equals(neu.getRoutingStatus(), old.getRoutingStatus())
				|| !Objects.equals(neu.getScheduleStatus(), old.getScheduleStatus())
				|| !Objects.equals(neu.getSourceAddress(), old.getSourceAddress())
				|| !Objects.equals(neu.getSourcePostalCode(), old.getSourcePostalCode())
				|| !Objects.equals(neu.getTrackingCode(), old.getTrackingCode())

		) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getLogistPartnerRef() == null) {
			if (old.getLogistPartnerRef() != null)
				result = true;
		} else {
			if (old.getLogistPartnerRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getLogistPartnerRef().getId(), old.getLogistPartnerRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LogisticsRoutingEntity, Long>.Outcome postProcess(int opCode,
			LogisticsRoutingEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<LogisticsRoutingEntity, Long>.Outcome preProcess(int opCode,
			LogisticsRoutingEntity entity) throws EntityValidationException {
		Outcome result = new Outcome();
		result.setResult(true);

		switch (opCode) {
		case ADD:
		case ADD_BULK:
			preProcessAdd(entity);
			break;
		case UPDATE:
		case UPDATE_BULK:
			validate(entity, true, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
		default:
			break;
		}

		return (result);
	}

	private void preProcessAdd(LogisticsRoutingEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(LogisticsRoutingEntity entity) throws EntityValidationException {
		/*
		 * if (entity.getLogistTrackingRef() != null) { LogisticsTrackingEntity
		 * logisticsTracking = entity.getLogistTrackingRef(); if
		 * (!isPrivilegedContext()) throw new EntityValidationException(
		 * "Cannot delete accountsubcategory when logisticsTracking is " +
		 * logisticsTracking.getTrackingCode()); }
		 */
	}
}
