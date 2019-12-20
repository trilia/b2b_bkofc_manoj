package com.olp.jpa.domain.docu.logist.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;

@NoRepositoryBean
public interface ServiceClassService extends IJpaService<ServiceClassEntity, Long> {
	public ServiceClassEntity findBySvcClassCode(String svcClassCode);

	public void validate(ServiceClassEntity entity, EntityVdationType type) throws EntityValidationException;

	Long requestStatusChange(String svcClassCode, LifeCycleStatus status) throws EntityValidationException;

}
