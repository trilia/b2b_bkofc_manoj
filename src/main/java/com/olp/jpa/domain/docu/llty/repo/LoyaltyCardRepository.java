package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.LoyaltyCardEntity;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCardEntity, Long>, ITextRepository<LoyaltyCardEntity, Long>{

	public LoyaltyCardEntity findByCustomerCode(String custCode);
	
	public List<LoyaltyCardEntity> findAllByCustomerCode(String custCode);
	
}
