package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;

@Repository("logisticsRoutingRepository")
public class LogisticsRoutingRepositoryImpl extends AbstractRepositoryImpl<LogisticsRoutingEntity, Long>
		implements LogisticsRoutingRepository {

	@Override
	@Transactional(readOnly = true)
	public LogisticsRoutingEntity findByTrackingCode(String trackingCode, int routingSequence) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LogisticsRoutingEntity> query = getEntityManager()
				.createNamedQuery("LogisticsRoutingEntity.findByOrderLine", LogisticsRoutingEntity.class);
		query.setParameter("trackingCode", trackingCode);
		query.setParameter("routingSequence", routingSequence);
		LogisticsRoutingEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
