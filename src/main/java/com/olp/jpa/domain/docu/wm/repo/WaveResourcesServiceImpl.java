package com.olp.jpa.domain.docu.wm.repo;

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
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.org.repo.EmployeeService;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceType;
import com.olp.jpa.util.JpaUtil;

@Service("waveResourcesService")
public class WaveResourcesServiceImpl extends AbstractServiceImpl<WaveResourcesEntity, Long>
		implements WaveResourcesService {

	@Autowired
	@Qualifier("waveResourcesRepository")
	private WaveResourcesRepository waveResourcesRepository;

	@Autowired
	@Qualifier("waveBatchRepository")
	private WaveBatchRepository waveBatchRepository;

	@Autowired
	@Qualifier("employeeService")
	private EmployeeService employeeService;

	@Override
	protected String getAlternateKeyAsString(WaveResourcesEntity waveResourcesEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"Wave Resources\" : \"").append(waveResourcesEntity.getBatchNumber()).append("\" }");
		return (buff.toString());
	}

	@Override
	protected JpaRepository<WaveResourcesEntity, Long> getRepository() {
		return waveResourcesRepository;
	}

	@Override
	protected ITextRepository<WaveResourcesEntity, Long> getTextRepository() {
		return waveResourcesRepository;
	}

	@Override
	public List<WaveResourcesEntity> findByEmp(String whCode, String empCode) {
		List<WaveResourcesEntity> waveResourcesList = waveResourcesRepository.findByEmp(whCode, empCode);
		return waveResourcesList;
	}

	@Override
	protected AbstractServiceImpl<WaveResourcesEntity, Long>.Outcome postProcess(int opCode, WaveResourcesEntity entity)
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

		return (result);
	}

	@Override
	protected AbstractServiceImpl<WaveResourcesEntity, Long>.Outcome preProcess(int opCode, WaveResourcesEntity entity)
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
			validate(entity, true, EntityVdationType.PRE_UPDATE);
		case DELETE:
		case DELETE_BULK:
			preDelete(entity);
		default:
			break;
		}

		return (result);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(WaveResourcesEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			WaveBatchEntity waveBatchEntity = entity.getWaveBatchRef();
			WaveBatchEntity waveBatchEntityTwo = null;
			if (waveBatchEntity == null)
				throw new EntityValidationException("WaveBatch reference cannot be null !!");

			if (waveBatchEntity.getId() == null) {
				try {
					waveBatchEntityTwo = waveBatchRepository.getBatchByNum(waveBatchEntity.getBatchNumber());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find wavebatch with batchNumber - " + waveBatchEntity.getBatchNumber());
				}
			} else {
				try {
					waveBatchEntityTwo = waveBatchRepository.findOne(waveBatchEntity.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find wavebatch with id - " + waveBatchEntity.getId());
				}
			}

			if (waveBatchEntityTwo == null)
				throw new EntityValidationException("Could not find wavebatch using either batchnum or id !");

			entity.setWaveBatchRef(waveBatchEntityTwo);
			entity.setBatchNumber(waveBatchEntityTwo.getBatchNumber());

			if (entity.getResourceType() == WaveResourceType.EMPLOYEE) {
				EmployeeEntity employeeRef = entity.getEmployeeRef();
				if (employeeRef == null) {
					throw new EntityValidationException("Employee Ref cannot be null for ResourceType employee !");
				}
				if (entity.getResourceRole() == null) {
					throw new EntityValidationException("Resource Role cannot be null for ResourceType employee !");
				}
			}
		}
		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	public boolean checkForUpdate(WaveResourcesEntity neu, WaveResourcesEntity old) throws EntityValidationException {

		boolean result = false;

		if (!Objects.equals(neu.getBatchNumber(), old.getBatchNumber())
				|| !Objects.equals(neu.getEmployeeCode(), old.getEmployeeCode())
				|| !Objects.equals(neu.getResourceRole(), old.getResourceRole())
				|| !Objects.equals(neu.getResourceType(), old.getResourceType())
				|| !Objects.equals(neu.getWarehouseCode(), old.getWarehouseCode())) {

			result = true;
			return (result);
		}
		if (neu.getEmployeeRef() == null) {
			if (old.getEmployeeRef() != null) {
				result = true;
				return (result);
			}
		} else {
			if (old.getEmployeeRef() == null) {
				result = true;
				return (result);
			} else {
				// both are not null
				EmployeeEntity newEmp = neu.getEmployeeRef();
				EmployeeEntity oldEmp = old.getEmployeeRef();
				/*
				 * if (employeeService.checkForUpdate(newEmp, oldEmp)) { result
				 * = true; break; }
				 */ // checkforupdate not found on employeeEntity
			}
		}

		return (result);
	}

	protected WaveResourcesEntity doUpdate(WaveResourcesEntity neu, WaveResourcesEntity old)
			throws EntityValidationException {
		if (!old.getEmployeeCode().equals(neu.getEmployeeCode()))
			throw new EntityValidationException("WaveResources employeeCode cannot be updated ! Existing - "
					+ old.getEmployeeCode() + " , new - " + neu.getEmployeeCode());

		if (!old.getBatchNumber().equals(neu.getBatchNumber())) {
			throw new EntityValidationException("WaveResources cannot be updated ! Existing - " + old.getBatchNumber()
					+ ", new - " + neu.getBatchNumber());
		}

		if (old.getWaveBatchRef().getBatchNumber() != null
				&& !old.getWaveBatchRef().getBatchNumber().equals(neu.getWaveBatchRef().getBatchNumber())) {
			throw new EntityValidationException("WaveBatch Ref Code cannot be updated ! Existing - "
					+ old.getWaveBatchRef().getBatchNumber() + ", new - " + neu.getWaveBatchRef().getBatchNumber());
		}

		old.setResourceRole(neu.getResourceRole());
		old.setResourceType(neu.getResourceType());
		old.setWarehouseCode(neu.getWarehouseCode());
		this.updateRevisionControl(old);
		return (old);
	}

	private void preProcessAdd(WaveResourcesEntity entity) throws EntityValidationException {
		validate(entity, true, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(WaveResourcesEntity entity) throws EntityValidationException {
		if (entity.getWaveBatchRef() != null) {
			WaveBatchEntity waveBatchEntity = entity.getWaveBatchRef();
			if (!isPrivilegedContext() && waveBatchEntity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
				throw new EntityValidationException("Cannot delete Wave Resources when WaveBatch status is "
						+ waveBatchEntity.getLifecycleStatus());
		}
	}

}
