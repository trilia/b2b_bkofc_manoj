package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;

@NoRepositoryBean
public interface CustomerLoyaltyTierService extends IJpaService<CustomerLoyaltyTierEntity, Long> {

	public void validate(CustomerLoyaltyTierEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;

	public List<CustomerLoyaltyTierEntity> findByCustTierCode(String customerCode, String programCode, String tierCode);

	public boolean checkForUpdate(CustomerLoyaltyTierEntity newCustomerLoyaltyTier,
			CustomerLoyaltyTierEntity oldCustomerLoyaltyTier2);
}
