package com.olp.jpa.domain.docu.llty.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.LoyaltyPartnerEntity;

@NoRepositoryBean
public interface LoyaltyPartnerService extends IJpaService<LoyaltyPartnerEntity, Long>{

	public LoyaltyPartnerEntity findByPartnerCode(String partnerCode);
	
	public void validate(LoyaltyPartnerEntity entity, EntityVdationType type) throws EntityValidationException;
	
}
