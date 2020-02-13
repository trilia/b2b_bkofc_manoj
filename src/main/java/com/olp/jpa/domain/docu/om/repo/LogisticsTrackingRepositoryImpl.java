package com.olp.jpa.domain.docu.om.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;

@Repository("logisticsTrackingRepository")
public class LogisticsTrackingRepositoryImpl extends AbstractRepositoryImpl<LogisticsTrackingEntity, Long>
		implements LogisticsTrackingRepository {

	@Override
	@Transactional(readOnly = true)
	public LogisticsTrackingEntity findByTrackingCode(String trackingCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LogisticsTrackingEntity> query = getEntityManager()
				.createNamedQuery("LogisticsTrackingEntity.findByTrackingCode", LogisticsTrackingEntity.class);
		query.setParameter("trackingCode", trackingCode);
		LogisticsTrackingEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public LogisticsTrackingEntity findByOrderLine(String merchTenantId, String orderNumber, int partNumber,
			int lineNumber) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<LogisticsTrackingEntity> query = getEntityManager()
				.createNamedQuery("LogisticsTrackingEntity.findByOrderLine", LogisticsTrackingEntity.class);
		query.setParameter("merchTenantId", merchTenantId);
		query.setParameter("orderNumber", orderNumber);
		query.setParameter("partNumber", partNumber);
		LogisticsTrackingEntity bean = query.getSingleResult();

		return (bean);
	}
	
	@Override
	public String getLazyLoadElements() {
		return null;
	}

}
