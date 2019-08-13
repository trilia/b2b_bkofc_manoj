package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;

@Repository("accountSubCategoryRepository")
public class AccountSubCategoryRepositoryImpl extends AbstractRepositoryImpl<AccountSubCategoryEntity, Long>
		implements AccountSubCategoryRepository {

	@Override
	public AccountSubCategoryEntity findbySubCatgCode(String catgCode, String subCatgCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<AccountSubCategoryEntity> query = getEntityManager()
				.createNamedQuery("AccountSubCategoryEntity.findbySubCatgCode", AccountSubCategoryEntity.class);
		query.setParameter("catgCode", catgCode);
		query.setParameter("subCatgCode", subCatgCode);
		query.setParameter("tenant", tid);
		AccountSubCategoryEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
