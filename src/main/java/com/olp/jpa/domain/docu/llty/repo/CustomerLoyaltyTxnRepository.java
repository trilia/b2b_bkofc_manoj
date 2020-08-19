package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;

@NoRepositoryBean
public interface CustomerLoyaltyTxnRepository extends JpaRepository<CustomerLoyaltyTxnEntity, Long>, ITextRepository<CustomerLoyaltyTxnEntity, Long>{

	public List<CustomerLoyaltyTxnEntity> findByCustProgCode(String customerCode, Date fromDate, Date toDate);
	
	public List<CustomerLoyaltyTxnEntity> findByCustomerCode(String customerCode);
}
