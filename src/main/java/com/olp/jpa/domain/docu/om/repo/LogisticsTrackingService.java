package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;

@NoRepositoryBean
public interface LogisticsTrackingService extends IJpaService<LogisticsTrackingEntity, Long> {

	public LogisticsTrackingEntity findByTrackingCode(String trackingCode);

	public LogisticsTrackingEntity findByOrderLine(String merchTenantId, String orderNumber, int partNumber,
			int lineNumber);

	public void validate(LogisticsTrackingEntity entity, EntityVdationType type) throws EntityValidationException;

}
