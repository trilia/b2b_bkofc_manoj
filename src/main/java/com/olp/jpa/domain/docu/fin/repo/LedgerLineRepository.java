package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;

@NoRepositoryBean
public interface LedgerLineRepository extends JpaRepository<LedgerLineEntity, Long>, ITextRepository<LedgerLineEntity, Long>{
	public LedgerLineEntity findbyLedgerLine(String  ledgerName, int lineNum);
}
