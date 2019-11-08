package com.olp.jpa.domain.docu.comm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.comm.model.LovDefinition;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;
import com.olp.jpa.domain.docu.comm.model.LovType;
import com.olp.jpa.domain.docu.comm.model.LovValues;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;
import com.olp.jpa.domain.docu.comm.repo.LovDefinitionService;
import com.olp.jpa.domain.docu.comm.repo.LovValuesService;

public class LuvDefinitionServiceTest  extends BaseSpringAwareTest {
	
	@Autowired
	@Qualifier("lovDefinitionService")
	private LovDefinitionService lovDefinitionService;
	
	@Autowired
	@Qualifier("lovValuesService")
	private LovValuesService lovValuesService;
	
	@Before
	public void before() {
		lovDefinitionService.deleteAll(false);
		lovValuesService.deleteAll(false);
		setup();
	}
	
	public void setup() {
		LovDefinitionEntity lovDefinition = makeLovDefinition();
		
		LovValuesEntity lovValuesOne = makeLovValues();
		lovValuesOne.setLovCode(lovDefinition.getLovCode());
		lovValuesOne.setLovDefRef(lovDefinition);
		
		LovValuesEntity lovValuesTwo = makeLovValues();
		lovValuesTwo.setLovCode(lovDefinition.getLovCode());
		lovValuesTwo.setLovDefRef(lovDefinition);	
		
		List<LovValuesEntity> lovValues = new ArrayList<>();
		lovValues.add(lovValuesOne);
		lovValues.add(lovValuesTwo);
		
		lovValuesService.add(lovValuesOne);
		lovValuesService.add(lovValuesTwo);
		
		lovDefinition.setLovValues(lovValues);
		
		lovDefinitionService.add(lovDefinition);
	}
	
	@Test
	public void testLovDefinition() {
		List<LovDefinitionEntity> lovDefintion = lovDefinitionService.findAll();
		assertNotNull("lovDefintion list cannot be null", lovDefintion);
	}
	
	@Test
	public void updateLovDefintions(){
		List<LovDefinitionEntity> lovDefinitions = lovDefinitionService.findAll();
		LovDefinition lovDefinition = lovDefinitions.get(0).convertTo(0);
		
		List<LovValues> lovValues = lovDefinition.getLovValues();
		assertEquals("lovValues size", lovValues.size(), 2);
		
		LovValuesEntity lv3 = makeLovValues();
		lv3.setLovCode(lovDefinition.getLovCode());
		lv3.setLovDefRef(lovDefinitions.get(0));
		
		LovValues lovValue = lv3.convertTo(0);
		lovValues.add(lovValue);
		
		LovDefinitionEntity lde = lovDefinition.convertTo(0);
		lovDefinitionService.update(lde);

		lovDefinitions= lovDefinitionService.findAll();
		assertEquals("lovValues size", lovDefinitions.get(0).getLovValues().size(), 3);
	}
	
	private static LovDefinitionEntity makeLovDefinition(){
		LovDefinitionEntity entity = new LovDefinitionEntity();
		
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setEnabled(true);
		entity.setLovCode("LC_"+str);
		entity.setLovName("LNAme");
		entity.setLovType(LovType.LOVVALUE1);
		entity.setTenantId(tid);
		
		return entity;
	}
	
	public static LovValuesEntity makeLovValues(){
		LovValuesEntity entity = new LovValuesEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setEnabled(true);
		entity.setValue("LV_"+str);
		entity.setTenantId(tid);
		
		return entity;
	}

}
