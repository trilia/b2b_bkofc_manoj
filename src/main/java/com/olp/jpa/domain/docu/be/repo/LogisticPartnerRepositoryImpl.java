package com.olp.jpa.domain.docu.be.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;

@Repository("logisticPartnerRepository")
public class LogisticPartnerRepositoryImpl extends AbstractRepositoryImpl<LogisticPartnerEntity, Long>
		implements LogisticPartnerRepository {

	@Override
	@Transactional(readOnly = true)
	public LogisticPartnerEntity findByPartnerCode(String partnerCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LogisticPartnerEntity> query = getEntityManager()
				.createNamedQuery("LogisticPartnerEntity.findByPartnerCode", LogisticPartnerEntity.class);
		query.setParameter("partnerCode", partnerCode);
		LogisticPartnerEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
