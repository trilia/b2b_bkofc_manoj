package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;

@NoRepositoryBean
public interface CustomerLoyaltyService extends IJpaService<CustomerLoyaltyEntity, Long> {

	public void validate(CustomerLoyaltyEntity entity, EntityVdationType type) throws EntityValidationException;

	public List<CustomerLoyaltyEntity> findByCustProgCode(String customerCode, String programCode);
}
