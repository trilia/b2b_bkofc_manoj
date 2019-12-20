package com.olp.jpa.domain.docu.be.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;

@NoRepositoryBean
public interface LogisticPartnerRepository extends JpaRepository<LogisticPartnerEntity, Long>, ITextRepository<LogisticPartnerEntity, Long>{
	public LogisticPartnerEntity findByPartnerCode(String partnerCode);
}
