package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.PartnerValidityEntity;

@NoRepositoryBean
public interface PartnerValidityRepository extends JpaRepository<PartnerValidityEntity, Long>, ITextRepository<PartnerValidityEntity, Long>{
	public PartnerValidityEntity findByEffectiveDate(String partnerCode, Date date);
	
	public List<PartnerValidityEntity> findByPartnerCode(String partnerCode);
}
