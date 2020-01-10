package com.olp.jpa.domain.docu.fin.repo;

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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.util.JpaUtil;

@Service("accountCategoryService")
public class AccountCategoryServiceImpl extends AbstractServiceImpl<AccountCategoryEntity, Long>
		implements AccountCategoryService {

	@Autowired
	@Qualifier("accountCategoryRepository")
	private AccountCategoryRepository accountCategoryRepository;

	@Autowired
	@Qualifier("accountSubCategoryService")
	private AccountSubCategoryService accountSubCategoryService;

	@Override
	protected JpaRepository<AccountCategoryEntity, Long> getRepository() {
		return accountCategoryRepository;
	}

	@Override
	protected ITextRepository<AccountCategoryEntity, Long> getTextRepository() {
		return accountCategoryRepository;
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountCategoryEntity findbyCategoryCode(String catgCode) {
		AccountCategoryEntity entity = accountCategoryRepository.findbyCategoryCode(catgCode);
		return entity;
	}

	@Override
	protected String getAlternateKeyAsString(AccountCategoryEntity entity) {
		StringBuilder buff = new StringBuilder();
		buff.append("{ \"accountCategory\" : \"").append(entity.getCategoryCode()).append("\" }");

		return (buff.toString());
	}

	@Override
	@Transactional(readOnly = true, noRollbackFor = { javax.persistence.NoResultException.class })
	public void validate(AccountCategoryEntity entity, EntityVdationType type) throws EntityValidationException {
		List<AccountSubCategoryEntity> accountSubCategories = entity.getAccountSubCategories();
		if (accountSubCategories != null && !accountSubCategories.isEmpty()) {
			for (AccountSubCategoryEntity accountSubCategory : accountSubCategories) {
				accountSubCategory.setCategoryRef(entity);
				accountSubCategory.setCategoryCode(entity.getCategoryCode());
				accountSubCategoryService.validate(accountSubCategory, false, type);
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
	public Long requestStatusChange(String categoryCode, LifeCycleStatus status) throws EntityValidationException {
		Long result = -1L;
		AccountCategoryEntity accountCategoryEntity = null;
		try {
			accountCategoryEntity = accountCategoryRepository.findbyCategoryCode(categoryCode);
		} catch (javax.persistence.NoResultException ex) {
			// throw new EntityValidationException("Could not find AccountCategory
			// with code - " + categoryCode);
			// no-op
		}

		if (accountCategoryEntity == null)
			throw new EntityValidationException("Could not find accountCategory with categoryCode - " + categoryCode);

		if (accountCategoryEntity.getLifecycleStatus() == LifeCycleStatus.INACTIVE) {
			if (status != LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					accountCategoryEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(accountCategoryEntity, true);
					result = 0L;
				}
			}
		} else if (accountCategoryEntity.getLifecycleStatus() == LifeCycleStatus.ACTIVE) {
			if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					accountCategoryEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(accountCategoryEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot set status to INACTIVE when current status is ACTIVE !");
				}
			} else if (status == LifeCycleStatus.SUSPENDED || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					accountCategoryEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(accountCategoryEntity, true);
					result = 0L;
				}
			}
		} else if (accountCategoryEntity.getLifecycleStatus() == LifeCycleStatus.SUSPENDED) {
			if (status == LifeCycleStatus.ACTIVE || status == LifeCycleStatus.TERMINATED) {
				if (isPrivilegedContext()) {
					accountCategoryEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(accountCategoryEntity, true);
					result = 0L;
				}
			} else if (status == LifeCycleStatus.INACTIVE) {
				if (isPrivilegedContext()) {
					accountCategoryEntity.setLifecycleStatus(status);
					JpaUtil.updateRevisionControl(accountCategoryEntity, true);
					result = 0L;
				} else {
					throw new EntityValidationException(
							"Cannot change status to INACTIVE when current status is SUSPENDED !");
				}
			}
		} else if (accountCategoryEntity.getLifecycleStatus() == LifeCycleStatus.TERMINATED) {
			if (isPrivilegedContext()) {
				accountCategoryEntity.setLifecycleStatus(status);
				JpaUtil.updateRevisionControl(accountCategoryEntity, true);
				result = 0L;
			} else {
				throw new EntityValidationException(
						"Cannot change status to " + status + " when current status is TERMINATED !");
			}
		}
		return (result);
	}

	@Override
	protected AbstractServiceImpl<AccountCategoryEntity, Long>.Outcome postProcess(int opCode,
			AccountCategoryEntity entity) throws EntityValidationException {
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
	protected AbstractServiceImpl<AccountCategoryEntity, Long>.Outcome preProcess(int opCode,
			AccountCategoryEntity entity) throws EntityValidationException {
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
	protected AccountCategoryEntity doUpdate(AccountCategoryEntity neu, AccountCategoryEntity old)
			throws EntityValidationException {

		if (!old.getCategoryCode().equals(neu.getCategoryCode())) {
			throw new EntityValidationException("AccountCategory cannot be updated ! Existing - " + old.getCategoryCode()
					+ ", new - " + neu.getCategoryCode());
		}

		// validate(neu, EntityVdationType.PRE_UPDATE);

		if (!Objects.equals(neu.getLifecycleStatus(), old.getLifecycleStatus())) {
			if (!isPrivilegedContext())
				throw new EntityValidationException("Cannot update status. Use requestStatusChange api instead !!");
		}

		List<Long> deletedAccountSubCategories = new ArrayList<Long>();

		if (old.getAccountSubCategories() != null && !old.getAccountSubCategories().isEmpty()) {
			for (AccountSubCategoryEntity oldAccountSubCategory : old.getAccountSubCategories()) {
				boolean found = false;
				if (neu.getAccountSubCategories() != null && !neu.getAccountSubCategories().isEmpty()) {
					for (AccountSubCategoryEntity newAccountSubCategory : neu.getAccountSubCategories()) {
						if (Objects.equals(newAccountSubCategory.getId(), oldAccountSubCategory.getId())) {
							found = true;
							break;
						}
					} // end for new accountSubCategory
				}
				if (!found) {
					// accountSubCategory deleted
					if (old.getLifecycleStatus() != LifeCycleStatus.INACTIVE) {
						if (isPrivilegedContext()) {
							deletedAccountSubCategories.add(oldAccountSubCategory.getId());
						} else {
							throw new EntityValidationException(
									"Cannot delete AccountSubCategory  " + oldAccountSubCategory.getCategoryCode()
											+ " when lifeCycle is " + old.getLifecycleStatus());
						}
					} else {
						deletedAccountSubCategories.add(oldAccountSubCategory.getId());
					}
				}
			}
		}

		boolean updated = false;
		if (!deletedAccountSubCategories.isEmpty()) {
			updated = true;
		} else {
			updated = checkForUpdate(neu, old);
		}

		if (updated) {
			// if (isPrivilegedContext()) {
			// carry out the update
			if (neu.getAccountSubCategories() != null && !neu.getAccountSubCategories().isEmpty()) {
				updateAccountSubCategories(neu, old); // end for
														// neu.getAccountSubCategories
			} // end if neu.getAccountSubCategories != null

			if (!deletedAccountSubCategories.isEmpty()) {
				// dissociate AccountSubCategories
				deleteAccountSubCategories(old.getAccountSubCategories(), old, deletedAccountSubCategories);
			} // end if deletedAccountSubCategories not empty

			old.setAccountClass(neu.getAccountClass());
			old.setCategoryName(neu.getCategoryName());
			JpaUtil.updateRevisionControl(old, true);
			// }
		}
		return (old);
	}

	private void preProcessAdd(AccountCategoryEntity entity) throws EntityValidationException {
		validate(entity, EntityVdationType.PRE_INSERT);
		this.updateTenantWithRevision(entity);
	}

	private void preDelete(AccountCategoryEntity entity) throws EntityValidationException {
		if (!isPrivilegedContext() && entity.getLifecycleStatus() != LifeCycleStatus.INACTIVE)
			throw new EntityValidationException(
					"Cannot delete accountCategory when state is " + entity.getLifecycleStatus());
	}

	private void updateAccountSubCategories(AccountCategoryEntity neu, AccountCategoryEntity old)
			throws EntityValidationException {
		for (AccountSubCategoryEntity newAccountSubCategory : neu.getAccountSubCategories()) {
			AccountSubCategoryEntity oldAccountSubCategory2 = null;
			boolean found = false;
			if (old.getAccountSubCategories() != null && !old.getAccountSubCategories().isEmpty()) {
				for (AccountSubCategoryEntity oldAccountSubCategory : old.getAccountSubCategories()) {
					if (Objects.equals(newAccountSubCategory.getId(), oldAccountSubCategory.getId())) {
						oldAccountSubCategory2 = oldAccountSubCategory;
						found = true;
						break;
					}
				} // end for old.getAccountSubCategories
			} // end if old.getAccountSubCategories

			if (!found) {
				// new AccountSubCategory being added
				newAccountSubCategory.setCategoryRef(old);
				newAccountSubCategory.setCategoryCode(old.getCategoryCode());
				newAccountSubCategory.setRevisionControl(getRevisionControl());
				newAccountSubCategory.setTenantId(getTenantId());

				accountSubCategoryService.validate(newAccountSubCategory, false, EntityVdationType.PRE_INSERT);
				old.getAccountSubCategories().add(newAccountSubCategory);
			} else {
				boolean accountSubCategoryUpdated = accountSubCategoryService.checkForUpdate(newAccountSubCategory,
						oldAccountSubCategory2);
				if (accountSubCategoryUpdated) {
					accountSubCategoryService.update(newAccountSubCategory);
				}
			}

		}
	}

	private void deleteAccountSubCategories(List<AccountSubCategoryEntity> accountSubCategories,
			AccountCategoryEntity old, List<Long> deletedAccountSubCategories) {
		Iterator<AccountSubCategoryEntity> accountSubCategoryEntityIter = old.getAccountSubCategories().iterator();
		for (Long id : deletedAccountSubCategories) {
			boolean found = false;
			AccountSubCategoryEntity oldAccountSubCategory = null;
			while (accountSubCategoryEntityIter.hasNext()) {
				oldAccountSubCategory = accountSubCategoryEntityIter.next();
				if (Objects.equals(id, oldAccountSubCategory.getId())) {
					found = true;
					break;
				}
			}
			if (found) {
				old.getAccountSubCategories().remove(oldAccountSubCategory);
				accountSubCategoryService.delete(id);
			}
		}
	}

	private boolean checkForUpdate(AccountCategoryEntity neu, AccountCategoryEntity old) {
		boolean result = false;

		if (!Objects.equals(neu.getAccountClass(), old.getAccountClass())
				|| !Objects.equals(neu.getCategoryCode(), old.getCategoryCode())
				|| !Objects.equals(neu.getCategoryName(), old.getCategoryName())) {

			result = true;
			return (result);
		}

		if (neu.getAccountSubCategories() == null || neu.getAccountSubCategories().isEmpty()) {
			if (old.getAccountSubCategories() != null && !old.getAccountSubCategories().isEmpty()) {
				result = true;
				return (result);
			}
		} else {
			if (old.getAccountSubCategories() == null || old.getAccountSubCategories().isEmpty()) {
				result = true;
				return (result);
			} else {
				// both are not null
				if (!Objects.equals(neu.getAccountSubCategories().size(), old.getAccountSubCategories().size())) {
					result = true;
					return (result);
				} else {
					result = checkForAccountSubCategories(neu.getAccountSubCategories(), old.getAccountSubCategories());
					return (result);
				}
			}
		}

		return result;
	}

	private boolean checkForAccountSubCategories(List<AccountSubCategoryEntity> newAccountSubCategories,
			List<AccountSubCategoryEntity> oldAccountSubCategories) {

		boolean result = false;
		Iterator<AccountSubCategoryEntity> oldAscEntityIter = oldAccountSubCategories.iterator();
		Iterator<AccountSubCategoryEntity> newAscEntityIter = newAccountSubCategories.iterator();
		AccountSubCategoryEntity oldAccountSubCategory = null;
		AccountSubCategoryEntity newAccountSubCategory = null;

		while (newAscEntityIter.hasNext()) {
			newAccountSubCategory = newAscEntityIter.next();
			Long newEntityId = newAccountSubCategory.getId();
			if (newEntityId != null) {
				while (oldAscEntityIter.hasNext()) {
					oldAccountSubCategory = oldAscEntityIter.next();
					Long oldEntityId = oldAccountSubCategory.getId();
					if (Objects.equals(newEntityId, oldEntityId)) {
						boolean outcome = accountSubCategoryService.checkForUpdate(newAccountSubCategory,
								oldAccountSubCategory);
						if (!outcome) {
							result = true;
							return result;
						}
					}
					break;
				}
			} else {
				boolean outcome = accountSubCategoryService.checkForUpdate(newAccountSubCategory,
						oldAccountSubCategory);
				if (!outcome) {
					result = true;
					return result;
				}
			}
		}
		return result;
	}

}
