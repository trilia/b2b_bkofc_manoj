package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;

@NoRepositoryBean
public interface CoaAccountsRepository extends JpaRepository<CoaAccountsEntity, Long>, ITextRepository<CoaAccountsEntity, Long>{
	public CoaAccountsEntity findbyAccountCatg(String  catgCode, String subCatgCode);
}
