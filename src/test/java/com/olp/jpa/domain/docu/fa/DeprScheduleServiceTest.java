package com.olp.jpa.domain.docu.fa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.fa.model.DeprSchedule;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.FaEnums.DepreciationType;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.domain.docu.fa.repo.DeprScheduleService;

public class DeprScheduleServiceTest extends BaseSpringAwareTest {
	
	@Autowired
	@Qualifier("deprScheduleService")
	private DeprScheduleService deprScheduleService;
	
	
	@Before
	public void before() {
		deprScheduleService.deleteAll(false);
		setup();
	}
	
	@Test
	public void testDeprSchedule() {
		List<DeprScheduleEntity> deprSchedules = deprScheduleService.findAll();
		assertNotNull("deprScheduleService list cannot be null", deprSchedules);
		
		assertEquals("deprSchedules size", deprSchedules.size(), 1);
	}
	
	@Test
	public void testUpdateDeprSchedule(){
		List<DeprScheduleEntity> deprSchedules = deprScheduleService.findAll();
		DeprSchedule deprSchedule = deprSchedules.get(0).convertTo(0);
		
		deprSchedule.setDeprScheduleName("1234");
		deprSchedule.setDeprPct(5F);
		deprSchedule.setDeprType(DepreciationType.WDV);
		
		DeprScheduleEntity entity = deprSchedule.convertTo(0);
		deprScheduleService.update(entity);
		
		deprSchedules = deprScheduleService.findAll();
		
		assertEquals("1234",deprSchedules.get(0).getDeprScheduleName() );
	}
	
	private void setup(){
		DeprScheduleEntity entity = new DeprScheduleEntity();
		entity.setDeprPct(1.1F);
		entity.setDeprScheduleCode("123");
		entity.setDeprType(DepreciationType.SLM);
		entity.setDeprTypeImpl("testimpl");
		entity.setDeprScheduleName("TestDepr");
		entity.setLifecycleStage(LifeCycleStage.NEW);
		
		deprScheduleService.add(entity);
		
		
	}
	

}
