package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.logist.model.ServiceAreaEntity;

@NoRepositoryBean
public interface ServiceAreaService extends IJpaService<ServiceAreaEntity, Long> {
	public List<ServiceAreaEntity> findBySvcClassCode(String svcClassCode);

	public void validate(ServiceAreaEntity entity, EntityVdationType type) throws EntityValidationException;

}
