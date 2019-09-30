package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;

@NoRepositoryBean
public interface LedgerLineService extends IJpaService<LedgerLineEntity, Long> {
	public LedgerLineEntity findbyLedgerLine(String  ledgerName, int lineNum);
	
	public void validate(LedgerLineEntity lovValues, boolean b, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(LedgerLineEntity neu, LedgerLineEntity old);

}
