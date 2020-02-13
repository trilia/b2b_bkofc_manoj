package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;

@NoRepositoryBean
public interface LogisticsRoutingService extends IJpaService<LogisticsRoutingEntity, Long> {

	public LogisticsRoutingEntity findByTrackingCode(String trackingCode, int routingSequence);

	public void validate(LogisticsRoutingEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(LogisticsRoutingEntity newLogisticsRouting,
			LogisticsRoutingEntity oldLogisticsRoutingEntity);
}
