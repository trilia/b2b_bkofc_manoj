package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.PartnerCodeShareEntity;

@NoRepositoryBean
public interface PartnerCodeShareRepository extends JpaRepository<PartnerCodeShareEntity, Long>, ITextRepository<PartnerCodeShareEntity, Long>{

	public List<PartnerCodeShareEntity> findByEffectiveDate(String partnerCode, Date date);
	
	public List<PartnerCodeShareEntity> findByPartnerCode(String partnerCode);
}
