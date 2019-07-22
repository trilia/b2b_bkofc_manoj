package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;

/**
 * @author Jayesh
 *
 */
public interface SalesOrderLineRepository extends JpaRepository<SalesOrderLineEntity, Long>, ITextRepository<SalesOrderLineEntity, Long> { 
	
	public SalesOrderLineEntity findByOrderLineNumber(String orderNumber, int partNumber,int lineNumber);

}
