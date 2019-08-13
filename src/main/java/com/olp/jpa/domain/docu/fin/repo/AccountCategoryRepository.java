package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;

@NoRepositoryBean
public interface AccountCategoryRepository
		extends JpaRepository<AccountCategoryEntity, Long>, ITextRepository<AccountCategoryEntity, Long> {

	public AccountCategoryEntity findbyCategoryCode(String catgCode);
}
