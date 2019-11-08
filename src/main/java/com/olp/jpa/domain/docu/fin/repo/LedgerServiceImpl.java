package com.olp.jpa.domain.docu.fin.repo;

import java.math.BigDecimal;
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
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerLineType;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerStatus;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("ledgerService")
public class LedgerServiceImpl extends AbstractServiceImpl<LedgerEntity, Long> implements LedgerService {

	@Autowired
	@Qualifier("ledgerRepository")
	private LedgerRepository ledgerRepository;

	@Autowired
	@Qualifier("ledgerLineService")
	private LedgerLineService ledgerLineService;

	@Override
	protected JpaRepository<LedgerEntity, Long> getRepository() {
		return ledgerRepository;
	}

	@Override
	protected ITextRepository<LedgerEntity, Long> getTextRepository() {
		return ledgerRepository;
	}

	@Override
	protected String getAlternateKeyAsString(LedgerEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ledger\" : \"").append(entity.getLedgerName()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public LedgerEntity findbyLedgerName(String ledgerName) {
		return ledgerRepository.findbyLedgerName(ledgerName);
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LedgerEntity entity, EntityVdationType type) throws EntityValidationException {
		List<LedgerLineEntity> ledgerLines = entity.getLedgerLines();
		if (ledgerLines != null && !ledgerLines.isEmpty()) {
			for (LedgerLineEntity ledgerLine : ledgerLines) {
				ledgerLine.setLedgerRef(entity);
				ledgerLine.setLedgerName(entity.getLedgerName());
				ledgerLineService.validate(ledgerLine, false, type);
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			entity.setLifecycleStatus(LedgerStatus.NEW);
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			// Status cannot be updated to POSTED, once it is cancelled
			// No updates are allowed if status in POSTED or CANCELLED
			if (entity.getLifecycleStatus() == LedgerStatus.CANCELLED
					|| entity.getLifecycleStatus() == LedgerStatus.POSTED) {
				throw new EntityValidationException(
						"Entity cannot be updated as LifeCycleStatus" + " is " + entity.getLifecycleStatus());

			}

			List<LedgerLineEntity> listOfLedgerLines = entity.getLedgerLines();
			BigDecimal sumOfCreditTypeLineAmount = new BigDecimal(0);
			BigDecimal sumOfDebitTypeLineAmount = new BigDecimal(0);
			for (LedgerLineEntity ledgerLine : listOfLedgerLines) {
				if (ledgerLine.getLineType() != null) {
					if (ledgerLine.getLineType() == LedgerLineType.CREDIT) {
						sumOfCreditTypeLineAmount = sumOfCreditTypeLineAmount.add(ledgerLine.getLineAmount());
					} else if (ledgerLine.getLineType() == LedgerLineType.DEBIT) {
						sumOfDebitTypeLineAmount = sumOfCreditTypeLineAmount.add(ledgerLine.getLineAmount());
					}
				}
			}

			// Status cannot be updated to POSTED if the sum of CREDIT type
			// lineAmount in LedgerLineEntity for
			// the ledger is equal to the sum of DEBIT type lineAmount
			if (sumOfCreditTypeLineAmount == sumOfDebitTypeLineAmount
					&& entity.getLifecycleStatus() == LedgerStatus.POSTED) {
				throw new EntityValidationException(
						"Entity cannot be updated Status cannot be updated to POSTED if the sum of CREDIT type lineAmount in LedgerLineEntity"
								+ "for the ledger is equal to the sum of DEBIT type lineAmount is "
								+ entity.getLifecycleStatus());
			}
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	@Override
	protected AbstractServiceImpl<LedgerEntity, Long>.Outcome postProcess(int opCode, LedgerEntity entity)
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
	protected AbstractServiceImpl<LedgerEntity, Long>.Outcome preProcess(int opCode, LedgerEntity entity)
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

	@Override
	public Long requestStatusChange(String ledgerName, LedgerStatus status) throws EntityValidationException {
		Long result = -1L;
		LedgerEntity ledgerEntity = null;
		try {
			ledgerEntity = ledgerRepository.findbyLedgerName(ledgerName);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find ledger
			// with code - " + ledgerName);
			// no-op
		}

		if (ledgerEntity == null)
			throw new EntityValidationException("Could not find ledger with ledgerName - " + ledgerName);

		if (ledgerEntity.getLifecycleStatus() == LedgerStatus.NEW) {
			if (status != LedgerStatus.NEW) {
				if (isPrivilegedContext()) {
					ledgerEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(ledgerEntity, true);
					result = 0L;
				}
			}
		} else if (ledgerEntity.getLifecycleStatus() == LedgerStatus.POSTED) {
			if (status == LedgerStatus.NEW || status == LedgerStatus.CANCELLED) {
				if (isPrivilegedContext()) {
					ledgerEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(ledgerEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to NEW OR CANCELLED when current status is POSTED !");
				}
			}
		} else if (ledgerEntity.getLifecycleStatus() == LedgerStatus.CANCELLED) {
			if (status == LedgerStatus.NEW || status == LedgerStatus.POSTED) {
				throw new EntityValidationException(
						"Cannot set status to NEW OR POSTED when current status is CANCELLED !");
			}
		}

		return (result);
	}

	@Override
	protected LedgerEntity doUpdate(LedgerEntity neu, LedgerEntity old) throws EntityValidationException {

		if (!old.getLedgerName().equals(neu.getLedgerName())) {
			throw new EntityValidationException("LedgerEntity cannot be updated ! Existing - " + old.getLedgerName()
					+ ", new - " + neu.getLedgerName());
		}

		validate(neu, EntityVdationType.PRE_UPDATE);

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		List<Long> deletedLedgerLines = new ArrayList<Long>();

		if (old.getLedgerLines() != null && !old.getLedgerLines().isEmpty()) {
			for (LedgerLineEntity oldLedgerLine : old.getLedgerLines()) {
				boolean found = false;
				if (neu.getLedgerLines() != null && !neu.getLedgerLines().isEmpty()) {
					for (LedgerLineEntity newLedgerLine : neu.getLedgerLines()) {
						if (Objects.equals(newLedgerLine.getId(), oldLedgerLine.getId())) {
							found = true;
							break;
						}
					} // end for new ledgerLines
				}
				if (!found) {
					// ledgerLines deleted
					deletedLedgerLines.add(oldLedgerLine.getId());
				}
			}
		}

		boolean updated = false;
		if (!deletedLedgerLines.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getLedgerLines() != null && !neu.getLedgerLines().isEmpty()) {
				updateLedgerLines(neu, old); // end for
												// neu.getLedgerLines
			} // end if neu.getLedgerLines != null

			if (!deletedLedgerLines.isEmpty()) {
				// dissociate LedgerLines
				deleteLedgerLines(old, deletedLedgerLines);
			} // end if deletedLedgerLines not empty

			old.setLedgerDesc(neu.getLedgerDesc());
			old.setPostingDate(neu.getPostingDate());
			JpaUtil.updateRevisionControl(old, true);
			// }
		}
		return (old);
	}

	private void preProcessAdd(LedgerEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		String ledgerName = Long.toHexString(Double.doubleToLongBits(Math.random()));
		entity.setLedgerName(ledgerName);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LedgerEntity entity) throws EntityValidationException {
		// if (!isPrivilegedContext() && entity.getLifecycleStatus() !=
		// LedgerStatus.NEW) // todo
		// check
		// throw new EntityValidationException("Cannot delete ledger when state
		// is " + entity.getLifecycleStatus());
	}

	private void updateLedgerLines(LedgerEntity neu, LedgerEntity old) throws EntityValidationException {
		for (LedgerLineEntity newLedgerLine : neu.getLedgerLines()) {
			LedgerLineEntity oldLedgerLine2 = null;
			boolean found = false;
			if (old.getLedgerLines() != null && !old.getLedgerLines().isEmpty()) {
				for (LedgerLineEntity oldLedgerLine : old.getLedgerLines()) {
					if (Objects.equals(newLedgerLine.getId(), oldLedgerLine.getId())) {
						oldLedgerLine2 = oldLedgerLine;
						found = true;
						break;
					}
				} // end for old.getLedgerLines
			} // end if old.getLedgerLines

			if (!found) {
				// new LedgerLines being added
				newLedgerLine.setLedgerRef(old);
				newLedgerLine.setLedgerName(old.getLedgerName());
				newLedgerLine.setTenantId(getTenantId());

				ledgerLineService.validate(newLedgerLine, false, EntityVdationType.PRE_INSERT);
				old.getLedgerLines().add(newLedgerLine);
			} else {
				boolean ledgerLineUpdated = ledgerLineService.checkForUpdate(newLedgerLine, oldLedgerLine2);
				if (ledgerLineUpdated) {
					ledgerLineService.update(newLedgerLine);
				}
			}

		}
	}

	private void deleteLedgerLines(LedgerEntity old, List<Long> deletedLedgerLines) {
		Iterator<LedgerLineEntity> ledgerLinesEntityIter = old.getLedgerLines().iterator();
		for (Long id : deletedLedgerLines) {
			boolean found = false;
			LedgerLineEntity oldLedgerLine = null;
			while (ledgerLinesEntityIter.hasNext()) {
				oldLedgerLine = ledgerLinesEntityIter.next();
				if (Objects.equals(id, oldLedgerLine.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				ledgerLineService.delete(id);
			}
		}
	}

	public boolean checkForUpdate(LedgerEntity neu, LedgerEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getLedgerDesc(), old.getLedgerDesc())
				|| !Objects.equals(neu.getLedgerName(), old.getLedgerName())
				|| !Objects.equals(neu.getPostingDate(), old.getPostingDate())) {

			result = true;
			return (result);
		}

		if (neu.getLedgerLines() == null || neu.getLedgerLines().isEmpty()) {
			if (old.getLedgerLines() != null && !old.getLedgerLines().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getLedgerLines() == null || old.getLedgerLines().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getLedgerLines().size(), old.getLedgerLines().size())) {
					result = true;
					return (result);
				} else {
					result = checkForLedgerLines(neu.getLedgerLines(), old.getLedgerLines());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForLedgerLines(List<LedgerLineEntity> newLedgerLines, List<LedgerLineEntity> oldLedgerLines) {

		boolean result = false;
		Iterator<LedgerLineEntity> oldLedgerLinesEntityIter = oldLedgerLines.iterator();
		Iterator<LedgerLineEntity> newLedgerLinesEntityIter = newLedgerLines.iterator();
		LedgerLineEntity oldLedgerLine = null;
		LedgerLineEntity newLedgerLine = null;

		while (newLedgerLinesEntityIter.hasNext()) {
			newLedgerLine = newLedgerLinesEntityIter.next();
			Long newEntityId = newLedgerLine.getId();
			if (newEntityId != null) {
				while (oldLedgerLinesEntityIter.hasNext()) {
					oldLedgerLine = oldLedgerLinesEntityIter.next();
					Long oldEntityId = oldLedgerLine.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = ledgerLineService.checkForUpdate(newLedgerLine, oldLedgerLine);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = ledgerLineService.checkForUpdate(newLedgerLine, oldLedgerLine);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

}
