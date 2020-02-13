package com.olp.jpa.domain.docu.om;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.be.CommonBE;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;
import com.olp.jpa.domain.docu.be.repo.LogisticPartnerLocService;
import com.olp.jpa.domain.docu.be.repo.LogisticPartnerService;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.TrackingStatus;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;
import com.olp.jpa.domain.docu.om.model.LogisticsTracking;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.domain.docu.om.repo.CustomerService;
import com.olp.jpa.domain.docu.om.repo.LogisticsRoutingService;
import com.olp.jpa.domain.docu.om.repo.LogisticsTrackingService;
import com.olp.jpa.domain.docu.om.repo.SalesOrderLineService;
import com.olp.jpa.domain.docu.om.repo.SalesOrderService;

public class LogisticsTrackingServiceTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("logisticsTrackingService")
	private LogisticsTrackingService logisticsTrackingService;

	@Autowired
	@Qualifier("logisticsRoutingService")
	private LogisticsRoutingService logisticsRoutingService;

	@Autowired
	@Qualifier("salesOrderLineService")
	private SalesOrderLineService salesOrderLineService;

	@Autowired
	@Qualifier("salesOrderService")
	private SalesOrderService salesOrderService;

	@Autowired
	@Qualifier("logisticPartnerService")
	private LogisticPartnerService logisticPartnerService;

	@Autowired
	@Qualifier("logisticPartnerLocService")
	private LogisticPartnerLocService logisticPartnerLocService;

	@Autowired
	@Qualifier("customerService")
	private CustomerService customerService;

	@Before
	public void before() {
		logisticsTrackingService.deleteAll(true);
		logisticsRoutingService.deleteAll(true);
		logisticPartnerService.deleteAll(true);
		logisticPartnerLocService.deleteAll(true);
		salesOrderService.deleteAll(true);
		salesOrderLineService.deleteAll(true);
		customerService.deleteAll(true);
		setup();
	}

	public void setup() {
		LogisticsTrackingEntity logisticsTracking = LogisticsWM.makeLogisticsTracking();

		LogisticsRoutingEntity lr1 = LogisticsWM.makeLogisticsRouting();
		lr1.setLogistTrackingCode(logisticsTracking.getTrackingCode());

		LogisticsRoutingEntity lr2 = LogisticsWM.makeLogisticsRouting();
		lr2.setLogistTrackingCode(logisticsTracking.getTrackingCode());

		SalesOrderLineEntity salesOrderLine = SalesOrderWM.makeSalesOrderLine();
		List<SalesOrderLineEntity> orderLineList = new ArrayList<>();
		orderLineList.add(salesOrderLine);

		SalesOrderEntity salesOrder = SalesOrderWM.makeSalesOrder();
		salesOrder.setOrderLines(orderLineList);
		salesOrderService.add(salesOrder);

		salesOrderLine.setOrderRef(salesOrder);

		logisticsTracking.setOrderNumber(salesOrderLine.getOrderNumber());
		logisticsTracking.setOrderLineRef(salesOrderLine);
		salesOrderLineService.add(salesOrderLine);
		logisticsTracking.setMerchTenantId(salesOrderLine.getTenantId());

		LogisticPartnerEntity logisticPartner = CommonBE.makeLogisticPartner();
		LogisticPartnerLocEntity LogisticPartnerLocOne = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocOne.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocOne.setPartnerRef(logisticPartner);
		LogisticPartnerLocEntity LogisticPartnerLocTwo = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocTwo.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocTwo.setPartnerRef(logisticPartner);
		List<LogisticPartnerLocEntity> logPartnerLocs = new ArrayList<>();
		logPartnerLocs.add(LogisticPartnerLocOne);
		logPartnerLocs.add(LogisticPartnerLocTwo);
		logisticPartnerLocService.add(LogisticPartnerLocOne);
		logisticPartnerLocService.add(LogisticPartnerLocTwo);
		logisticPartner.setPartnerLocations(logPartnerLocs);
		logisticPartnerService.add(logisticPartner);

		lr1.setLogistPartnerCode(logisticPartner.getPartnerCode());
		lr2.setLogistPartnerCode(logisticPartner.getPartnerCode());
		lr1.setLogistPartnerRef(logisticPartner);
		lr2.setLogistPartnerRef(logisticPartner);

		CustomerEntity customerEntity = LogisticsWM.makeCustomer();
		customerService.add(customerEntity);

		logisticsTracking.setCustomerCode(customerEntity.getCustomerCode());
		logisticsTracking.setCustomerRef(customerEntity);

		List<LogisticsRoutingEntity> logisticsRoutings = new ArrayList<>();
		logisticsRoutings.add(lr1);
		logisticsRoutings.add(lr2);

		logisticsRoutingService.add(lr1);
		logisticsRoutingService.add(lr2);

		logisticsTracking.setLogistRoutings(logisticsRoutings);

		logisticsTrackingService.add(logisticsTracking);
	}

	@Test
	public void testLogisticsTracking() {
		List<LogisticsTrackingEntity> logisticsTrackings = logisticsTrackingService.findAll();
		assertNotNull("logisticsTracking list cannot be null", logisticsTrackings);
		assertEquals("logisticsTracking size", logisticsTrackings.size(), 1);
	}

	@Test
	public void updateLogisticsTracking() {
		List<LogisticsTrackingEntity> logisticsTrackings = logisticsTrackingService.findAll();
		LogisticsTracking logisticsTracking = logisticsTrackings.get(0).convertTo(0);

		List<LogisticsRoutingEntity> lre = logisticsTracking.getLogistRoutings();
		assertEquals("LogisticsRoutingEntity size", lre.size(), 2);
		LogisticsRoutingEntity lr3 = LogisticsWM.makeLogisticsRouting();

		SalesOrderLineEntity salesOrderLine = SalesOrderWM.makeSalesOrderLine();
		List<SalesOrderLineEntity> orderLineList = new ArrayList<>();
		orderLineList.add(salesOrderLine);

		SalesOrderEntity salesOrder = SalesOrderWM.makeSalesOrder();
		salesOrder.setOrderLines(orderLineList);
		salesOrderService.add(salesOrder);

		salesOrderLine.setOrderRef(salesOrder);

		salesOrderLineService.add(salesOrderLine);
		logisticsTracking.setMerchTenantId(salesOrderLine.getTenantId());

		LogisticPartnerEntity logisticPartner = CommonBE.makeLogisticPartner();
		LogisticPartnerLocEntity LogisticPartnerLocOne = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocOne.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocOne.setPartnerRef(logisticPartner);
		LogisticPartnerLocEntity LogisticPartnerLocTwo = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocTwo.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocTwo.setPartnerRef(logisticPartner);
		List<LogisticPartnerLocEntity> logPartnerLocs = new ArrayList<>();
		logPartnerLocs.add(LogisticPartnerLocOne);
		logPartnerLocs.add(LogisticPartnerLocTwo);
		logisticPartnerLocService.add(LogisticPartnerLocOne);
		logisticPartnerLocService.add(LogisticPartnerLocTwo);
		logisticPartner.setPartnerLocations(logPartnerLocs);
		logisticPartnerService.add(logisticPartner);

		lr3.setLogistPartnerCode(logisticPartner.getPartnerCode());
		lr3.setLogistPartnerRef(logisticPartner);
		lr3.setLogistTrackingCode(logisticsTracking.getTrackingCode());

		lre.add(lr3);
		logisticsTracking.setBaseShipCost(new BigDecimal(1234));
		logisticsTracking.setSecondaryEmail("secondaryEmailTest");
		logisticsTracking.setTrackingStatus(TrackingStatus.NEW);
		LogisticsTrackingEntity lte = logisticsTracking.convertTo(0);
		logisticsTrackingService.update(lte);

		logisticsTrackings = logisticsTrackingService.findAll();
		assertEquals("logistcis getBaseShipCost", logisticsTrackings.get(0).getBaseShipCost(), new BigDecimal(1234));
		assertEquals("logistcis getSecondaryEmail size", logisticsTrackings.get(0).getSecondaryEmail(),
				"secondaryEmailTest");
		assertEquals("logisticsRouting size", logisticsTrackings.get(0).getLogistRoutings().size(), 3);
	}
}
