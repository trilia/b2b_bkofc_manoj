package com.olp.jpa.domain.docu.llty.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.LoyaltyPartnerEntity;

@NoRepositoryBean
public interface LoyaltyPartnerRepository extends JpaRepository<LoyaltyPartnerEntity, Long>, ITextRepository<LoyaltyPartnerEntity, Long>{
	
	public LoyaltyPartnerEntity findByPartnerCode(String partnerCode);
	
}
