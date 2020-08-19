package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;

@NoRepositoryBean
public interface CustomerLoyaltyTxnService extends IJpaService<CustomerLoyaltyTxnEntity, Long> {

	public void validate(CustomerLoyaltyTxnEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;

	public List<CustomerLoyaltyTxnEntity> findByCustProgCode(String customerCode, Date fromDate, Date toDate);

	public boolean checkForUpdate(CustomerLoyaltyTxnEntity neu,CustomerLoyaltyTxnEntity old);

	public List<CustomerLoyaltyTxnEntity> findByCustomerCode(String customerCode);
}
