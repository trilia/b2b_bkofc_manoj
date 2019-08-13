package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;

@NoRepositoryBean
public interface AccountSubCategoryRepository extends JpaRepository<AccountSubCategoryEntity , Long>, ITextRepository<AccountSubCategoryEntity , Long> {

	public AccountSubCategoryEntity findbySubCatgCode(String  catgCode, String subCatgCode);
}
