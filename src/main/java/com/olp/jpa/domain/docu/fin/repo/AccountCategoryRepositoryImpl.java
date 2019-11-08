package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;

@Repository("accountCategoryRepository")
public class AccountCategoryRepositoryImpl extends AbstractRepositoryImpl<AccountCategoryEntity, Long>
implements AccountCategoryRepository {

	@Override
	@Transactional(readOnly=true)
	public AccountCategoryEntity findbyCategoryCode(String  catgCode) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<AccountCategoryEntity> query = getEntityManager().createNamedQuery("AccountCategoryEntity.findbyCategoryCode", AccountCategoryEntity.class);
	        query.setParameter("catgCode", catgCode);
	        query.setParameter("tenant", tid);
	        AccountCategoryEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
