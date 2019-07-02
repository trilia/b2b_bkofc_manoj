package com.olp.jpa.domain.docu.inv.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;
import com.olp.jpa.util.JpaUtil;

@Service("unitOfMeasureService")
public class UnitOfMeasureServiceImpl extends AbstractServiceImpl<UnitOfMeasureEntity, Long>
		implements UnitOfMeasureService {

	@Autowired
	@Qualifier("unitOfMeasureRepository")
	private UnitOfMeasureRepository unitOfMeasureRepo;

	@Autowired
	@Qualifier("uomConversionService")
	private UomConversionService uomConversionService;

	@Autowired
	@Qualifier("uomConversionRepository")
	private UomConversionRepository unitOfCovRepo;

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public UnitOfMeasureEntity findByUomCode(String uomCode) {
		UnitOfMeasureEntity unitOfMeasureEntity = unitOfMeasureRepo.findByUomCode(uomCode);
		return unitOfMeasureEntity;
	}

	@Override
	protected String getAlternateKeyAsString(UnitOfMeasureEntity unitOfMeasureEntity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"unitOfMeasure\" : \"").append(unitOfMeasureEntity.getUomCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	protected JpaRepository<UnitOfMeasureEntity, Long> getRepository() {
		return unitOfMeasureRepo;
	}

	@Override
	protected ITextRepository<UnitOfMeasureEntity, Long> getTextRepository() {
		return unitOfMeasureRepo;
	}

	@Override
	protected Outcome postProcess(int opCode, UnitOfMeasureEntity entity)
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
	protected Outcome preProcess(int opCode, UnitOfMeasureEntity entity)
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

	private void preProcessAdd(UnitOfMeasureEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(UnitOfMeasureEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
			throw new EntityValidationException(
					"Cannot delete unitOfMeasure when state is " + entity.getLifecycleStatus());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(UnitOfMeasureEntity entity, EntityVdationType type) throws EntityValidationException {
		
		Set<UomConversionEntity> srcConversionList = entity.getSrcConversions();
		if (srcConversionList != null && !srcConversionList.isEmpty()) {
			for (UomConversionEntity srcConversion : srcConversionList) {
				srcConversion.setSrcUomRef(entity);
				srcConversion.setSrcUomCode(entity.getUomCode());
				uomConversionService.validate(srcConversion, false, type);
			}
		}

		Set<UomConversionEntity> destConversionList = entity.getDestConversions();
		if (destConversionList != null && !destConversionList.isEmpty()) {
			for (UomConversionEntity destConversion : destConversionList) {
				destConversion.setDestUomRef(entity);
				destConversion.setDestUomCode(entity.getUomCode());
				uomConversionService.validate(destConversion, false, type);
			}
		}

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
	@Transactional
	public Long requestStatusChange(String uomCode, LifeCycleStatus status) throws EntityValidationException {
		Long result = -1L;
		UnitOfMeasureEntity unitOfMeasureEntity = null;
		try {
			unitOfMeasureEntity = unitOfMeasureRepo.findByUomCode(uomCode);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find UOM
			// with code - " + uomCode);
			// no-op
		}

		if (unitOfMeasureEntity == null)
			throw new EntityValidationException("Could not find UnitOfMeasure with code - " + uomCode);

		if (unitOfMeasureEntity.getLifecycleStatus() == LifeCycleStatus.INACTIVE) {
			if (status != LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					unitOfMeasureEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
					result = 0L;
				}
			}
		} else if (unitOfMeasureEntity.getLifecycleStatus() == LifeCycleStatus.ACTIVE) {
			if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					unitOfMeasureEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to INACTIVE when current status is ACTIVE !");
				}
			} else if (status == LifeCycleStatus.SUSPENDED || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					unitOfMeasureEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
					result = 0L;
				}
			}
		} else if (unitOfMeasureEntity.getLifecycleStatus() == LifeCycleStatus.SUSPENDED) {
			if (status == LifeCycleStatus.ACTIVE || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					unitOfMeasureEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
					result = 0L;
				}
			} else if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					unitOfMeasureEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot change status to INACTIVE when current status is SUSPENDED !");
				}
			}
		} else if (unitOfMeasureEntity.getLifecycleStatus() == LifeCycleStatus.TERMINATED) {
			if (isPrivilegedContext()) {
				unitOfMeasureEntity.setLifecycleStatus(status);
				JpaUtil.updateRevisionControl(unitOfMeasureEntity, true);
				result = 0L;
			} else {
				throw new EntityValidationException(
						"Cannot change status to " + status + " when current status is TERMINATED !");
			}
		}
		return (result);
	}

	@Override
	protected UnitOfMeasureEntity doUpdate(UnitOfMeasureEntity neu, UnitOfMeasureEntity old)
			throws EntityValidationException {

		if (!old.getUomCode().equals(neu.getUomCode())) {
			throw new EntityValidationException("UnitOfMeasureEntity cannot be updated ! Existing - " + old.getUomCode()
					+ ", new - " + neu.getUomCode());
		}

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}
		
		
		ArrayList<Long> deletedDestUomConversions = new ArrayList<>();
		ArrayList<Long> deletedSrcUomConversions = new ArrayList<>();

		if (old.getDestConversions() != null && !old.getDestConversions().isEmpty()) {
			for (UomConversionEntity oldUoc : old.getDestConversions()) {
				boolean foundDest = false;
				
				if (neu.getDestConversions() != null && !neu.getDestConversions().isEmpty()) {
					for (UomConversionEntity newUoc : neu.getDestConversions()) {
						if (Objects.equals(newUoc.getId(), oldUoc.getId())) {
							foundDest = true;
							break;
						}
					} // end for DestUoc
				}
				if (!foundDest) {
					// Conversion deleted
					if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedDestUomConversions.add(oldUoc.getId());
						} else {
							throw new EntityValidationException(
									"Cannot delete UnitOfConversion " + oldUoc.getDestUomCode()
											+ " when UnitOfConversion is " + old.getLifecycleStatus());
						}
					} else {
						deletedDestUomConversions.add(oldUoc.getId());
					}
				}

			} // end for old measure
		}
		
		
		if (old.getSrcConversions() != null && !old.getSrcConversions().isEmpty()) {
			for (UomConversionEntity oldUoc : old.getSrcConversions()) {
				boolean foundDest = false;
				boolean foundSrc = false;
				if (neu.getSrcConversions() != null && !neu.getSrcConversions().isEmpty()) {
					for (UomConversionEntity newUoc : neu.getSrcConversions()) {
						if (Objects.equals(newUoc.getId(), oldUoc.getId())) {
							foundSrc = true;
							break;
						}
					} // end for Src Uoc
				}
				if (!foundSrc) {
					// Conversion deleted
					if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedSrcUomConversions.add(oldUoc.getId());
						} else {
							throw new EntityValidationException(
									"Cannot delete UnitOfConversion " + oldUoc.getDestUomCode()
											+ " when UnitOfConversion is " + old.getLifecycleStatus());
						}
					} else {
						deletedSrcUomConversions.add(oldUoc.getId());
					}
				}
			}
		}
		
		boolean updated = false;
		
		if (!deletedDestUomConversions.isEmpty() || !deletedSrcUomConversions.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}
		
		if (updated) {
			if (isPrivilegedContext()) {
				
				// carry out the update
				updateDestConversions(neu, old);
				
				updateSrcConversions(neu, old);
				
				if (!deletedDestUomConversions.isEmpty()) {
					// dissociate Uoc
					deleteUocEntity(old.getDestConversions(), old, deletedDestUomConversions, "dest");
				} // end if DestUoc not empty

				if (!deletedSrcUomConversions.isEmpty()) {
					// dissociate Uoc
					deleteUocEntity(old.getSrcConversions(), old, deletedSrcUomConversions, "src");
				} // end if srcUoc not empty
				
				old.setStrictConv(neu.isStrictConv());
				old.setUomClass(neu.getUomClass());
				old.setUomCode(neu.getUomCode());
				old.setUomDesc(neu.getUomDesc());
				old.setUomType(neu.getUomType());
				
				JpaUtil.updateRevisionControl(old, true);
				
			} else {
				//TODO: submit a workflow for the changes
			}
		}
		return (old);
	}

	private void updateSrcConversions(UnitOfMeasureEntity neu, UnitOfMeasureEntity old)
			throws EntityValidationException {
		
		if (neu.getSrcConversions() != null && !neu.getSrcConversions().isEmpty()) {
			for (UomConversionEntity newUoc : neu.getSrcConversions()) {
				UomConversionEntity oldUoc2 = null;
				boolean found = false;
				if (old.getSrcConversions() != null && !old.getSrcConversions().isEmpty()) {
					for (UomConversionEntity oldUoc : old.getSrcConversions()) {
						if (Objects.equals(newUoc.getId(), oldUoc.getId())) {
							oldUoc2 = oldUoc;
							found = true;
							break;
						}
					} // end for old.getSrcConversions
				} // end if old.getSrcConversions

				if (!found) {
					// new zone being added
					newUoc.setSrcUomRef(old);
					newUoc.setSrcUomCode(old.getUomCode());
					newUoc.setTenantId(getTenantId());

					uomConversionService.validate(newUoc, false, EntityVdationType.PRE_INSERT);
					old.getSrcConversions().add(newUoc);
				} else {
					boolean uocUpdated = uomConversionService.checkForUpdate(newUoc, oldUoc2);
					if (uocUpdated) {
						uomConversionService.update(newUoc);
					}
				}
			} // end for neu.Uoc
		}
		
	}

	private void updateDestConversions(UnitOfMeasureEntity neu, UnitOfMeasureEntity old)
			throws EntityValidationException {
		
		if (neu.getSrcConversions() != null && !neu.getSrcConversions().isEmpty()) {
			for (UomConversionEntity newUoc : neu.getDestConversions()) {
				UomConversionEntity oldUoc2 = null;
				boolean found = false;
				if (old.getDestConversions() != null && !old.getDestConversions().isEmpty()) {
					for (UomConversionEntity oldUoc : old.getDestConversions()) {
						if (Objects.equals(newUoc.getId(), oldUoc.getId())) {
							oldUoc2 = oldUoc;
							found = true;
							break;
						}
					} // end for old.getDestConversions
				} // end if old.getDestConversions

				if (!found) {
					// new zone being added
					newUoc.setDestUomRef(old);
					newUoc.setDestUomCode(old.getUomCode());
					newUoc.setTenantId(getTenantId());

					uomConversionService.validate(newUoc, false, EntityVdationType.PRE_INSERT);
					old.getDestConversions().add(newUoc);
				} else {
					boolean uocUpdated = uomConversionService.checkForUpdate(newUoc, oldUoc2);
					if (uocUpdated) {
						uomConversionService.update(newUoc);
					}
				}
			} // end for neu.Uoc
		}
		
	}

	private void deleteUocEntity(Set<UomConversionEntity> uomConversions, UnitOfMeasureEntity old,
			ArrayList<Long> deletedUomConversions, String conversionType) {
		
		Iterator<UomConversionEntity> uocEntityIter = uomConversions.iterator();
		for (Long id : deletedUomConversions) {
			boolean found = false;
			UomConversionEntity oldUoc = null;
			while (uocEntityIter.hasNext()) {
				oldUoc = uocEntityIter.next();
				if (Objects.equals(id, oldUoc.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				if (conversionType.equals("src")) {
					old.getSrcConversions().remove(oldUoc);
				} else if (conversionType.equals("dest")) {
					old.getDestConversions().remove(oldUoc);
				}
				uomConversionService.delete(id);
			}
		}
	} // end if srcUoc not empty

	private boolean checkForUpdate(UnitOfMeasureEntity neu, UnitOfMeasureEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getUomCode(), old.getUomCode()) || 
				!Objects.equals(neu.getUomDesc(), old.getUomDesc()) ||
				!Objects.equals(neu.getUomType(), old.getUomType()) ||
				!Objects.equals(neu.getUomClass(), old.getUomClass()) ||
				!Objects.equals(neu.isStrictConv(), old.isStrictConv())
				) {

			result = true;
			return (result);
		}
		
		if (neu.getDestConversions() == null || neu.getDestConversions().isEmpty()) {
			if (old.getDestConversions() != null && !old.getDestConversions().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getDestConversions() == null || old.getDestConversions().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getDestConversions().size(), old.getDestConversions().size())) {
					result = true;
					return (result);
				} else {
					result = checkForUpdateConversions(neu.getDestConversions(), old.getDestConversions());
					return (result);
				}
			}
		}

		if (neu.getSrcConversions() == null || neu.getSrcConversions().isEmpty()) {
			if (old.getSrcConversions() != null && !old.getSrcConversions().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getSrcConversions() == null || old.getSrcConversions().isEmpty()) {
				result = true;
				return (result);
			} else {
				if (!Objects.equals(neu.getDestConversions().size(), old.getDestConversions().size())) {
					result = true;
					return (result);
				} else {
					result = checkForUpdateConversions(neu.getSrcConversions(), old.getSrcConversions());
					return (result);
				}
			}
		}
		
		return result;
	}

	private boolean checkForUpdateConversions(Set<UomConversionEntity> newConvs, Set<UomConversionEntity> oldConvs) {
		
		boolean result = false;
		Iterator<UomConversionEntity> oldUocEntityIter = oldConvs.iterator();
		Iterator<UomConversionEntity> newUocEntityIter = newConvs.iterator();
		UomConversionEntity oldUoc = null;
		UomConversionEntity newUoc = null;

		while (newUocEntityIter.hasNext()) {
			newUoc = newUocEntityIter.next();
			Long newEntityId = newUoc.getId();
			if (newEntityId != null) {
				while (oldUocEntityIter.hasNext()) {
					oldUoc = oldUocEntityIter.next();
					Long oldEntityId = oldUoc.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = uomConversionService.checkForUpdate(newUoc, oldUoc);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = uomConversionService.checkForUpdate(newUoc, oldUoc);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}
	
	
}
