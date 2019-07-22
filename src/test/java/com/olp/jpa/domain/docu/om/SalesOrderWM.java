package com.olp.jpa.domain.docu.om;

import java.util.Date;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.om.model.SalesOrderEntity;
import com.olp.jpa.domain.docu.om.model.SalesOrderLineEntity;
import com.olp.jpa.domain.docu.om.model.OrderEnums.DeliveryType;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderLineStatus;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderLineType;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderSource;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderStatus;
import com.olp.jpa.domain.docu.om.model.OrderEnums.OrderType;

public class SalesOrderWM extends BaseSpringAwareTest{

	public static SalesOrderEntity makeSalesOrder(){
		SalesOrderEntity salesOrder = new SalesOrderEntity();
		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		salesOrder.setCustomerCode("1234");
		salesOrder.setDeliverByDate(new Date());
		salesOrder.setDeliveryType(DeliveryType.STANDARD);
		salesOrder.setOrderDate(new Date());
		salesOrder.setOrderNumber("12");
		salesOrder.setOrderPart(1);
		salesOrder.setOrderSource(OrderSource.INTERNAL);
		salesOrder.setOrderStatus(OrderStatus.RECEIVED);
		salesOrder.setOrderType(OrderType.FIXED_COST);
		salesOrder.setParentOrderNum("11");
		//salesOrder.setParentOrderRef(parentOrderRef);
		salesOrder.setShippingAddress("TestAddr");
		
		
	
		return salesOrder;
	}
	
	public static SalesOrderLineEntity makeSalesOrderLine(){
		SalesOrderLineEntity salesOrderLine = new SalesOrderLineEntity();
		salesOrderLine.setLineNumber(1);
		salesOrderLine.setLineStatus(OrderLineStatus.RECEIVED);
		salesOrderLine.setLineType(OrderLineType.ITEM);
		salesOrderLine.setOrderNumber("123");
		salesOrderLine.setOrderQuantity(1.2f);
		salesOrderLine.setPartNumber(12);
		salesOrderLine.setProductSkuCode("1234");
		salesOrderLine.setReturnQuantity(1.1f);
		salesOrderLine.setReturnStatus(false);
		salesOrderLine.setUnitRate(1.1f);
		salesOrderLine.setUom("12");
		
		
		return salesOrderLine;
	}
}
