package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;

@NoRepositoryBean
public interface AccountCategoryService extends IJpaService<AccountCategoryEntity, Long> {
	public AccountCategoryEntity findbyCategoryCode(String catgCode);

	public void validate(AccountCategoryEntity entity, EntityVdationType type) throws EntityValidationException;

	public Long requestStatusChange(String uomCode, LifeCycleStatus status) throws EntityValidationException;

}
