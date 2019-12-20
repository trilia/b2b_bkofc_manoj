package com.olp.jpa.domain.docu.logist.repo;

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

		//update logisticsInfo
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

		boolean updated = checkForUpdate(neu, old);

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			// end if deletedLovValues not empty

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
		return result;
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
