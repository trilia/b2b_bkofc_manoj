package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;

@NoRepositoryBean
public interface LovDefinitionService extends IJpaService<LovDefinitionEntity, Long> {
	public LovDefinitionEntity findByLovCode(String lovCode);
	public void validate(LovDefinitionEntity entity, EntityVdationType type) throws EntityValidationException;
}
