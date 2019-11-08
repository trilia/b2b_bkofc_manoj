package com.olp.jpa.domain.docu.om;

import java.math.BigDecimal;
import java.util.Date;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderLineStatus;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderStatus;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentMethod;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentStatus;

public class CompositeOrderWM extends BaseSpringAwareTest{

	public static CompositeOrderEntity makeCompositeOrder(){
		CompositeOrderEntity compositeOrder = new CompositeOrderEntity();
		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		compositeOrder.setCompOrderDate(new Date());
		compositeOrder.setCompOrderNum("CO_"+str);
		compositeOrder.setCompOrderStatus(CompOrderStatus.RECEIVED);
		compositeOrder.setOrderValue(new BigDecimal(11.11));
		compositeOrder.setPaymentMethod(PaymentMethod.WALLET);
		compositeOrder.setPaymentStatus(PaymentStatus.SUCCESS);
	
		return compositeOrder;
	}
	
	public static CompositeOrderLineEntity makeCompositeOrderLine(){
		CompositeOrderLineEntity compositeOrderLine = new CompositeOrderLineEntity();
		
		String str = getRandom().toUpperCase();
		
		IContext ctx = ContextManager.getContext();
		compositeOrderLine.setMerchTenantId("MT1");
		compositeOrderLine.setOrderStatus(CompOrderLineStatus.PROCESSING);
		
		return compositeOrderLine;
	}
	
}
