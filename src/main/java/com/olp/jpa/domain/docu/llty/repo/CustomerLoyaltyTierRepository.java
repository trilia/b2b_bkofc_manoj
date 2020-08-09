package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;

@NoRepositoryBean
public interface CustomerLoyaltyTierRepository extends JpaRepository<CustomerLoyaltyTierEntity, Long>, ITextRepository<CustomerLoyaltyTierEntity, Long>{

	public List<CustomerLoyaltyTierEntity> findByCustTierCode(String customerCode, String programCode, String tierCode);
}
