package com.olp.jpa.domain.docu.be.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;

@NoRepositoryBean
public interface LogisticPartnerLocService extends IJpaService<LogisticPartnerLocEntity, Long>{
	public LogisticPartnerLocEntity findByLocationCode(String partnerCode, String locCode);
	
	public void validate(LogisticPartnerLocEntity logisticPartnerLoc, boolean b, EntityVdationType type)throws EntityValidationException;

	public boolean checkForUpdate(LogisticPartnerLocEntity newLovValue, LogisticPartnerLocEntity oldLovValue);

}
