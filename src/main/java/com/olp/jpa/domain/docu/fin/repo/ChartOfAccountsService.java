package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;

@NoRepositoryBean
public interface ChartOfAccountsService extends IJpaService<ChartOfAccountsEntity, Long> {
	public ChartOfAccountsEntity findbyCoaCode(String coaCode);

	public void validate(ChartOfAccountsEntity coaValues, boolean b, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(ChartOfAccountsEntity neu, ChartOfAccountsEntity old);
}
