package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;

@Repository("ledgerLineRepository")
public class LedgerLineRepositoryImpl extends AbstractRepositoryImpl<LedgerLineEntity, Long>
implements LedgerLineRepository {

	@Override
	@Transactional(readOnly=true)
	public LedgerLineEntity findbyLedgerLine(String ledgerName, int lineNum) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LedgerLineEntity> query = getEntityManager()
				.createNamedQuery("LedgerLineEntity.findbyLedgerLine", LedgerLineEntity.class);
		query.setParameter("ledgerName", ledgerName);
		query.setParameter("lineNum", lineNum);
		query.setParameter("tenant", tid);
		LedgerLineEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
