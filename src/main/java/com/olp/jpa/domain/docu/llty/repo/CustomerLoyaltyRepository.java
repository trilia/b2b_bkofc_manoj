package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;

@NoRepositoryBean
public interface CustomerLoyaltyRepository extends JpaRepository<CustomerLoyaltyEntity, Long>, ITextRepository<CustomerLoyaltyEntity, Long>{

	public List<CustomerLoyaltyEntity> findByCustProgCode(String customerCode, String programCode);
}
