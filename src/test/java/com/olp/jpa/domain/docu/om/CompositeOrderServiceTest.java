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
import com.olp.jpa.domain.docu.be.model.MerchantEntity;
import com.olp.jpa.domain.docu.be.repo.MerchantService;
import com.olp.jpa.domain.docu.inv.repo.ProductSkuService;
import com.olp.jpa.domain.docu.om.model.CompositeOrder;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLine;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderLineStatus;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.domain.docu.om.repo.CompositeOrderLineService;
import com.olp.jpa.domain.docu.om.repo.CompositeOrderService;
import com.olp.jpa.domain.docu.om.repo.SalesOrderLineService;
import com.olp.jpa.domain.docu.om.repo.SalesOrderService;

public class CompositeOrderServiceTest extends BaseSpringAwareTest{
	
	@Autowired
	@Qualifier("compositeOrderService")
	private CompositeOrderService compositeOrderService;
	
	@Autowired
	@Qualifier("compositeOrderLineService")
	private CompositeOrderLineService compositeOrderLineService;
	
	@Autowired
	@Qualifier("salesOrderService")
	private SalesOrderService salesOrderService;
	
	@Autowired
	@Qualifier("salesOrderLineService")
	private SalesOrderLineService salesOrderLineService;
	
	@Autowired
	@Qualifier("prodSkuService")
	private ProductSkuService productSkuService;
	
	@Autowired
	@Qualifier("merchantService")
	private MerchantService merchantService;
	
	@Before
	public void before() {
		productSkuService.deleteAll(true);
		salesOrderLineService.deleteAll(true);
		salesOrderService.deleteAll(true);
		compositeOrderLineService.deleteAll(true);
		compositeOrderService.deleteAll(true);
		setup();
	}
	
	public void setup() {
		CompositeOrderEntity coe = CompositeOrderWM.makeCompositeOrder();
		
		List<CompositeOrderLineEntity> cols = new ArrayList<CompositeOrderLineEntity>();
		CompositeOrderLineEntity col1 = CompositeOrderWM.makeCompositeOrderLine();
		col1.setCompOrderRef(coe);
		col1.setCompOrderNum(coe.getCompOrderNum());
				
		CompositeOrderLineEntity col2 = CompositeOrderWM.makeCompositeOrderLine();
		col2.setCompOrderRef(coe);
		col2.setCompOrderNum(coe.getCompOrderNum());
		
		cols.add(col1);
		cols.add(col2);
		
		coe.setOrderLines(cols);
		
		SalesOrderEntity soe = SalesOrderWM.makeSalesOrder();
		
		List<SalesOrderLineEntity> sols = new ArrayList<SalesOrderLineEntity>();
		SalesOrderLineEntity sol = SalesOrderWM.makeSalesOrderLine();
		
		sols.add(sol);
		soe.setOrderLines(sols);
		
		salesOrderService.add(soe);
		
		MerchantEntity mechantEntity = new MerchantEntity();
		mechantEntity.setMerchantCode("Mcode11");
		mechantEntity.setMerchantName("Test");
		//merchantService.add(mechantEntity);
		
		//col1.setMerchantRef(mechantEntity);
		col1.setSalesOrderRef(soe);
		col1.setSalesOrderNum(soe.getOrderNumber());
		col1.setLineNum(14);
		col2.setSalesOrderRef(soe);
		col2.setSalesOrderNum(soe.getOrderNumber());
		col2.setLineNum(15);
		compositeOrderLineService.add(col1);
		compositeOrderLineService.add(col2);
		
		compositeOrderService.add(coe);
	}
	
	@Test
	public void testCompositeOrder() {
		List<CompositeOrderEntity> compositeOrder = compositeOrderService.findAll();
		assertNotNull("compositeOrder list cannot be null", compositeOrder);
		assertEquals("compositeOrder size", compositeOrder.size(), 1);
	}
	
	@Test
	public void updateCompositeOrder(){
		List<CompositeOrderEntity> compositeOrders = compositeOrderService.findAll();
		CompositeOrder compositeOrder = compositeOrders.get(0).convertTo(0);
		
		List<CompositeOrderLine> cols = compositeOrder.getOrderLines();
		assertEquals("CompositeOrderLine size", cols.size(), 2);
		CompositeOrderLine compOrderLine = cols.get(0);
		assertEquals("orderStatus",compOrderLine.getOrderStatus(),CompOrderLineStatus.PROCESSING);
		
		CompositeOrderLineEntity compOrderLineEntity = compOrderLine.convertTo(0);
		compOrderLineEntity.setMerchTenantId("MT2");
		compOrderLineEntity.setOrderStatus(CompOrderLineStatus.PROCESSING);
		compositeOrderLineService.update(compOrderLineEntity);

		compositeOrders = compositeOrderService.findAll();
		compOrderLine = compositeOrder.getOrderLines().get(0);	
		assertEquals("orderStatus",compOrderLine.getOrderStatus(),CompOrderLineStatus.PROCESSING);
		
		
	/*	AccountSubCategoryEntity sc3 = CommonFin.makeAccountSubCategory();
		sc3.setCategoryCode(accountCategory.getCategoryCode());
		sc3.setCategoryRef(accountCategories.get(0));
		sc3.setLifecycleStatus(LifeCycleStatus.ACTIVE);
		
		AccountSubCategory asubCat = sc3.convertTo(0);
		asubCat.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
		asc.add(asubCat);
		AccountCategoryEntity ace = accountCategory.convertTo(0);
		accountCategoryService.update(ace);

		accountCategories= accountCategoryService.findAll();
		assertEquals("accountSubCategories size", accountCategories.get(0).getAccountSubCategories().size(), 3);
	*/}

}
