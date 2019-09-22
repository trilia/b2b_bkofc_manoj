package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;

@NoRepositoryBean
public interface ChartOfAccountsRepository extends JpaRepository<ChartOfAccountsEntity, Long>, ITextRepository<ChartOfAccountsEntity, Long>{
	public ChartOfAccountsEntity findbyCoaCode(String  coaCode);
}
