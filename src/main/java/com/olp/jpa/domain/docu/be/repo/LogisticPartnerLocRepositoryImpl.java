package com.olp.jpa.domain.docu.be.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;

@Repository("logisticPartnerLocRepository")
public class LogisticPartnerLocRepositoryImpl extends AbstractRepositoryImpl<LogisticPartnerLocEntity, Long>
		implements LogisticPartnerLocRepository {

	@Override
	@Transactional(readOnly = true)
	public LogisticPartnerLocEntity findByLocationCode(String partnerCode, String locCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LogisticPartnerLocEntity> query = getEntityManager()
				.createNamedQuery("LogisticPartnerLocEntity.findByLocationCode", LogisticPartnerLocEntity.class);
		query.setParameter("partnerCode", partnerCode);
		query.setParameter("locCode", locCode);
		LogisticPartnerLocEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
