package com.olp.jpa.domain.docu.llty.repo;

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
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;
import com.olp.jpa.util.JpaUtil;

@Service("loyaltyProgramService")
public class LoyaltyProgramServiceImpl extends AbstractServiceImpl<LoyaltyProgramEntity, Long>
		implements LoyaltyProgramService {

	@Autowired
	@Qualifier("loyaltyProgramRepository")
	private LoyaltyProgramRepository loyaltyProgramRepository;

	@Autowired
	@Qualifier("programTierService")
	private ProgramTierService programTierService;

	@Override
	protected JpaRepository<LoyaltyProgramEntity, Long> getRepository() {
		return loyaltyProgramRepository;
	}

	@Override
	protected ITextRepository<LoyaltyProgramEntity, Long> getTextRepository() {
		return loyaltyProgramRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LoyaltyProgramEntity findByProgramCode(String programCode) {
		return loyaltyProgramRepository.findByProgramCode(programCode);
	}

	@Override
	protected String getAlternateKeyAsString(LoyaltyProgramEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"loyaltyProgram\" : \"").append(entity.getProgramCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LoyaltyProgramEntity entity, EntityVdationType type) throws EntityValidationException {
		List<ProgramTierEntity> programTiers = entity.getProgramTiers();
		if (programTiers != null && !programTiers.isEmpty()) {
			for (ProgramTierEntity programTier : programTiers) {
				programTier.setProgramRef(entity);
				programTier.setProgramCode(entity.getProgramCode());
				programTierService.validate(programTier, false, type);
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			entity.setLifecycleStatus(LifecycleStatus.INACTIVE);
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {

			// this is a dummy update of lifecycle status, we do not use this
			// field for actual
			// update. This is needed only to get past the required attribute
			// checker.
			entity.setLifecycleStatus(LifecycleStatus.INACTIVE);
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	public Long requestStatusChange(String programCode, LifecycleStatus status) throws EntityValidationException {
		Long result = -1L;
		LoyaltyProgramEntity loyaltyProgramEntity = null;
		try {
			loyaltyProgramEntity = loyaltyProgramRepository.findByProgramCode(programCode);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find
			// loyaltyProgram
			// with code - " + programCode);
			// no-op
		}

		if (loyaltyProgramEntity == null)
			throw new EntityValidationException("Could not find loyaltyProgram with programCode - " + programCode);

		if (loyaltyProgramEntity.getLifecycleStatus() == LifecycleStatus.INACTIVE) {
			if (status != LifecycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					loyaltyProgramEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
					result = 0L;
				}
			}
		} else if (loyaltyProgramEntity.getLifecycleStatus() == LifecycleStatus.ACTIVE) {
			if (status == LifecycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					loyaltyProgramEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to INACTIVE when current status is ACTIVE !");
				}
			} else if (status == LifecycleStatus.SUSPENDED || status == LifecycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					loyaltyProgramEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
					result = 0L;
				}
			}
		} else if (loyaltyProgramEntity.getLifecycleStatus() == LifecycleStatus.SUSPENDED) {
			if (status == LifecycleStatus.ACTIVE || status == LifecycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					loyaltyProgramEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
					result = 0L;
				}
			} else if (status == LifecycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					loyaltyProgramEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot change status to INACTIVE when current status is SUSPENDED !");
				}
			}
		} else if (loyaltyProgramEntity.getLifecycleStatus() == LifecycleStatus.TERMINATED) {
			if (isPrivilegedContext()) {
				loyaltyProgramEntity.setLifecycleStatus(status);
				JpaUtil.updateRevisionControl(loyaltyProgramEntity, true);
				result = 0L;
			} else {
				throw new EntityValidationException(
						"Cannot change status to " + status + " when current status is TERMINATED !");
			}
		}
		return (result);
	}

	@Override
	protected AbstractServiceImpl<LoyaltyProgramEntity, Long>.Outcome postProcess(int opCode,
			LoyaltyProgramEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<LoyaltyProgramEntity, Long>.Outcome preProcess(int opCode,
			LoyaltyProgramEntity entity) throws EntityValidationException {
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
	protected LoyaltyProgramEntity doUpdate(LoyaltyProgramEntity neu, LoyaltyProgramEntity old)
			throws EntityValidationException {

		if (!old.getProgramCode().equals(neu.getProgramCode())) {
			throw new EntityValidationException("LoyaltyProgram cannot be updated ! Existing - " + old.getProgramCode()
					+ ", new - " + neu.getProgramCode());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		List<Long> deletedProgramTiers = new ArrayList<Long>();

		if (old.getProgramTiers() != null && !old.getProgramTiers().isEmpty()) {
			for (ProgramTierEntity oldProgramTier : old.getProgramTiers()) {
				boolean found = false;
				if (neu.getProgramTiers() != null && !neu.getProgramTiers().isEmpty()) {
					for (ProgramTierEntity newProgramTier : neu.getProgramTiers()) {
						if (Objects.equals(newProgramTier.getId(), oldProgramTier.getId())) {
							found = true;
							break;
						}
					} // end for new programTier
				}
				if (!found) {
					// programTier deleted
					if (old.getLifecycleStatus() != LifecycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedProgramTiers.add(oldProgramTier.getId());
						} else {
							throw new EntityValidationException(
									"Cannot delete programTier  " + oldProgramTier.getProgramCode()
											+ " when lifeCycle is " + old.getLifecycleStatus());
						}
					} else {
						deletedProgramTiers.add(oldProgramTier.getId());
					}
				}
			}
		}

		boolean updated = false;
		if (!deletedProgramTiers.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getProgramTiers() != null && !neu.getProgramTiers().isEmpty()) {
				updateProgramTiers(neu, old); // end for
												// neu.getProgramTiers
			} // end if neu.getProgramTiers != null

			if (!deletedProgramTiers.isEmpty()) {
				// dissociate programTier
				deleteProgramTiers(old, deletedProgramTiers);
			} // end if programTier not empty

			old.setDescription(neu.getDescription());
			old.setEffectiveFrom(neu.getEffectiveFrom());
			old.setEffectiveUpto(neu.getEffectiveUpto());
			old.setProgramCode(neu.getProgramCode());
			old.setProgramName(neu.getProgramName());
			old.setProgramScope(neu.getProgramScope());
			old.setSpendConvAlgo(neu.getSpendConvAlgo());
			old.setTierMoveType(neu.getTierMoveType());
			old.setTierReviewFreq(neu.getTierReviewFreq());
			old.setTierValidity(neu.getTierValidity());
			JpaUtil.updateRevisionControl(old, true);
		}
		return (old);
	}

	private void updateProgramTiers(LoyaltyProgramEntity neu, LoyaltyProgramEntity old)
			throws EntityValidationException {
		for (ProgramTierEntity newProgramTier : neu.getProgramTiers()) {
			ProgramTierEntity oldProgramTier2 = null;
			boolean found = false;
			if (old.getProgramTiers() != null && !old.getProgramTiers().isEmpty()) {
				for (ProgramTierEntity oldProgramTier : old.getProgramTiers()) {
					if (Objects.equals(newProgramTier.getId(), oldProgramTier.getId())) {
						oldProgramTier2 = oldProgramTier;
						found = true;
						break;
					}
				} // end for old.getProgramTiers
			} // end if old.getProgramTiers

			if (!found) {
				// new ProgramTier being added
				newProgramTier.setProgramRef(old);
				newProgramTier.setProgramCode(old.getProgramCode());
				newProgramTier.setRevisionControl(getRevisionControl());
				newProgramTier.setTenantId(getTenantId());

				programTierService.validate(newProgramTier, false, EntityVdationType.PRE_INSERT);
				old.getProgramTiers().add(newProgramTier);
			} else {
				boolean programTierUpdated = programTierService.checkForUpdate(newProgramTier, oldProgramTier2);
				if (programTierUpdated) {
					programTierService.update(newProgramTier);
				}
			}

		}
	}

	private void deleteProgramTiers(LoyaltyProgramEntity old, List<Long> deletedProgramTiers) {
		Iterator<ProgramTierEntity> programTierEntityIter = old.getProgramTiers().iterator();
		for (Long id : deletedProgramTiers) {
			boolean found = false;
			ProgramTierEntity oldProgramTier = null;
			while (programTierEntityIter.hasNext()) {
				oldProgramTier = programTierEntityIter.next();
				if (Objects.equals(id, oldProgramTier.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getProgramTiers().remove(oldProgramTier);
				programTierService.delete(id);
			}
		}
	}

	private boolean checkForUpdate(LoyaltyProgramEntity neu, LoyaltyProgramEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getDescription(), old.getDescription())
				|| !Objects.equals(neu.getEffectiveFrom(), old.getEffectiveFrom())
				|| !Objects.equals(neu.getEffectiveUpto(), old.getEffectiveUpto())
				|| !Objects.equals(neu.getProgramCode(), old.getProgramCode())
				|| !Objects.equals(neu.getProgramName(), old.getProgramName())
				|| !Objects.equals(neu.getProgramScope(), old.getProgramScope())
				|| !Objects.equals(neu.getSpendConvAlgo(), old.getSpendConvAlgo())
				|| !Objects.equals(neu.getTierMoveType(), old.getTierMoveType())
				|| !Objects.equals(neu.getTierReviewFreq(), old.getTierReviewFreq())
				|| !Objects.equals(neu.getTierValidity(), old.getTierValidity())) {

			result = true;
			return (result);
		}

		if (neu.getProgramTiers() == null || neu.getProgramTiers().isEmpty()) {
			if (old.getProgramTiers() != null && !old.getProgramTiers().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getProgramTiers() == null || old.getProgramTiers().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getProgramTiers().size(), old.getProgramTiers().size())) {
					result = true;
					return (result);
				} else {
					result = checkForProgramTiers(neu.getProgramTiers(), old.getProgramTiers());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForProgramTiers(List<ProgramTierEntity> newProgramTiers,
			List<ProgramTierEntity> oldProgramTiers) {

		boolean result = false;
		Iterator<ProgramTierEntity> oldProgamTierEntityIter = oldProgramTiers.iterator();
		Iterator<ProgramTierEntity> newProgramTierEntityIter = newProgramTiers.iterator();
		ProgramTierEntity oldProgramTier = null;
		ProgramTierEntity newProgramTier = null;

		while (newProgramTierEntityIter.hasNext()) {
			newProgramTier = newProgramTierEntityIter.next();
			Long newEntityId = newProgramTier.getId();
			if (newEntityId != null) {
				while (oldProgamTierEntityIter.hasNext()) {
					oldProgramTier = oldProgamTierEntityIter.next();
					Long oldEntityId = oldProgramTier.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = programTierService.checkForUpdate(newProgramTier, oldProgramTier);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = programTierService.checkForUpdate(newProgramTier, oldProgramTier);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

	private void preProcessAdd(LoyaltyProgramEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LoyaltyProgramEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifecycleStatus.ACTIVE)
			throw new EntityValidationException(
					"Cannot delete loyaltyProgram when state is " + entity.getLifecycleStatus());
	}

}
