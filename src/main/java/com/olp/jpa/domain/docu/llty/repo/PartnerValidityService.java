package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;

@NoRepositoryBean
public interface PartnerValidityService extends IJpaService<PartnerValidityEntity, Long>{

public PartnerValidityEntity findByEffectiveDate(String partnerCode, Date date);
	
	public List<PartnerValidityEntity> findByPartnerCode(String partnerCode);
	
	public void validate(PartnerValidityEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(PartnerValidityEntity old,
			PartnerValidityEntity neu);
}
