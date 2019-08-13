package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;

@NoRepositoryBean
public interface AccountSubCategoryService extends IJpaService<AccountSubCategoryEntity, Long> {
	public AccountSubCategoryEntity findbySubCatgCode(String  catgCode, String subCatgCode);

	public void validate(AccountSubCategoryEntity entity,boolean valParent, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(AccountSubCategoryEntity newAccountSubCategory,
			AccountSubCategoryEntity oldAccountSubCategory2);


}
