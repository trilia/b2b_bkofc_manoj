package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;

@NoRepositoryBean
public interface LogisticsTrackingRepository
		extends JpaRepository<LogisticsTrackingEntity, Long>, ITextRepository<LogisticsTrackingEntity, Long> {

	public LogisticsTrackingEntity findByTrackingCode(String trackingCode);

	public LogisticsTrackingEntity findByOrderLine(String merchTenantId, String orderNumber, int partNumber,
			int lineNumber);
}
