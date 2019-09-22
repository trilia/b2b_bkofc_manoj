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
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;
import com.olp.jpa.util.JpaUtil;

@Service("CoaAccountsService")
public class CoaAccountsServiceImpl extends AbstractServiceImpl<CoaAccountsEntity, Long> implements CoaAccountsService {

	@Autowired
	@Qualifier("coaAccountsRepository")
	private CoaAccountsRepository coaAccountsRepository;

	@Autowired
	@Qualifier("chartOfAccountsRepository")
	private ChartOfAccountsRepository chartOfAccountsRepository;

	@Autowired
	@Qualifier("accountCategoryRepository")
	private AccountCategoryRepository accountCategoryRepository;

	@Autowired
	@Qualifier("accountSubCategoryRepository")
	private AccountSubCategoryRepository accountSubCategoryRepository;

	@Override
	protected JpaRepository<CoaAccountsEntity, Long> getRepository() {
		return coaAccountsRepository;
	}

	@Override
	protected ITextRepository<CoaAccountsEntity, Long> getTextRepository() {
		return coaAccountsRepository;
	}

	@Override
	protected String getAlternateKeyAsString(CoaAccountsEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"CoaAccounts\" : \"").append(entity.getAccountCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	public CoaAccountsEntity findbyAccountCatg(String catgCode, String subCatgCode) {
		return coaAccountsRepository.findbyAccountCatg(catgCode, subCatgCode);
	}

	@Override
	protected AbstractServiceImpl<CoaAccountsEntity, Long>.Outcome postProcess(int opCode, CoaAccountsEntity entity)
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
	protected AbstractServiceImpl<CoaAccountsEntity, Long>.Outcome preProcess(int opCode, CoaAccountsEntity entity)
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
			break;
		default:
			break;
		}

