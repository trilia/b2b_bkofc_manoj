package com.olp.jpa.domain.docu.om;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.domain.docu.om.repo.SalesOrderService;

public class SalesOrderServiceTest extends BaseSpringAwareTest {
	@Autowired
	@Qualifier("salesOrderService")
	private SalesOrderService salesOrderService;
	
	@Before
	public void before() {
		salesOrderService.deleteAll(true);
		setup();

	}
	
	@Test
	public void testSalesOrder() {
		List<SalesOrderEntity> salesOrder = salesOrderService.findAll();
		assertNotNull("salesOrder list cannot be null", salesOrder);
		assertEquals("salesOrder size", salesOrder.size(), 1);
	}
	
	public void setup() {
		SalesOrderEntity soe = SalesOrderWM.makeSalesOrder();
		
		List<SalesOrderLineEntity> sols = new ArrayList<SalesOrderLineEntity>();
		SalesOrderLineEntity sol = SalesOrderWM.makeSalesOrderLine();
		
		sols.add(sol);
		soe.setOrderLines(sols);
		
		salesOrderService.add(soe);
	}
}
