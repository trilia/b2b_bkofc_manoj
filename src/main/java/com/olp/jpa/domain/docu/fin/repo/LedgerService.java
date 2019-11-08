package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerStatus;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;

@NoRepositoryBean
public interface LedgerService extends IJpaService<LedgerEntity, Long> {
	public LedgerEntity findbyLedgerName(String ledgerName);

	public void validate(LedgerEntity ledger, EntityVdationType type)
			throws EntityValidationException;

	public Long requestStatusChange(String ledgerName, LedgerStatus status) throws EntityValidationException;
	
}