		return (result);
	}

	@Override
	protected CoaAccountsEntity doUpdate(CoaAccountsEntity neu, CoaAccountsEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getAccountCode(), old.getAccountCode()))
			throw new EntityValidationException(
					"The coaaccounts does not match, existing - " + old.getCoaCode() + " , new - " + neu.getCoaCode());

		if (checkForUpdate(neu, old)) {
			old.setAccountCatgCode(neu.getAccountCatgCode());
			old.setAccountCatgRef(neu.getAccountCatgRef());
			old.setAccountName(neu.getAccountName());
			old.setAccountSubCatgCode(neu.getAccountSubCatgCode());
			old.setAccountSubCatgRef(neu.getAccountSubCatgRef());
			old.setChild(neu.isChild());
			old.setCoaCode(neu.getCoaCode());
			old.setCoaRef(neu.getCoaRef());
			old.setEnabled(neu.isEnabled());
			old.setParentAccountCode(neu.getParentAccountCode());
			old.setParentAccountRef(neu.getParentAccountRef());
			old.setSegment1LovCode(neu.getSegment1LovCode());
			old.setSegment1Value(neu.getSegment1Value());
			old.setSegment2LovCode(neu.getSegment2LovCode());
			old.setSegment2Value(neu.getSegment2Value());
			old.setSegment3LovCode(neu.getSegment3LovCode());
			old.setSegment3Value(neu.getSegment3Value());
			old.setSegment4LovCode(neu.getSegment4LovCode());
			old.setSegment4Value(neu.getSegment4Value());
			old.setSegment5LovCode(neu.getSegment5LovCode());
			old.setSegment5Value(neu.getSegment5Value());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(CoaAccountsEntity neu, CoaAccountsEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getAccountCatgCode(), old.getAccountCatgCode())
				|| !Objects.equals(neu.getAccountName(), old.getAccountName())
				|| !Objects.equals(neu.getAccountSubCatgCode(), old.getAccountSubCatgCode())
				|| !Objects.equals(neu.isChild(), old.isChild()) || !Objects.equals(neu.getCoaCode(), old.getCoaCode())
				|| !Objects.equals(neu.isEnabled(), old.isEnabled())
				|| !Objects.equals(neu.getParentAccountCode(), old.getParentAccountCode())
				|| !Objects.equals(neu.getSegment1LovCode(), old.getSegment1LovCode())
				|| !Objects.equals(neu.getSegment1Value(), old.getSegment1Value())
				|| !Objects.equals(neu.getSegment2LovCode(), old.getSegment2LovCode())
				|| !Objects.equals(neu.getSegment2Value(), old.getSegment2Value())
				|| !Objects.equals(neu.getSegment3LovCode(), old.getSegment3LovCode())
				|| !Objects.equals(neu.getSegment3Value(), old.getSegment3Value())
				|| !Objects.equals(neu.getSegment4LovCode(), old.getSegment4LovCode())
				|| !Objects.equals(neu.getSegment4Value(), old.getSegment4Value())
				|| !Objects.equals(neu.getSegment5LovCode(), old.getSegment5LovCode())
				|| !Objects.equals(neu.getSegment5Value(), old.getSegment5Value())) {
			result = true;
		}

		if (result)
			return (true);

		// check if accountCatgeory changed
		if (neu.getAccountCatgRef() == null) {
			if (old.getAccountCatgRef() != null)
				result = true;
		} else {
			if (old.getAccountCatgRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getAccountCatgRef().getId(), old.getAccountCatgRef().getId())) {
					result = true;
				}
			}
		}

		// check if accountSubCategory changed
		if (neu.getAccountSubCatgRef() == null) {
			if (old.getAccountSubCatgRef() != null)
				result = true;
		} else {
			if (old.getAccountSubCatgRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getAccountSubCatgRef().getId(), old.getAccountSubCatgRef().getId())) {
					result = true;
				}
			}
		}

		// check if coaRef changed
		if (neu.getCoaRef() == null) {
			if (old.getCoaRef() != null)
				result = true;
		} else {
			if (old.getCoaRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCoaRef().getId(), old.getCoaRef().getId())) {
					result = true;
				}
			}
		}

		// check if parentAcount changed
		if (neu.getParentAccountRef() == null) {
			if (old.getParentAccountRef() != null)
				result = true;
		} else {
			if (old.getParentAccountRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getParentAccountRef().getId(), old.getParentAccountRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(CoaAccountsEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			validateLinkedAccountCategory(entity);
			validateLinkedAccountSubCategory(entity);
			validateLinkedChartOfAccounts(entity);
			validateLinkedParentAccount(entity);
		}
		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}
	}

	private void validateLinkedAccountSubCategory(CoaAccountsEntity entity) throws EntityValidationException {
		if (entity.getAccountSubCatgRef() != null) {
			// it is possible to have destination asc null
			AccountSubCategoryEntity asc = entity.getAccountSubCatgRef(), asc2 = null;

			if (asc.getId() == null) {
				try {
					asc2 = accountSubCategoryRepository.findbySubCatgCode(asc.getCategoryCode(),
							asc.getSubCategoryCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find AccountSubCategory with code - " + asc.getCategoryCode());
				}
			} else {
				try {
					asc2 = accountSubCategoryRepository.findOne(asc.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find AccountSubCategory with id - " + asc.getId());
				}
			}

			if (asc2 == null)
				throw new EntityValidationException("Could not find AccountSubCategory using either code or id !");

			entity.setAccountSubCatgRef(asc2);
			entity.setAccountSubCatgCode(asc2.getSubCategoryCode());
		}
	}

	private void validateLinkedAccountCategory(CoaAccountsEntity entity) throws EntityValidationException {
		if (entity.getAccountCatgRef() != null) {
			// it is possible to have destination ac null
			AccountCategoryEntity ac = entity.getAccountCatgRef(), ac2 = null;

			if (ac.getId() == null) {
				try {
					ac2 = accountCategoryRepository.findbyCategoryCode(ac.getCategoryCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find AccountCategory with code - " + ac.getCategoryCode());
				}
			} else {
				try {
					ac2 = accountCategoryRepository.findOne(ac.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find AccountCategory with id - " + ac.getId());
				}
			}

			if (ac2 == null)
				throw new EntityValidationException("Could not find AccountCategory using either code or id !");

			entity.setAccountCatgRef(ac2);
			entity.setAccountCatgCode(ac2.getCategoryCode());
		}
	}

	private void validateLinkedChartOfAccounts(CoaAccountsEntity entity) throws EntityValidationException {
		if (entity.getAccountSubCatgRef() != null) {
			// it is possible to have destination coa null
			ChartOfAccountsEntity coa = entity.getCoaRef(), coa2 = null;

			if (coa.getId() == null) {
				try {
					coa2 = chartOfAccountsRepository.findbyCoaCode(coa.getCoaCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find ChartOfAccounts with code - " + coa.getCoaCode());
				}
			} else {
				try {
					coa2 = chartOfAccountsRepository.findOne(coa.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find ChartOfAccounts with id - " + coa.getId());
				}
			}

			if (coa2 == null)
				throw new EntityValidationException("Could not find ChartOfAccounts using either code or id !");

			entity.setCoaRef(coa2);
			entity.setCoaCode(coa2.getCoaCode());
		}
	}

	private void validateLinkedParentAccount(CoaAccountsEntity entity) throws EntityValidationException {
		if (entity.getAccountSubCatgRef() != null) {
			// it is possible to have destination coa null
			CoaAccountsEntity coaParent = entity.getParentAccountRef(), coaParent2 = null;

			if (coaParent.getId() == null) {
				try {
					coaParent2 = coaAccountsRepository.findbyAccountCatg(coaParent.getAccountCatgCode(),
							coaParent.getAccountSubCatgCode());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException(
							"Could not find parentAccount with code - " + coaParent.getCoaCode());
				}
			} else {
				try {
					coaParent2 = coaAccountsRepository.findOne(coaParent.getId());
				} catch (javax.persistence.NoResultException ex) {
					throw new EntityValidationException("Could not find parentAccount with id - " + coaParent.getId());
				}
			}

			if (coaParent2 == null)
				throw new EntityValidationException("Could not find parentAccount using either code or id !");

			entity.setParentAccountRef(coaParent2);
			entity.setParentAccountCode(coaParent2.getAccountCode());
		}
	}

	private void preProcessAdd(CoaAccountsEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(CoaAccountsEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext()) {
			throw new EntityValidationException("Cannot delete CoaAccounts  " + entity.getCoaCode());
		}
	}

}
