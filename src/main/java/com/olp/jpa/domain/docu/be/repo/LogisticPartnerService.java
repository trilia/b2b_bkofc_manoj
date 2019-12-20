package com.olp.jpa.domain.docu.be.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;

@NoRepositoryBean
public interface LogisticPartnerService extends IJpaService<LogisticPartnerEntity, Long>{
	public LogisticPartnerEntity findByPartnerCode(String partnerCode);

	public void validate(LogisticPartnerEntity entity, EntityVdationType type) throws EntityValidationException;
}
