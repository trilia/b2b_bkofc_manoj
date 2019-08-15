package com.olp.jpa.domain.docu.comm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.CommonEnums.CalendarMonth;
import com.olp.jpa.domain.docu.comm.model.CalPeriodicity;
import com.olp.jpa.domain.docu.comm.model.CalUsageType;
import com.olp.jpa.domain.docu.comm.model.OrgCalendar;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.comm.repo.OrgCalendarService;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;

public class OrgCalendarServiceTest extends BaseSpringAwareTest {
	
	@Autowired
	@Qualifier("orgCalendarService")
	private OrgCalendarService orgCalendarService;
	
	@Before
	public void before() {
		orgCalendarService.deleteAll(false);
		setup();
	}
	
	public void setup() {
		OrgCalendarEntity entity = OrgCalendarServiceTest.makeOrgCalendar();		
		orgCalendarService.add(entity);
	}
	
	@Test
	public void testOrgCalendar() {
		List<OrgCalendarEntity> orgCalendar = orgCalendarService.findAll();
		assertNotNull("orgCalendar list cannot be null", orgCalendar);
	}
	
	//@After
	public void after() {     
		orgCalendarService.deleteAll(false);      
    }
	
	@Test
	public void updateOrgCalendar() {
		List<OrgCalendarEntity> orgCalendar = orgCalendarService.findAll();
		assertNotNull("orgCalendar list cannot be null", orgCalendar);
		
		OrgCalendar orgCal = orgCalendar.get(0).convertTo(0);
		orgCal.setCalendarName("Name2");
		orgCal.setStartDay(11);
		orgCal.setStartMonth(CalendarMonth.OCT);
		orgCal.setUsageType(CalUsageType.HCM);
		
		OrgCalendarEntity entity = orgCal.convertTo(0);
		entity.setLifecycleStage(LifeCycleStage.ACTIVE);
		orgCalendarService.update(entity);
		
		orgCalendar = orgCalendarService.findAll();
		assertNotNull("orgCalendar list cannot be null", orgCalendar);
		assertEquals("", "Name2", orgCalendar.get(0).getCalendarName());
	}


	public static OrgCalendarEntity makeOrgCalendar(){
		OrgCalendarEntity entity = new OrgCalendarEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setTenantId(tid);
		entity.setCalendarCode("CC_"+str);
		entity.setCalendarName("Cname");
		entity.setEndDay(12);
		entity.setEndMonth(CalendarMonth.APR);
		entity.setEndYear(2019);
		entity.setPeriodicity(CalPeriodicity.HALFYEARLY);
		entity.setStartDay(12);
		entity.setStartMonth(CalendarMonth.NOV);
		entity.setStartYear(2018);
		entity.setUsageType(CalUsageType.FINANCIAL);
		entity.setLifecycleStage(LifeCycleStage.NEW);
		
		return entity;
	}
	
	public static void main(String args[]){
		LocalDate today = LocalDate.now();
		System.out.println("today=>"+today);
		 
		String formattedDate = today.format(DateTimeFormatter.ofPattern("MMM-yyyy"));
		System.out.println("formattedDate--"+formattedDate);
		
		
	}
}
