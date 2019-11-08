package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;

@Repository("coaAccountsRepository")
public class CoaAccountsRepositoryImpl extends AbstractRepositoryImpl<CoaAccountsEntity, Long>
implements CoaAccountsRepository {

	@Override
	@Transactional(readOnly=true)
	public CoaAccountsEntity findbyAccountCatg(String catgCode, String subCatgCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<CoaAccountsEntity> query = getEntityManager().createNamedQuery("CoaAccountsEntity.findbyAccountCatg", CoaAccountsEntity.class);
        query.setParameter("accountCatgCode", catgCode);
        query.setParameter("accountSubCatgCode", subCatgCode);
        query.setParameter("tenant", tid);
        CoaAccountsEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}
}
