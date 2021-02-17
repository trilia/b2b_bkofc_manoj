package com.olp.jpa.domain.docu.llty.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.PartnerCodeShareEntity;

@NoRepositoryBean
public interface PartnerCodeShareService extends IJpaService<PartnerCodeShareEntity, Long> {

	public void validate(PartnerCodeShareEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;

	public List<PartnerCodeShareEntity> findByEffectiveDate(String partnerCode, Date date);

	public List<PartnerCodeShareEntity> findByPartnerCode(String partnerCode);

	boolean checkForUpdate(PartnerCodeShareEntity old, PartnerCodeShareEntity neu);

}
