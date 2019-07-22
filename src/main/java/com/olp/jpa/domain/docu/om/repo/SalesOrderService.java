package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;

@NoRepositoryBean
public interface SalesOrderService extends IJpaService<SalesOrderEntity, Long> {
	public SalesOrderEntity findbyOrderNumber(String orderNumber, int partNumber);
	
	public void validate(SalesOrderEntity entity, EntityVdationType type) throws EntityValidationException;

}
