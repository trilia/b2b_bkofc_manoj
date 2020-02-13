package com.olp.jpa.domain.docu.om;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.RoutingStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ScheduleStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ShipmentStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.TrackingStatus;
import com.olp.jpa.domain.docu.om.model.LogisticsRoutingEntity;
import com.olp.jpa.domain.docu.om.model.LogisticsTrackingEntity;

public class LogisticsWM extends BaseSpringAwareTest {

	public static LogisticsTrackingEntity makeLogisticsTracking() {
		LogisticsTrackingEntity logisticsTrackingEntity = new LogisticsTrackingEntity();
		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		logisticsTrackingEntity.setBaseShipCost(new BigDecimal(11));
		logisticsTrackingEntity.setBaseShipCost(new BigDecimal(11));
		logisticsTrackingEntity.setLineNumber(1);
		// logisticsTrackingEntity.setMerchTenantId(merchTenantId);
		logisticsTrackingEntity.setNonOffsetTax(new BigDecimal(31));
		logisticsTrackingEntity.setOffsetTax(new BigDecimal(41));
		logisticsTrackingEntity.setPartNumber(12);
		logisticsTrackingEntity.setPostalCode("1234");
		logisticsTrackingEntity.setPrimaryEmail("test@gmail.com");
		logisticsTrackingEntity.setPrimaryPhone("123456789");
		logisticsTrackingEntity.setRevisionControl(revisionControl());
		logisticsTrackingEntity.setScheduleStatus(ScheduleStatus.ON_TIME);
		logisticsTrackingEntity.setSecondaryEmail("test@gmail.com");
		logisticsTrackingEntity.setSecondaryPhone("3456789012");
		logisticsTrackingEntity.setShipmentStatus(ShipmentStatus.ASSIGNED);
		logisticsTrackingEntity.setShippingAddress("testaddress");
		logisticsTrackingEntity.setTrackingCode("TC_" + str);
		logisticsTrackingEntity.setTrackingStatus(TrackingStatus.ACTIVE);

		return logisticsTrackingEntity;
	}

	public static LogisticsRoutingEntity makeLogisticsRouting() {
		LogisticsRoutingEntity bean = new LogisticsRoutingEntity();
		IContext ctx = ContextManager.getContext();
		String str = getRandom().toUpperCase();
		bean.setTrackingCode("TC_" + str);
		bean.setDestAddress("destAddress");
		bean.setDestPostalCode("HX1234");
		bean.setIntermediateFnc(false);
		bean.setLastMileFunc(true);
		bean.setNonOffsetTax("testNonoffset");
		bean.setOffsetTax("offsetTax");
		bean.setPickupFunc(true);
		bean.setRevisionControl(revisionControl());
		bean.setRoutingSequence(1234);
		bean.setRoutingStatus(RoutingStatus.COMPLETE);
		bean.setScheduleStatus(ScheduleStatus.ON_TIME);
		bean.setSourceAddress("123456");
		bean.setSourcePostalCode("gh434");

		return bean;
	}

	public static CustomerEntity makeCustomer() {
		CustomerEntity customerEntity = new CustomerEntity();
		String str = getRandom().toUpperCase();
		customerEntity.setCustomerCode("CUST_" + str);
		customerEntity.setFirstName("Test");
		customerEntity.setLastName("Last");
		customerEntity.setMiddleName("Middle");
		customerEntity.setRevisionControl(revisionControl());

		return customerEntity;
	}

	public static RevisionControlBean revisionControl() {
		RevisionControlBean rev = new RevisionControlBean();
		rev.setCreatedBy("UT");
		rev.setCreatedById(100);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		rev.setCreationDate(cal.getTime());
		rev.setRevisedBy("UT");
		rev.setRevisedById(100);
		rev.setRevisionDate(cal.getTime());

		return (rev);
	}
}
