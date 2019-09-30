package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;

@NoRepositoryBean
public interface LedgerRepository extends JpaRepository<LedgerEntity, Long>, ITextRepository<LedgerEntity, Long>{
	public LedgerEntity findbyLedgerName(String  ledgerName);
}
