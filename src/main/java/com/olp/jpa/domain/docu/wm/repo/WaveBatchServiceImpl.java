/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;
import com.olp.jpa.util.JpaUtil;

@Service("waveBatchService")
public class WaveBatchServiceImpl extends AbstractServiceImpl<WaveBatchEntity, Long> implements WaveBatchService {

	@Autowired
	@Qualifier("waveBatchRepository")
	private WaveBatchRepository waveBatchRepo;

	@Autowired
	@Qualifier("waveResourcesService")
	private WaveResourcesService waveResourcesService;

	@Autowired
	@Qualifier("waveTasksService")
	private WaveTasksService waveTasksService;

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public WaveBatchEntity getBatchByNum(String batchNum) {
		WaveBatchEntity waveBatchentity = waveBatchRepo.getBatchByNum(batchNum);
		return waveBatchentity;
	}

	@Override
	protected String getAlternateKeyAsString(WaveBatchEntity waveBatchentity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"waveBatch\" : \"").append(waveBatchentity.getBatchNumber()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected JpaRepository<WaveBatchEntity, Long> getRepository() {
		return waveBatchRepo;
	}

	@Override
	protected ITextRepository<WaveBatchEntity, Long> getTextRepository() {
		return waveBatchRepo;
	}

	@Override
	protected Outcome postProcess(int opCode, WaveBatchEntity arg1) throws EntityValidationException {
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
	protected Outcome preProcess(int opCode, WaveBatchEntity entity) throws EntityValidationException {
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
	@Transactional
	public Long requestStatusChange(String batchNumber, LifeCycleStatus status) throws EntityValidationException {
		Long result = -1L;
		WaveBatchEntity waveBatchEntity = null;
		try {
			waveBatchEntity = waveBatchRepo.getBatchByNum(batchNumber);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find UOM
			// with code - " + uomCode);
			// no-op
		}

		if (waveBatchEntity == null)
			throw new EntityValidationException("Could not find WaveBatch with batchNumber - " + batchNumber);

		if (waveBatchEntity.getLifecycleStatus() == LifeCycleStatus.INACTIVE) {
			if (status != LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					waveBatchEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(waveBatchEntity, true);
					result = 0L;
				}
			}
		} else if (waveBatchEntity.getLifecycleStatus() == LifeCycleStatus.ACTIVE) {
			if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					waveBatchEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(waveBatchEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to INACTIVE when current status is ACTIVE !");
				}
			} else if (status == LifeCycleStatus.SUSPENDED || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					waveBatchEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(waveBatchEntity, true);
					result = 0L;
				}
			}
		} else if (waveBatchEntity.getLifecycleStatus() == LifeCycleStatus.SUSPENDED) {
			if (status == LifeCycleStatus.ACTIVE || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					waveBatchEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(waveBatchEntity, true);
					result = 0L;
				}
			} else if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					waveBatchEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(waveBatchEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot change status to INACTIVE when current status is SUSPENDED !");
				}
			}
		} else if (waveBatchEntity.getLifecycleStatus() == LifeCycleStatus.TERMINATED) {
			if (isPrivilegedContext()) {
				waveBatchEntity.setLifecycleStatus(status);
				JpaUtil.updateRevisionControl(waveBatchEntity, true);
				result = 0L;
			} else {
				throw new EntityValidationException(
						"Cannot change status to " + status + " when current status is TERMINATED !");
			}
		}
		return (result);
	}

	@Override
	@Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
	public void validate(WaveBatchEntity entity, EntityVdationType type) throws EntityValidationException {
		Set<WaveResourcesEntity> waveResources = entity.getWaveResoures();

		if (waveResources != null && !waveResources.isEmpty()) {
			for (WaveResourcesEntity waveResource : waveResources) {
				waveResource.setWaveBatchRef(entity);
				waveResource.setWarehouseCode(entity.getWarehouseCode());
				waveResourcesService.validate(waveResource, false, type);
			}

		} // end of waveResources validation

		List<WaveTasksEntity> waveTasks = entity.getWaveTasks();
		if (waveTasks != null && !waveTasks.isEmpty()) {
			for (WaveTasksEntity waveTask : waveTasks) {
				waveTask.setWaveBatchRef(entity);
				waveTask.setWarehouseCode(entity.getWarehouseCode());
				waveTasksService.validate(waveTask, false, type);
			}
		} // end of waveTasks validation

		if (EntityVdationType.PRE_INSERT == type) {
			entity.setLifecycleStatus(LifeCycleStatus.INACTIVE);
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {

			// this is a dummy update of lifecycle status, we do not use this
			// field for actual
			// update. This is needed only to get past the required attribute
			// checker.
			entity.setLifecycleStatus(LifeCycleStatus.INACTIVE);
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected WaveBatchEntity doUpdate(WaveBatchEntity neu, WaveBatchEntity old) throws EntityValidationException {

		if (!old.getBatchNumber().equals(neu.getBatchNumber())) {
			throw new EntityValidationException("WaveBatch cannot be updated ! Existing - " + old.getBatchNumber()
					+ ", new - " + neu.getBatchNumber());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		if (!Objects.equal(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		List<Long> deletedWaveResources = new ArrayList<Long>();
		List<Long> deletedWaveTasks = new ArrayList<Long>();

		if (old.getWaveResoures() != null && !old.getWaveResoures().isEmpty()) {
			for (WaveResourcesEntity oldWaveResource : old.getWaveResoures()) {
				boolean found = false;
				if (neu.getWaveResoures() != null && !neu.getWaveResoures().isEmpty()) {
					for (WaveResourcesEntity newWaveResource : neu.getWaveResoures()) {
						if (Objects.equal(newWaveResource.getId(), oldWaveResource.getId())) {
							found = true;
							break;
						}
					} // end for new wave resource
				}
				if (!found) {
					// waveResource deleted
					if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedWaveResources.add(oldWaveResource.getId());
						} else {
							throw new EntityValidationException(
									"Cannot delete Wave Resource " + oldWaveResource.getBatchNumber()
											+ " when warehouse is " + old.getLifecycleStatus());
						}
					} else {
						deletedWaveResources.add(oldWaveResource.getId());
					}
				}
			}
		}

		if (old.getWaveTasks() != null && !old.getWaveTasks().isEmpty()) {
			for (WaveTasksEntity oldWaveTask : old.getWaveTasks()) {
				boolean found = false;
				if (neu.getWaveTasks() != null && !neu.getWaveTasks().isEmpty()) {
					for (WaveTasksEntity newWaveTask : neu.getWaveTasks()) {
						if (Objects.equal(newWaveTask.getId(), oldWaveTask.getId())) {
							found = true;
							break;
						}
					} // end for new WaveTask
				}
				if (!found) {
					// waveTasks deleted
					if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedWaveTasks.add(oldWaveTask.getId());
						} else {
							throw new EntityValidationException("Cannot delete Wave Task "
									+ oldWaveTask.getBatchNumber() + " when warehouse is " + old.getLifecycleStatus());
						}
					} else {
						deletedWaveTasks.add(oldWaveTask.getId());
					}
				}
			}
		}

		boolean updated = false;
		if (!deletedWaveResources.isEmpty() || !deletedWaveTasks.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			//if (isPrivilegedContext()) {
				// carry out the update
				if (neu.getWaveResoures() != null && !neu.getWaveResoures().isEmpty()) {
					updateWaveResources(neu, old); // end for neu.getResources
				} // end if neu.getWaveResources != null

				if (neu.getWaveTasks() != null && !neu.getWaveTasks().isEmpty()) {
					updateWaveTasks(neu, old); // end for neu.getResources
				} // end if neu.getWaveTasks != null

				if (!deletedWaveResources.isEmpty()) {
					// dissociate waveResources
					deleteWaveResources(old.getWaveResoures(), old, deletedWaveResources);
				} // end if deletedWaveTasks not empty

				if (!deletedWaveTasks.isEmpty()) {
					// dissociate waveTasks
					deleteWaveTasks(old.getWaveTasks(), old, deletedWaveTasks);
				}

				JpaUtil.updateRevisionControl(old, true);
			//}
		}
		return (old);
	}

	private void preProcessAdd(WaveBatchEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(WaveBatchEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
			throw new EntityValidationException("Cannot delete wavebatch when state is " + entity.getLifecycleStatus());
	}

	private boolean checkForUpdate(WaveBatchEntity neu, WaveBatchEntity old) throws EntityValidationException {
		boolean result = false;

		if (!Objects.equal(neu.getBatchNumber(), old.getBatchNumber())
				|| !Objects.equal(neu.getWarehouseCode(), old.getWarehouseCode())) {

			result = true;
			return (result);
		}

		// check if waveResources are same of diff
		if (neu.getWaveResoures() == null || neu.getWaveResoures().isEmpty()) {
			if (old.getWaveResoures() != null && !old.getWaveResoures().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getWaveResoures() == null || old.getWaveResoures().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equal(neu.getWaveResoures().size(), old.getWaveResoures().size())) {
					result = true;
					return (result);
				} else {
					result = checkForUpdateWhenBothResourcesEntityNotNull(old, neu);
					return (result);
				}
			}
		}

		// check if wavetasks are same of diff
		if (neu.getWaveTasks() == null || neu.getWaveTasks().isEmpty()) {
			if (old.getWaveTasks() != null && !old.getWaveTasks().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getWaveTasks() == null || old.getWaveTasks().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equal(neu.getWaveTasks().size(), old.getWaveTasks().size())) {
					result = true;
					return (result);
				} else {
					result = checkForUpdateWhenBothTasksEntityNotNull(old, neu);
					return (result);
				}
			}
		}
		return result;

	}

	private boolean checkForUpdateWhenBothResourcesEntityNotNull(WaveBatchEntity old, WaveBatchEntity neu) throws EntityValidationException {
		boolean result = false;
		Iterator<WaveResourcesEntity> oldWaveResourceEntityIter = old.getWaveResoures().iterator();
		Iterator<WaveResourcesEntity> newWaveReourceEntityIter = neu.getWaveResoures().iterator();
		WaveResourcesEntity oldWaveResource = null;
		WaveResourcesEntity newWaveResource = null;

		while (newWaveReourceEntityIter.hasNext()) {
			newWaveResource = newWaveReourceEntityIter.next();
			Long newEntityId = newWaveResource.getId();
			if (newEntityId != null) {
				while (oldWaveResourceEntityIter.hasNext()) {
					oldWaveResource = oldWaveResourceEntityIter.next();
					Long oldEntityId = oldWaveResource.getId();
					if (Objects.equal(newEntityId, oldEntityId)) {
						boolean outcome = waveResourcesService.checkForUpdate(newWaveResource, oldWaveResource);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = waveResourcesService.checkForUpdate(newWaveResource, oldWaveResource);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private boolean checkForUpdateWhenBothTasksEntityNotNull(WaveBatchEntity old, WaveBatchEntity neu) {
		boolean result = false;
		Iterator<WaveTasksEntity> oldWaveTasksEntityIter = old.getWaveTasks().iterator();
		Iterator<WaveTasksEntity> newWaveTasksEntityIter = neu.getWaveTasks().iterator();
		WaveTasksEntity oldWaveTask = null;
		WaveTasksEntity newWaveTask = null;

		while (newWaveTasksEntityIter.hasNext()) {
			newWaveTask = newWaveTasksEntityIter.next();
			Long newEntityId = newWaveTask.getId();
			if (newEntityId != null) {
				while (oldWaveTasksEntityIter.hasNext()) {
					oldWaveTask = oldWaveTasksEntityIter.next();
					Long oldEntityId = oldWaveTask.getId();
					if (Objects.equal(newEntityId, oldEntityId)) {
						boolean outcome = waveTasksService.checkForUpdate(newWaveTask, oldWaveTask);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = waveTasksService.checkForUpdate(newWaveTask, oldWaveTask);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void updateWaveResources(WaveBatchEntity neu, WaveBatchEntity old) throws EntityValidationException {
		for (WaveResourcesEntity newWaveResource : neu.getWaveResoures()) {
			WaveResourcesEntity oldWaveResource2 = null;
			boolean found = false;
			if (old.getWaveResoures() != null && !old.getWaveResoures().isEmpty()) {
				for (WaveResourcesEntity oldWaveResource : old.getWaveResoures()) {
					if (Objects.equal(newWaveResource.getId(), oldWaveResource.getId())) {
						oldWaveResource2 = oldWaveResource;
						found = true;
						break;
					}
				} // end for old.getWaveResources
			} // end if old.getWaveResources

			if (!found) {
				// new zone being added
				newWaveResource.setWaveBatchRef(old);
				newWaveResource.setBatchNumber(old.getBatchNumber());
				newWaveResource.setRevisionControl(getRevisionControl());
				newWaveResource.setTenantId(getTenantId());

				waveResourcesService.validate(newWaveResource, false, EntityVdationType.PRE_INSERT);
				old.getWaveResoures().add(newWaveResource);
			} else {
				boolean waveResourcesUpdated = waveResourcesService.checkForUpdate(newWaveResource, oldWaveResource2);
				if (waveResourcesUpdated) {
					waveResourcesService.update(newWaveResource);
				}
			}

		}
	}

	private void updateWaveTasks(WaveBatchEntity neu, WaveBatchEntity old) throws EntityValidationException {
		for (WaveTasksEntity newWaveTask : neu.getWaveTasks()) {
			WaveTasksEntity oldWaveTask2 = null;
			boolean found = false;
			if (old.getWaveResoures() != null && !old.getWaveResoures().isEmpty()) {
				for (WaveTasksEntity oldWaveResource : old.getWaveTasks()) {
					if (Objects.equal(newWaveTask.getId(), oldWaveResource.getId())) {
						oldWaveTask2 = oldWaveResource;
						found = true;
						break;
					}
				} // end for old.getWaveTasks
			} // end if old.getWaveTasks

			if (!found) {
				// new zone being added
				newWaveTask.setWaveBatchRef(old);
				newWaveTask.setBatchNumber(old.getBatchNumber());
				newWaveTask.setRevisionControl(getRevisionControl());
				newWaveTask.setTenantId(getTenantId());

				waveTasksService.validate(newWaveTask, false, EntityVdationType.PRE_INSERT);
				old.getWaveTasks().add(newWaveTask);
			} else {
				boolean waveResourcesUpdated = waveTasksService.checkForUpdate(newWaveTask, oldWaveTask2);
				if (waveResourcesUpdated) {
					waveTasksService.update(newWaveTask);
				}
			}
		}
	}
	
	private void deleteWaveResources(Set<WaveResourcesEntity> waveResources, WaveBatchEntity old,
			List<Long> deletedWaveResources) {
		Iterator<WaveResourcesEntity> waveResourceEntityIter = old.getWaveResoures().iterator();
		for (Long id : deletedWaveResources) {
			boolean found = false;
			WaveResourcesEntity oldWaveResource = null;
			while (waveResourceEntityIter.hasNext()) {
				oldWaveResource = waveResourceEntityIter.next();
				if (Objects.equal(id, oldWaveResource.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				waveResourcesService.delete(id);
			}
		}
	} 
	
	private void deleteWaveTasks(List<WaveTasksEntity> waveTasks, WaveBatchEntity old,
			List<Long> deletedWaveTasks) {
		// dissociate waveTasks
		Iterator<WaveTasksEntity> waveTasksEntityIter = old.getWaveTasks().iterator();
		for (Long id : deletedWaveTasks) {
			boolean found = false;
			WaveTasksEntity oldWaveTask = null;
			while (waveTasksEntityIter.hasNext()) {
				oldWaveTask = waveTasksEntityIter.next();
				if (Objects.equal(id, oldWaveTask.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				waveTasksService.delete(id);
			}
		}
	} 
}
