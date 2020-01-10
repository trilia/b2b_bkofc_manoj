package com.olp.jpa.domain.docu.logist.repo;

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
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;

@Service("serviceCategorySvc")
public class ServiceCategoryServiceImpl extends AbstractServiceImpl<ServiceCategoryEntity, Long>
		implements ServiceCategoryService {

	@Autowired
	@Qualifier("serviceCategoryRepository")
	private ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	@Qualifier("serviceClassRepository")
	private ServiceClassRepository serviceClassRepository;

	@Autowired
	@Qualifier("logisticsCostService")
	private LogisticsCostService logisticsCostService;

	@Override
	protected JpaRepository<ServiceCategoryEntity, Long> getRepository() {
		return serviceCategoryRepository;
	}

	@Override
	protected ITextRepository<ServiceCategoryEntity, Long> getTextRepository() {
		return serviceCategoryRepository;
	}

	@Override
	protected String getAlternateKeyAsString(ServiceCategoryEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ServiceArea srcSvcClassCode\" : \"").append(entity.getSrcSvcClassCode()).append("\" }");
		buff.append("{ \"ServiceArea destSvcClassCode\" : \"").append(entity.getDestSvcClassCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	public List<ServiceCategoryEntity> findBySrcSvcClassCode(String svcClassCode) {
		return serviceCategoryRepository.findBySrcSvcClassCode(svcClassCode);
	}

	@Override
	public List<ServiceCategoryEntity> findByDestSvcClassCode(String svcClassCode) {
		return serviceCategoryRepository.findByDestSvcClassCode(svcClassCode);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(ServiceCategoryEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {

			if (entity.getDestSvcClassRef() != null) {
				// it is possible to have destination sc null
				ServiceClassEntity sc = entity.getDestSvcClassRef(), sc2 = null;

				if (sc.getId() == null) {
					try {
						sc2 = serviceClassRepository.findBySvcClassCode(sc.getSvcClassCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find ServiceClass with code - " + sc.getSvcClassCode());
					}
				} else {
					try {
						sc2 = serviceClassRepository.findOne(sc.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find ServiceClass with id - " + sc.getId());
					}
				}

				if (sc2 == null)
					throw new EntityValidationException("Could not find ServiceClass using either code or id !");

				entity.setDestSvcClassRef(sc2);
				entity.setDestSvcClassCode(sc2.getSvcClassCode());
			}

			if (entity.getSrcSvcClassRef() != null) {
				// it is possible to have src sc null
				ServiceClassEntity sc = entity.getSrcSvcClassRef(), sc2 = null;

				if (sc.getId() == null) {
					try {
						sc2 = serviceClassRepository.findBySvcClassCode(sc.getSvcClassCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find ServiceClass with code - " + sc.getSvcClassCode());
					}
				} else {
					try {
						sc2 = serviceClassRepository.findOne(sc.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find ServiceClass with id - " + sc.getId());
					}
				}

				if (sc2 == null)
					throw new EntityValidationException("Could not find ServiceClass using either code or id !");

				entity.setSrcSvcClassRef(sc2);
				entity.setSrcSvcClassCode(sc2.getSvcClassCode());
			}
		}

		List<LogisticsCostEntity> logisticsCosts = entity.getCostEstimate();
		if (logisticsCosts != null && !logisticsCosts.isEmpty()) {
			for (LogisticsCostEntity logisticsCost : logisticsCosts) {
				logisticsCost.setSvcCatgRef(entity);
				logisticsCostService.validate(logisticsCost, false, type);
			}
		}

	}

	@Override
	protected ServiceCategoryEntity doUpdate(ServiceCategoryEntity neu, ServiceCategoryEntity old)
			throws EntityValidationException {

		List<Long> deletedLogisticsCost = new ArrayList<Long>();

		if (old.getCostEstimate() != null && !old.getCostEstimate().isEmpty()) {
			for (LogisticsCostEntity oldLogisticsCost : old.getCostEstimate()) {
				boolean found = false;
				if (neu.getCostEstimate() != null && !neu.getCostEstimate().isEmpty()) {
					for (LogisticsCostEntity newLogisticsCost : neu.getCostEstimate()) {
						if (Objects.equals(newLogisticsCost.getId(), oldLogisticsCost.getId())) {
							found = true;
							break;
						}
					} // end for new LogisticsCost
				}
				if (!found) {
					// logisticsCost deleted
					deletedLogisticsCost.add(oldLogisticsCost.getId());

				}
			}
		}

		boolean updated = false;
		if (!deletedLogisticsCost.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getCostEstimate() != null && !neu.getCostEstimate().isEmpty()) {
				updateLogisticsCosts(neu, old); // end for
												// neu.getCostEstimate
			} // end if neu.getCostEstimate != null

			if (!deletedLogisticsCost.isEmpty()) {
				// dissociate LogisticsCost
				deleteLogisticsCosts(old.getCostEstimate(), old, deletedLogisticsCost);
			} // end if deleteLogisticsCosts not empty
			old.setBothWaysFlag(neu.isBothWaysFlag());
			old.setDestSvcClassCode(neu.getDestSvcClassCode());
			old.setDestSvcClassRef(neu.getDestSvcClassRef());
			old.setEtaMaxHours(neu.getEtaMaxHours());
			old.setEtaMinHours(neu.getEtaMinHours());
			old.setPartnerId(neu.getPartnerId());
			old.setSrcSvcClassCode(neu.getSrcSvcClassCode());
			old.setSrcSvcClassRef(neu.getSrcSvcClassRef());
			// }
		}
		return (old);

	}

	@Override
	protected AbstractServiceImpl<ServiceCategoryEntity, Long>.Outcome postProcess(int opCode,
			ServiceCategoryEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<ServiceCategoryEntity, Long>.Outcome preProcess(int opCode,
			ServiceCategoryEntity entity) throws EntityValidationException {
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
			break;
		default:
			break;
		}

		return (result);
	}

	@Override
	public boolean checkForUpdate(ServiceCategoryEntity neu, ServiceCategoryEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getDestSvcClassCode(), old.getDestSvcClassCode())
				|| !Objects.equals(neu.getDestSvcClassRef(), old.getDestSvcClassRef())
				|| !Objects.equals(neu.getEtaMaxHours(), old.getEtaMaxHours())
				|| !Objects.equals(neu.getEtaMinHours(), old.getEtaMinHours())
				|| !Objects.equals(neu.getSrcSvcClassCode(), old.getSrcSvcClassCode())
				|| !Objects.equals(neu.getSrcSvcClassRef(), old.getSrcSvcClassRef())) {

			result = true;
			return (result);
		}

		if (neu.getCostEstimate() == null || neu.getCostEstimate().isEmpty()) {
			if (old.getCostEstimate() != null && !old.getCostEstimate().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getCostEstimate() == null || old.getCostEstimate().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getCostEstimate().size(), old.getCostEstimate().size())) {
					result = true;
					return (result);
				} else {
					result = checkForLogisticsCosts(neu.getCostEstimate(), old.getCostEstimate());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForLogisticsCosts(List<LogisticsCostEntity> newLogisticsCosts,
			List<LogisticsCostEntity> oldLogisticsCosts) {

		boolean result = false;
		Iterator<LogisticsCostEntity> oldLogisticsCostEntityIter = oldLogisticsCosts.iterator();
		Iterator<LogisticsCostEntity> newLogisticsCostEntityIter = newLogisticsCosts.iterator();
		LogisticsCostEntity oldLogisticsCost = null;
		LogisticsCostEntity newLogisticsCost = null;

		while (newLogisticsCostEntityIter.hasNext()) {
			newLogisticsCost = newLogisticsCostEntityIter.next();
			Long newEntityId = newLogisticsCost.getId();
			if (newEntityId != null) {
				while (oldLogisticsCostEntityIter.hasNext()) {
					oldLogisticsCost = oldLogisticsCostEntityIter.next();
					Long oldEntityId = oldLogisticsCost.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = logisticsCostService.checkForUpdate(newLogisticsCost, oldLogisticsCost);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = logisticsCostService.checkForUpdate(newLogisticsCost, oldLogisticsCost);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void updateLogisticsCosts(ServiceCategoryEntity neu, ServiceCategoryEntity old)
			throws EntityValidationException {
		for (LogisticsCostEntity newLogisticsCost : neu.getCostEstimate()) {
			LogisticsCostEntity oldLogisticsCost2 = null;
			boolean found = false;
			if (old.getCostEstimate() != null && !old.getCostEstimate().isEmpty()) {
				for (LogisticsCostEntity oldLogisticsCost : old.getCostEstimate()) {
					if (Objects.equals(newLogisticsCost.getId(), oldLogisticsCost.getId())) {
						oldLogisticsCost2 = oldLogisticsCost;
						found = true;
						break;
					}
				} // end for old.getCostEstimate
			} // end if old.getCostEstimate

			if (!found) {
				// new LogisticsCost being added
				newLogisticsCost.setSvcCatgRef(old);
				logisticsCostService.validate(newLogisticsCost, false, EntityVdationType.PRE_INSERT);
				old.getCostEstimate().add(newLogisticsCost);
			} else {
				boolean logisticsCostUpdated = logisticsCostService.checkForUpdate(newLogisticsCost, oldLogisticsCost2);
				if (logisticsCostUpdated) {
					logisticsCostService.update(newLogisticsCost);
				}
			}
		}
	}

	private void deleteLogisticsCosts(List<LogisticsCostEntity> logisticsCosts, ServiceCategoryEntity old,
			List<Long> deletedLogisticsCosts) {
		Iterator<LogisticsCostEntity> logisticsCostEntityIter = old.getCostEstimate().iterator();
		for (Long id : deletedLogisticsCosts) {
			boolean found = false;
			LogisticsCostEntity oldLogisticsCost = null;
			while (logisticsCostEntityIter.hasNext()) {
				oldLogisticsCost = logisticsCostEntityIter.next();
				if (Objects.equals(id, oldLogisticsCost.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getCostEstimate().remove(oldLogisticsCost);
				logisticsCostService.delete(id);
			}
		}
	}

	private void preProcessAdd(ServiceCategoryEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(ServiceCategoryEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext()) {
			throw new EntityValidationException("Cannot delete ServiceCategory. Only be TERMINATED.");
		}
	}

	@Override
	public ServiceCategoryEntity findBySvcClassCode(String svcClassCode) {
		return serviceCategoryRepository.findBySvcClassCode(svcClassCode);
	}

}
