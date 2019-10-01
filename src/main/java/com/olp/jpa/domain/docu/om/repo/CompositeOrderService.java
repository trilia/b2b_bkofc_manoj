package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;

@NoRepositoryBean
public interface CompositeOrderService extends IJpaService<CompositeOrderEntity, Long>{
	public CompositeOrderEntity findByCompOrderNum(String compOrderNum);
	
	public void validate(CompositeOrderEntity lovValues, EntityVdationType type)
			throws EntityValidationException;
}
