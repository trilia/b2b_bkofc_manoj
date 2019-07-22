package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;

@NoRepositoryBean
public interface SalesOrderLineService extends IJpaService<SalesOrderLineEntity, Long> {
	public SalesOrderLineEntity findbyOrderLineNumber(String orderNumber, int partNumber, int lineNumber);
	
	public void validate(SalesOrderLineEntity entity,boolean b, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(SalesOrderLineEntity newOrderLines, SalesOrderLineEntity oldOrderLines);


}
