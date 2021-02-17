package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.LoyaltyCardEntity;

@NoRepositoryBean
public interface LoyaltyCardService extends IJpaService<LoyaltyCardEntity, Long> {

	public void validate(LoyaltyCardEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(LoyaltyCardEntity newLoyaltyCardEntity,
			LoyaltyCardEntity oldLoyaltyCardEntity);
	
	public LoyaltyCardEntity findByCustomerCode(String custCode);

	public List<LoyaltyCardEntity> findAllByCustomerCode(String custCode);

}
