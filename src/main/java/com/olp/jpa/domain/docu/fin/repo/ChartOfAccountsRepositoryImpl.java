package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;

@Repository("chartOfAccountsRepository")
public class ChartOfAccountsRepositoryImpl extends AbstractRepositoryImpl<ChartOfAccountsEntity, Long>
		implements ChartOfAccountsRepository {

	@Override
	@Transactional(readOnly=true)
	public ChartOfAccountsEntity findbyCoaCode(String coaCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<ChartOfAccountsEntity> query = getEntityManager()
				.createNamedQuery("ChartOfAccountsEntity.findbyAccountCatg", ChartOfAccountsEntity.class);
		query.setParameter("coaCode", coaCode);
		query.setParameter("tenant", tid);
		ChartOfAccountsEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}
}
