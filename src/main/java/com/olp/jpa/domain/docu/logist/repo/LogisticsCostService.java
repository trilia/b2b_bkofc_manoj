package com.olp.jpa.domain.docu.logist.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;

@NoRepositoryBean
public interface LogisticsCostService extends IJpaService<LogisticsCostEntity, Long> {

	public void validate(LogisticsCostEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(LogisticsCostEntity neu, LogisticsCostEntity old);
}
