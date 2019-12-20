package com.olp.jpa.domain.docu.be.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;

@NoRepositoryBean
public interface LogisticPartnerLocRepository extends JpaRepository<LogisticPartnerLocEntity, Long>, ITextRepository<LogisticPartnerLocEntity, Long>{
	public LogisticPartnerLocEntity findByLocationCode(String partnerCode, String locCode);
}
