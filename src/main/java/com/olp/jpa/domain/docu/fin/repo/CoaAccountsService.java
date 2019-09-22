package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;

@NoRepositoryBean
public interface CoaAccountsService extends IJpaService<CoaAccountsEntity, Long> {
	public CoaAccountsEntity findbyAccountCatg(String catgCode, String subCatgCode);
	public void validate(CoaAccountsEntity lovValues, boolean b, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(CoaAccountsEntity newLovValues, CoaAccountsEntity oldLuvValues2);


}
