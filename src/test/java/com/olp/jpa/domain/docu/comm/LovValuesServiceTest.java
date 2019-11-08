package com.olp.jpa.domain.docu.comm;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;
import com.olp.jpa.domain.docu.comm.repo.LovValuesService;
import com.olp.jpa.domain.docu.fin.CommonFin;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;

public class LovValuesServiceTest  extends BaseSpringAwareTest{

  @Autowired
  @Qualifier
  private LovValuesService lovValuesService;
  
  @Before
	public void before() {
	  lovValuesService.deleteAll(false);
	  setup();
	}
	
	public void setup() {
		LovValuesEntity entity = LovValuesServiceTest.makeLovValues();	
		
		LovDefinitionEntity ld = LovValuesServiceTest.makeLovDefinition();
		/*ld.setLovCode(lovCode);
		(accountCategory.getCategoryCode());
		sc1.setCategoryRef(accountCategory);*/
		
		lovValuesService.add(entity);
	}

	@Test
	public void testOrgCalendar() {
		List<LovValuesEntity> lovValues = lovValuesService.findAll();
		assertNotNull("orgCalendar list cannot be null", lovValues);
	}
	
	//@After
	public void after() {     
		lovValuesService.deleteAll(false);      
    }
	
	private static LovValuesEntity makeLovValues(){
		LovValuesEntity entity = new LovValuesEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setEnabled(true);
		entity.setLovCode("LC_"+str);
		entity.setValue("testValue");
		
		return entity;
	}
	
	private static LovDefinitionEntity makeLovDefinition() {
		LovDefinitionEntity entity = new LovDefinitionEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();
		
		entity.setEnabled(true);
		entity.setLovCode("LC_"+str);
		entity.setLovName("test");
		return entity;
	}
		
}
