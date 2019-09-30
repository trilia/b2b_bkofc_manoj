package com.olp.jpa.domain.docu.fin.repo;

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
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerStatus;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;
import com.olp.jpa.util.JpaUtil;

@Service("ledgerLineService")
public class LedgerLineServiceImpl extends AbstractServiceImpl<LedgerLineEntity, Long> implements LedgerLineService {

	@Autowired
	@Qualifier("ledgerLineRepository")
	private LedgerLineRepository ledgerLineRepository;

	@Autowired
	@Qualifier("ledgerRepository")
	private LedgerRepository ledgerRepository;

	@Autowired
	@Qualifier("coaAccountsRepository")
	private CoaAccountsRepository coaAccountsRepository;

	@Override
	protected JpaRepository<LedgerLineEntity, Long> getRepository() {
		return ledgerLineRepository;
	}

	@Override
	protected ITextRepository<LedgerLineEntity, Long> getTextRepository() {
		return ledgerLineRepository;
	}

	@Override
	public LedgerLineEntity findbyLedgerLine(String ledgerName, int lineNum) {
		return ledgerLineRepository.findbyLedgerLine(ledgerName, lineNum);
	}

	@Override
	protected String getAlternateKeyAsString(LedgerLineEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"ledgerLines\" : \"").append(entity.getLedgerName()).append("\" }");
		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(LedgerLineEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getLedgerRef() != null) {
				// it is possible to have destination ledger null
				LedgerEntity le = entity.getLedgerRef(), le2 = null;

				if (le.getId() == null) {
					try {
						le2 = ledgerRepository.findbyLedgerName(le.getLedgerName());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find Ledger with name - " + le.getLedgerName());
					}
				} else {
					try {
						le2 = ledgerRepository.findOne(le.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find Ledger with id - " + le.getId());
					}
				}

				if (le2 == null)
					throw new EntityValidationException("Could not find Ledger using either code or id !");

				entity.setLedgerRef(le2);
				entity.setLedgerName(le2.getLedgerName());
			}

			if (entity.getAccountRef() != null) {
				// it is possible to have destination ledger null
				CoaAccountsEntity ae = entity.getAccountRef(), ae2 = null;

				if (ae.getId() == null) {
					try {
						ae2 = coaAccountsRepository.findbyAccountCatg(ae.getAccountCatgCode(),
								ae.getAccountSubCatgCode());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException(
								"Could not find Account with name - " + ae.getAccountCode());
					}
				} else {
					try {
						ae2 = coaAccountsRepository.findOne(ae.getId());
					} catch (javax.persistence.NoResultException ex) {
						throw new EntityValidationException("Could not find CoaAccount with id - " + ae.getId());
					}
				}

				if (ae2 == null)
					throw new EntityValidationException("Could not find CoaAccount using either code or id !");

				entity.setAccountRef(ae2);
				entity.setAccountCode(ae2.getAccountCode());
			}
		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected LedgerLineEntity doUpdate(LedgerLineEntity neu, LedgerLineEntity old) throws EntityValidationException {

		if (!Objects.equals(neu.getLedgerName(), old.getLedgerName()))
			throw new EntityValidationException("The ledger names does not match, existing - " + old.getLedgerName()
					+ " , new - " + neu.getLedgerName());

		if (checkForUpdate(neu, old)) {
			old.setAccountCode(neu.getAccountCode());
			old.setLineAmount(neu.getLineAmount());
			old.setLineDesc(neu.getLineDesc());
			old.setLineNum(neu.getLineNum());
			old.setLineType(neu.getLineType());
			old.setAccountRef(neu.getAccountRef());
			old.setLedgerRef(neu.getLedgerRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(LedgerLineEntity neu, LedgerLineEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getAccountCode(), old.getAccountCode())
				|| !Objects.equals(neu.getLedgerName(), old.getLedgerName())
				|| !Objects.equals(neu.getLineAmount(), old.getLineAmount())
				|| !Objects.equals(neu.getLineDesc(), old.getLineDesc())
				|| !Objects.equals(neu.getLineNum(), old.getLineNum())
				|| !Objects.equals(neu.getLineType(), old.getLineType())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getLedgerRef() == null) {
			if (old.getLedgerRef() != null)
				result = true;
		} else {
			if (old.getLedgerRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getLedgerRef().getId(), old.getLedgerRef().getId())) {
					result = true;
				}
			}
		}
		if (result)
			return (true);

		if (neu.getAccountRef() == null) {
			if (old.getAccountRef() != null)
				result = true;
		} else {
			if (old.getAccountRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getAccountRef().getId(), old.getAccountRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<LedgerLineEntity, Long>.Outcome postProcess(int opCode, LedgerLineEntity entity)
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
	protected AbstractServiceImpl<LedgerLineEntity, Long>.Outcome preProcess(int opCode, LedgerLineEntity entity)
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

	private void preProcessAdd(LedgerLineEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(LedgerLineEntity entity) throws EntityValidationException {
		if (entity.getLedgerRef() != null) {
			LedgerEntity ledger = entity.getLedgerRef();
			if (!isPrivilegedContext() && ledger.getLifecycleStatus() != LedgerStatus.CANCELLED)// status to check																				// checked
				throw new EntityValidationException(
						"Cannot delete ledgerLine when ledgerLine status is " + ledger.getLifecycleStatus());
		}
	}

}
