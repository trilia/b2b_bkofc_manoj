package com.olp.jpa.domain.docu.logist.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceAreaEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;
import com.olp.jpa.util.JpaUtil;

@Service("serviceClassSvc")
public class ServiceClassServiceImpl extends AbstractServiceImpl<ServiceClassEntity, Long>
		implements ServiceClassService {

	@Autowired
	@Qualifier("serviceClassRepository")
	private ServiceClassRepository serviceClassRepository;

	@Autowired
	@Qualifier("serviceAreaRepository")
	private ServiceAreaRepository serviceAreaRepository;

	@Autowired
	@Qualifier("serviceCategoryRepository")
	private ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	@Qualifier("logisticsCostRepository")
	private LogisticsCostRepository logisticsCostRepository;

	@Autowired
	@Qualifier("serviceCategorySvc")
	private ServiceCategoryService serviceCategoryService;

	@Override
	protected JpaRepository<ServiceClassEntity, Long> getRepository() {
		return serviceClassRepository;
	}

	@Override
	protected ITextRepository<ServiceClassEntity, Long> getTextRepository() {
		return serviceClassRepository;
	}

	@Override
	public ServiceClassEntity findBySvcClassCode(String svcClassCode) {
		return serviceClassRepository.findBySvcClassCode(svcClassCode);
	}

	@Override
	protected String getAlternateKeyAsString(ServiceClassEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ServiceClass\" : \"").append(entity.getSvcClassCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(ServiceClassEntity entity, EntityVdationType type) throws EntityValidationException {
		if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	@Transactional
	public Long requestStatusChange(String svcClassCode, LifeCycleStatus status) throws EntityValidationException {

		Long result = -1L;
		ServiceClassEntity sc = null;

		try {
			sc = serviceClassRepository.findBySvcClassCode(svcClassCode);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find ServiceClass
			// with code - " + svcClassCode);
			// no-op
		}

		if (sc == null)
			throw new EntityValidationException("Could not find serviceclass with code - " + svcClassCode);

		if (sc.getLifeCycleStatus() == LifeCycleStatus.INACTIVE) {
			if (status != LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					sc.setLifeCycleStatus(status);
					JpaUtil.updateRevisionControl(sc, true);
					updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
					result = 0L;

				}
			}
		} else if (sc.getLifeCycleStatus() == LifeCycleStatus.ACTIVE) {
			if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					sc.setLifeCycleStatus(status);
					JpaUtil.updateRevisionControl(sc, true);
					updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to INACTIVE when current status is ACTIVE !");
				}
			} else if (status == LifeCycleStatus.SUSPENDED || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					sc.setLifeCycleStatus(status);
					JpaUtil.updateRevisionControl(sc, true);
					updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
					result = 0L;
				}
			}
		} else if (sc.getLifeCycleStatus() == LifeCycleStatus.SUSPENDED) {
			if (status == LifeCycleStatus.ACTIVE || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					sc.setLifeCycleStatus(status);
					JpaUtil.updateRevisionControl(sc, true);
					updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
					result = 0L;
				}
			} else if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					sc.setLifeCycleStatus(status);
					JpaUtil.updateRevisionControl(sc, true);
					updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot change status to INACTIVE when current status is SUSPENDED !");
				}
			}
		} else if (sc.getLifeCycleStatus() == LifeCycleStatus.TERMINATED) {
			if (isPrivilegedContext()) {
				sc.setLifeCycleStatus(status);
				JpaUtil.updateRevisionControl(sc, true);
				updateSvcAreaCategoryAndLogisticsLifeCycle(svcClassCode, status);
				result = 0L;
			} else {
				throw new EntityValidationException(
						"Cannot change status to " + status + " when current status is TERMINATED !");
			}
		}

		return (result);
	}

	private void updateSvcAreaCategoryAndLogisticsLifeCycle(String svcClassCode, LifeCycleStatus status) {
		// update serviceArea
		List<ServiceAreaEntity> svcAreas = serviceAreaRepository.findBySvcClassCode(svcClassCode);
		for (ServiceAreaEntity serviceArea : svcAreas) {
			serviceArea.setLifeCycleStatus(status);
			JpaUtil.updateRevisionControl(serviceArea, true);
		}

		// update serviceCategory
		ServiceCategoryEntity serviceCategories = serviceCategoryRepository.findBySvcClassCode(svcClassCode);
		// no method of lifecyclestatus - not defined in requirement spec
		JpaUtil.updateRevisionControl(serviceCategories, true);

		// update logisticsInfo
		List<LogisticsCostEntity> logisticsCostEntities = logisticsCostRepository.findAll();
		for (LogisticsCostEntity logisticsCost : logisticsCostEntities) {
			ServiceCategoryEntity serviceCategory = logisticsCost.getSvcCatgRef();
			if (serviceCategory != null && serviceCategory.getDestSvcClassCode().equals(svcClassCode)
					|| serviceCategory.getSrcSvcClassCode().equals(svcClassCode)) {
				logisticsCost.setLifecycleStatus(status);
				JpaUtil.updateRevisionControl(logisticsCost, true);
			}
		}

	}

	@Override
	protected ServiceClassEntity doUpdate(ServiceClassEntity neu, ServiceClassEntity old)
			throws EntityValidationException {

		if (!old.getSvcClassCode().equals(neu.getSvcClassCode())) {
			throw new EntityValidationException("ServiceClass cannot be updated ! Existing - " + old.getSvcClassCode()
					+ ", new - " + neu.getSvcClassCode());
		}

		if (!Objects.equals(neu.getLifeCycleStatus(), old.getLifeCycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		List<Long> deletedServiceDestCategories = new ArrayList<Long>();
		List<Long> deletedServiceSrcCategories = new ArrayList<Long>();

		if (old.getDestSvcCategories() != null && !old.getDestSvcCategories().isEmpty()) {
			for (ServiceCategoryEntity oldServiceDestCategory : old.getDestSvcCategories()) {
				boolean found = false;
				if (neu.getDestSvcCategories() != null && !neu.getDestSvcCategories().isEmpty()) {
					for (ServiceCategoryEntity newServiceDestCategory : neu.getDestSvcCategories()) {
						if (Objects.equals(newServiceDestCategory.getId(), oldServiceDestCategory.getId())) {
							found = true;
							break;
						}
						// } // end for new serviceCategory
					}
				}
				if (!found) {
					// accountSubCategory deleted
					if (old.getLifeCycleStatus() != LifeCycleStatus.INACTIVE) {
						// if (isPrivilegedContext()) {
						deletedServiceDestCategories.add(oldServiceDestCategory.getId());
						/*
						 * } else { throw new EntityValidationException(
						 * "Cannot delete ServiceCategory  " +
						 * oldServiceDestCategory.getDestSvcClassCode() +
						 * " when lifeCycle is " + old.getLifeCycleStatus()); }
						 */
					} else {
						deletedServiceDestCategories.add(oldServiceDestCategory.getId());
					}
				}

			}

		}

		if (old.getSrcSvcCategories() != null && !old.getSrcSvcCategories().isEmpty()) {
			for (ServiceCategoryEntity oldServiceSrcCategory : old.getSrcSvcCategories()) {
				boolean found = false;
				if (neu.getSrcSvcCategories() != null && !neu.getSrcSvcCategories().isEmpty()) {
					for (ServiceCategoryEntity newServiceSrcCategory : neu.getSrcSvcCategories()) {
						if (Objects.equals(newServiceSrcCategory.getId(), oldServiceSrcCategory.getId())) {
							found = true;
							break;
						}
						// } // end for new serviceCategory
					}
				}
				if (!found) {
					// serviceCategory deleted
					if (old.getLifeCycleStatus() != LifeCycleStatus.INACTIVE) {
						// if (isPrivilegedContext()) {
						deletedServiceSrcCategories.add(oldServiceSrcCategory.getId());
						/*
						 * } else { throw new EntityValidationException(
						 * "Cannot delete ServiceCategory  " +
						 * oldServiceSrcCategory.getSrcSvcClassCode() +
						 * " when lifeCycle is " + old.getLifeCycleStatus()); }
						 */
					} else {
						deletedServiceSrcCategories.add(oldServiceSrcCategory.getId());
					}
				}

			}

		}
		
		boolean updated = false;
		if (!deletedServiceDestCategories.isEmpty() || !deletedServiceSrcCategories.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update

			// carry out the update
			if (neu.getDestSvcCategories() != null && !neu.getDestSvcCategories().isEmpty()) {
				updateDestServiceCategories(neu, old);
			}

			if (neu.getSrcSvcCategories() != null && !neu.getSrcSvcCategories().isEmpty()) {
				updateSrcServiceCategories(neu, old);

			}

			if (!deletedServiceDestCategories.isEmpty()) {
				// dissociate DestServiceCategories
				deleteServiceCategory(old.getDestSvcCategories(),old, deletedServiceDestCategories,"dest");
			}

			if (!deletedServiceSrcCategories.isEmpty()) {
				// dissociate SrcServiceCategories
				deleteServiceCategory(old.getSrcSvcCategories(),old, deletedServiceSrcCategories,"src");
			}

			old.setPartnerId(neu.getPartnerId());
			old.setSvcClassName(neu.getSvcClassName());
			JpaUtil.updateRevisionControl(old, true);
			// }
		}
		return (old);
	}

	private boolean checkForUpdate(ServiceClassEntity neu, ServiceClassEntity old) {
		boolean result = false;
		if (!Objects.equals(neu.getPartnerId(), old.getPartnerId())
				|| !Objects.equals(neu.getSvcClassName(), old.getSvcClassName())) {

			result = true;
		}

		if (neu.getDestSvcCategories() == null || neu.getDestSvcCategories().isEmpty()) {
			if (old.getDestSvcCategories() != null && !old.getDestSvcCategories().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getDestSvcCategories() == null || old.getDestSvcCategories().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getDestSvcCategories().size(), old.getDestSvcCategories().size())) {
					result = true;
					return (result);
				} else {
					result = checkForSvcCategories(neu.getDestSvcCategories(), old.getDestSvcCategories());
					return (result);
				}
			}
		}

		if (neu.getSrcSvcCategories() == null || neu.getSrcSvcCategories().isEmpty()) {
			if (old.getSrcSvcCategories() != null && !old.getSrcSvcCategories().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getSrcSvcCategories() == null || old.getSrcSvcCategories().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getSrcSvcCategories().size(), old.getSrcSvcCategories().size())) {
					result = true;
					return (result);
				} else {
					result = checkForSvcCategories(neu.getSrcSvcCategories(), old.getSrcSvcCategories());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForSvcCategories(Set<ServiceCategoryEntity> newServiceCategories,
			Set<ServiceCategoryEntity> oldServiceCategories) {

		boolean result = false;
		Iterator<ServiceCategoryEntity> oldServiceCategoryEntityIter = oldServiceCategories.iterator();
		Iterator<ServiceCategoryEntity> newServiceCategoryEntityIter = newServiceCategories.iterator();
		ServiceCategoryEntity oldServiceCategory = null;
		ServiceCategoryEntity newServiceCategory = null;

		while (newServiceCategoryEntityIter.hasNext()) {
			newServiceCategory = newServiceCategoryEntityIter.next();
			Long newEntityId = newServiceCategory.getId();
			if (newEntityId != null) {
				while (oldServiceCategoryEntityIter.hasNext()) {
					oldServiceCategory = oldServiceCategoryEntityIter.next();
					Long oldEntityId = oldServiceCategory.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = serviceCategoryService.checkForUpdate(newServiceCategory, oldServiceCategory);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = serviceCategoryService.checkForUpdate(newServiceCategory, oldServiceCategory);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void deleteServiceCategory(Set<ServiceCategoryEntity> serviceCategories,ServiceClassEntity oldSvcClass,
			List<Long> deletedServiceCategories,String conversionType) {
		Iterator<ServiceCategoryEntity> serviceCategoryEntityIter = serviceCategories.iterator();
		for (Long id : deletedServiceCategories) {
			boolean found = false;
			ServiceCategoryEntity oldServiceCategory = null;
			while (serviceCategoryEntityIter.hasNext()) {
				oldServiceCategory = serviceCategoryEntityIter.next();
				if (Objects.equals(id, oldServiceCategory.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				if (conversionType.equals("src")){
					oldSvcClass.getSrcSvcCategories().remove(oldServiceCategory);
				}else if (conversionType.equals("dest")){
					oldSvcClass.getDestSvcCategories().remove(oldServiceCategory);
				}
				serviceCategoryService.delete(id);
			}
		}
	}

	private void updateDestServiceCategories(ServiceClassEntity neu, ServiceClassEntity old)
			throws EntityValidationException {
		for (ServiceCategoryEntity newServiceCategory : neu.getDestSvcCategories()) {
			ServiceCategoryEntity oldServiceCategory2 = null;
			boolean found = false;
			if (old.getDestSvcCategories() != null && !old.getDestSvcCategories().isEmpty()) {
				for (ServiceCategoryEntity oldServiceCategory : old.getDestSvcCategories()) {
					if (Objects.equals(newServiceCategory.getId(), oldServiceCategory.getId())) {
						oldServiceCategory2 = oldServiceCategory;
						found = true;
						break;
					}
				} // end for old.getServiceCategories
			} // end if old.getServiceCategories for src and dest

			if (!found) {
				// new ServiceCategory being added
				newServiceCategory.setDestSvcClassRef(old);
				newServiceCategory.setDestSvcClassCode(old.getSvcClassCode());
				newServiceCategory.setRevisionControl(getRevisionControl());

				serviceCategoryService.validate(newServiceCategory, false, EntityVdationType.PRE_INSERT);
				old.getDestSvcCategories().add(newServiceCategory);
			} else {
				boolean serviceCategoryUpdated = serviceCategoryService.checkForUpdate(newServiceCategory,
						oldServiceCategory2);
				if (serviceCategoryUpdated) {
					serviceCategoryService.update(newServiceCategory);
				}
			}

		}
	}

	private void updateSrcServiceCategories(ServiceClassEntity neu, ServiceClassEntity old)
			throws EntityValidationException {
		for (ServiceCategoryEntity newServiceCategory : neu.getSrcSvcCategories()) {
			ServiceCategoryEntity oldServiceCategory2 = null;
			boolean found = false;
			if (old.getSrcSvcCategories() != null && !old.getSrcSvcCategories().isEmpty()) {
				for (ServiceCategoryEntity oldServiceCategory : old.getSrcSvcCategories()) {
					if (Objects.equals(newServiceCategory.getId(), oldServiceCategory.getId())) {
						oldServiceCategory2 = oldServiceCategory;
						found = true;
						break;
					}
				} // end for old.getAccountSubCategories
			} // end if old.getAccountSubCategories

			if (!found) {
				// new AccountSubCategory being added
				newServiceCategory.setSrcSvcClassRef(old);
				newServiceCategory.setSrcSvcClassCode(old.getSvcClassCode());
				newServiceCategory.setRevisionControl(getRevisionControl());

				serviceCategoryService.validate(newServiceCategory, false, EntityVdationType.PRE_INSERT);
				old.getSrcSvcCategories().add(newServiceCategory);
			} else {
				boolean serviceCategoryUpdated = serviceCategoryService.checkForUpdate(newServiceCategory,
						oldServiceCategory2);
				if (serviceCategoryUpdated) {
					serviceCategoryService.update(newServiceCategory);
				}
			}

		}
	}

	@Override
	protected AbstractServiceImpl<ServiceClassEntity, Long>.Outcome postProcess(int opCode, ServiceClassEntity entity)
			throws EntityValidationException {
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

		return result;
	}

	@Override
	protected AbstractServiceImpl<ServiceClassEntity, Long>.Outcome preProcess(int opCode, ServiceClassEntity entity)
			throws EntityValidationException {
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

	private void preProcessAdd(ServiceClassEntity entity) throws EntityValidationException {
		entity.setLifeCycleStatus(LifeCycleStatus.ACTIVE);// TODO - to be
															// changes NEW with
															// new enum
		validate(entity, EntityVdationType.PRE_INSERT);
	}

	private void preDelete(ServiceClassEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifeCycleStatus() != LifeCycleStatus.ACTIVE) {// TODO
																								// -
																								// new
																								// status
																								// in
																								// enum
			throw new EntityValidationException(
					"Cannot delete ServiceClass. when state is " + entity.getLifeCycleStatus() + ".Only be TERMINATED");
		}
	}

}
