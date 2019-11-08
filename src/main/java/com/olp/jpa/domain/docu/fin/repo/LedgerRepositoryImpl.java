package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;

@Repository("ledgerRepository")
public class LedgerRepositoryImpl extends AbstractRepositoryImpl<LedgerEntity, Long>
implements LedgerRepository{

	@Override
	@Transactional(readOnly=true)
	public LedgerEntity findbyLedgerName(String ledgerName) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LedgerEntity> query = getEntityManager()
				.createNamedQuery("LedgerEntity.findbyLedgerName", LedgerEntity.class);
		query.setParameter("ledgerName", ledgerName);
		query.setParameter("tenant", tid);
		LedgerEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
