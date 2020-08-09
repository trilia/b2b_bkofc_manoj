package com.olp.jpa.domain.docu.llty.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;

@NoRepositoryBean
public interface LoyaltyProgramService extends IJpaService<LoyaltyProgramEntity, Long> {

	public void validate(LoyaltyProgramEntity entity, EntityVdationType type) throws EntityValidationException;

	public Long requestStatusChange(String programCode, LifecycleStatus status) throws EntityValidationException;

	public LoyaltyProgramEntity findByProgramCode(String programCode);
}
