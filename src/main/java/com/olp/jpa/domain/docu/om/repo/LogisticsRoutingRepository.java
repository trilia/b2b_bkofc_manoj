package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;

@NoRepositoryBean
public interface LogisticsRoutingRepository
		extends JpaRepository<LogisticsRoutingEntity, Long>, ITextRepository<LogisticsRoutingEntity, Long> {

	public LogisticsRoutingEntity findByTrackingCode(String trackingCode, int routingSequence);
}
