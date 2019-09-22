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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.util.JpaUtil;

@Service("accountSubCategoryService")
public class AccountSubCategoryServiceImpl extends AbstractServiceImpl<AccountSubCategoryEntity, Long>
		implements AccountSubCategoryService {

	@Autowired
	@Qualifier("accountSubCategoryRepository")
	private AccountSubCategoryRepository accountSubCategoryRepository;

	@Autowired
	@Qualifier("accountCategoryRepository")
	private AccountCategoryRepository accountCategoryRepository;

	@Override
	protected JpaRepository<AccountSubCategoryEntity, Long> getRepository() {
		return accountSubCategoryRepository;
	}

	@Override
	protected ITextRepository<AccountSubCategoryEntity, Long> getTextRepository() {
		return accountSubCategoryRepository;
	}

	@Override
	public AccountSubCategoryEntity findbySubCatgCode(String catgCode, String subCatgCode) {
		AccountSubCategoryEntity entity = accountSubCategoryRepository.findbySubCatgCode(catgCode, subCatgCode);

		return entity;
	}

	@Override
	protected String getAlternateKeyAsString(AccountSubCategoryEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"accountSubCategory\" : \"").append(entity.getSubCategoryCode()).append("\" }");
		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(AccountSubCategoryEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException {
		if (valParent) {
			if (entity.getCategoryRef() != null) {
				// it is possible to have destination ac null
				AccountCategoryEntity ac = entity.getCategoryRef(), ac2 = null;

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

				entity.setCategoryRef(ac2);
				entity.setCategoryCode(ac2.getCategoryCode());

			}

		}

		if (EntityVdationType.PRE_INSERT == type) {
			this.updateTenantWithRevision(entity);
		} else if (EntityVdationType.PRE_UPDATE == type) {
			JpaUtil.updateRevisionControl(entity, true);
		}

	}

	@Override
	protected AccountSubCategoryEntity doUpdate(AccountSubCategoryEntity neu, AccountSubCategoryEntity old)
			throws EntityValidationException {

		if (!Objects.equals(neu.getSubCategoryCode(), old.getSubCategoryCode()))
			throw new EntityValidationException("The sub category does not match, existing - "
					+ old.getSubCategoryCode() + " , new - " + neu.getSubCategoryCode());

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		if (checkForUpdate(neu, old)) {
			old.setCategoryCode(neu.getCategoryCode());
			old.setSubCategoryName(neu.getSubCategoryName());
			old.setCategoryRef(neu.getCategoryRef());

			JpaUtil.updateRevisionControl(old, true);
		}

		return (old);
	}

	@Override
	public boolean checkForUpdate(AccountSubCategoryEntity neu, AccountSubCategoryEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getCategoryCode(), old.getCategoryCode())
				|| !Objects.equals(neu.getSubCategoryName(), old.getSubCategoryName())) {
			result = true;
		}

		if (result)
			return (true);

		if (neu.getCategoryRef() == null) {
			if (old.getCategoryRef() != null)
				result = true;
		} else {
			if (old.getCategoryRef() == null) {
				result = true;
			} else {
				// both not null
				if (!Objects.equals(neu.getCategoryRef().getId(), old.getCategoryRef().getId())) {
					result = true;
				}
			}
		}

		return result;
	}

	@Override
	protected AbstractServiceImpl<AccountSubCategoryEntity, Long>.Outcome postProcess(int opCode,
			AccountSubCategoryEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<AccountSubCategoryEntity, Long>.Outcome preProcess(int opCode,
			AccountSubCategoryEntity entity) throws EntityValidationException {
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

	private void preProcessAdd(AccountSubCategoryEntity entity) throws EntityValidationException {
		validate(entity, false, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(AccountSubCategoryEntity entity) throws EntityValidationException {
		if (entity.getCategoryRef() != null) {
			AccountCategoryEntity accountCategory = entity.getCategoryRef();
			if (!isPrivilegedContext() && accountCategory.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
				throw new EntityValidationException(
						"Cannot delete uom when AccountSubCategory status is " + accountCategory.getLifecycleStatus());
		}
	}

}
