package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;

/**
 * @author Jayesh
 *
 */
public interface SalesOrderRepository
		extends JpaRepository<SalesOrderEntity, Long>, ITextRepository<SalesOrderEntity, Long> {

	public SalesOrderEntity findbyOrderNumber(String orderNumber, int partNumber);

}
