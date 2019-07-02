package com.olp.jpa.domain.docu.wm.repo;

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
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;
import com.olp.jpa.util.JpaUtil;

@Service("waveTasksService")
public class WaveTasksServiceImpl extends AbstractServiceImpl<WaveTasksEntity, Long> implements WaveTasksService {

	@Autowired
	@Qualifier("waveTasksRepository")
	private WaveTasksRepository waveTasksRepository;

	@Autowired
	@Qualifier("waveBatchRepository")
	private WaveBatchRepository waveBatchRepository;

	@Override
	protected String getAlternateKeyAsString(WaveTasksEntity waveTasksEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"Wave Tasks\" : \"").append(waveTasksEntity.getBatchNumber()).append("\" }");
		return (buff.toString());
	}

	@Override
	public WaveTasksEntity getBatchByNum(String batchNum) {
		WaveTasksEntity waveTasksEntity = waveTasksRepository.getBatchByNum(batchNum);
		return waveTasksEntity;
	}

	@Override
	protected JpaRepository<WaveTasksEntity, Long> getRepository() {
		return waveTasksRepository;
	}

	@Override
	protected ITextRepository<WaveTasksEntity, Long> getTextRepository() {
		return waveTasksRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(WaveTasksEntity entity, boolean valParent, EntityVdationType type)
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

			// Employees should belong to WaveResourcesEntity for the same
			EmployeeEntity employeeInTaskEntity = entity.getAssignedTo();
			if (employeeInTaskEntity != null) {
				boolean employeeMatch = false;
				Set<WaveResourcesEntity> waveResources = waveBatchEntityTwo.getWaveResoures();
				for (WaveResourcesEntity waveResource : waveResources) {
					EmployeeEntity employeeEntityInResources = waveResource.getEmployeeRef();
					if (employeeEntityInResources != null && employeeEntityInResources.getEmployeeCode()
							.equals(employeeInTaskEntity.getEmployeeCode())) {
						employeeMatch = true;
					}
				}
				if (!employeeMatch) {
					throw new EntityValidationException(
							"Could not found Employees should belong to WaveResourcesEntity for the same !");
				}
			}

			// TODO - Employees should not have overlapping time schedule for
			// all active engagements in another batch
		}
		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected AbstractServiceImpl<WaveTasksEntity, Long>.Outcome postProcess(int opCode, WaveTasksEntity entity)
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
	protected AbstractServiceImpl<WaveTasksEntity, Long>.Outcome preProcess(int opCode, WaveTasksEntity entity)
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
	public boolean checkForUpdate(WaveTasksEntity neu, WaveTasksEntity old) {

		boolean result = false;

		if (!Objects.equals(neu.getBatchNumber(), old.getBatchNumber())
				|| !Objects.equals(neu.getEmployeeCode(), old.getEmployeeCode())
				|| !Objects.equals(neu.getWarehouseCode(), old.getWarehouseCode())
				|| !Objects.equals(neu.getActualFinishDate(), old.getActualFinishDate())
				|| !Objects.equals(neu.getActualStartDate(), old.getActualStartDate())
				|| !Objects.equals(neu.getBatchNumber(), old.getBatchNumber())
				|| !Objects.equals(neu.getEmployeeCode(), old.getEmployeeCode())
				|| !Objects.equals(neu.getEstimatedFinishDate(), old.getEstimatedFinishDate())
				|| !Objects.equals(neu.getEstimatedStartDate(), old.getEstimatedStartDate())
				|| !Objects.equals(neu.getMatPipeCode(), old.getMatPipeCode())
				|| !Objects.equals(neu.getPalletNum(), old.getPalletNum())
				|| !Objects.equals(neu.getQuantity(), old.getQuantity())
				|| !Objects.equals(neu.getUomCode(), old.getUomCode())) {

			result = true;
			return (result);
		}

		return (result);
	}

	protected WaveTasksEntity doUpdate(WaveTasksEntity neu, WaveTasksEntity old) throws EntityValidationException {

		if (!old.getBatchNumber().equals(neu.getBatchNumber())) {
			throw new EntityValidationException("WaveTasks cannot be updated ! Existing - " + old.getBatchNumber()
					+ ", new - " + neu.getBatchNumber());
		}

		if (old.getWaveBatchRef().getBatchNumber() != null
				&& !old.getWaveBatchRef().getBatchNumber().equals(neu.getWaveBatchRef().getBatchNumber())) {
			throw new EntityValidationException("WaveBatch Ref Code cannot be updated ! Existing - "
					+ old.getWaveBatchRef().getBatchNumber() + ", new - " + neu.getWaveBatchRef().getBatchNumber());
		}

		old.setActualFinishDate(neu.getActualFinishDate());
		old.setActualStartDate(neu.getActualStartDate());
		old.setEstimatedFinishDate(neu.getEstimatedFinishDate());
		old.setEstimatedStartDate(neu.getEstimatedStartDate());
		old.setEmployeeCode(neu.getEmployeeCode());
		old.setWarehouseCode(neu.getWarehouseCode());
		// old.setAssignedTo(neu.getAssignedTo());
		old.setMatPipeCode(neu.getMatPipeCode());
		old.setMatPipeRef(neu.getMatPipeRef());
		old.setPalletNum(neu.getPalletNum());
		// old.setPalletRef(neu.getPalletRef());
		old.setQuantity(neu.getQuantity());
		// old.setQuantityUomRef(neu.getQuantityUomRef());
		old.setSerials(neu.getSerials());
		old.setTaskStatus(neu.getTaskStatus());
		old.setUomCode(neu.getUomCode());

		this.updateRevisionControl(old);
		return (old);
	}

	private void preProcessAdd(WaveTasksEntity entity) throws EntityValidationException {
		validate(entity, true, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(WaveTasksEntity entity) throws EntityValidationException {
		if (entity.getWaveBatchRef() != null) {
			WaveBatchEntity waveBatchEntity = entity.getWaveBatchRef();
			if (!isPrivilegedContext() && waveBatchEntity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
				throw new EntityValidationException(
						"Cannot delete Wave Tasks when WaveBatch status is " + waveBatchEntity.getLifecycleStatus());
		}
	}

}
