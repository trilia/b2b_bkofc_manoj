package com.olp.jpa.domain.docu.fin;

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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;
import com.olp.jpa.domain.docu.comm.model.LovType;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;
import com.olp.jpa.domain.docu.comm.repo.LovDefinitionService;
import com.olp.jpa.domain.docu.comm.repo.LovValuesService;
import com.olp.jpa.domain.docu.fin.model.ChartOfAccountsEntity;
import com.olp.jpa.domain.docu.fin.repo.ChartOfAccountsService;

public class ChartOfAccountsServiceTest extends BaseSpringAwareTest {
	
	@Autowired
	@Qualifier("chartOfAccountsService")
	private ChartOfAccountsService chartOfAccountsService;
	
	@Autowired
	@Qualifier("lovDefinitionService")
	private LovDefinitionService lovDefinitionService;
	
	@Autowired
	@Qualifier("lovValuesService")
	private LovValuesService lovValuesService;
	
	@Before
	public void before() {
		chartOfAccountsService.deleteAll(false);
		lovDefinitionService.deleteAll(false);
		setup();
	}
	
	public void setup() {
		ChartOfAccountsEntity chartOfAccount = makeChartOfAccounts();
		
		LovDefinitionEntity lovDefinitionOne = makeLovDefinition();
		LovDefinitionEntity lovDefinitionTwo = makeLovDefinition();
		LovDefinitionEntity lovDefinitionThree = makeLovDefinition();
		LovDefinitionEntity lovDefinitionFour = makeLovDefinition();
		LovDefinitionEntity lovDefinitionFive = makeLovDefinition();
		
		chartOfAccount.setSegment1LovCode(lovDefinitionOne.getLovCode());
		chartOfAccount.setSegment1LovRef(lovDefinitionOne);
		
		chartOfAccount.setSegment2LovCode(lovDefinitionTwo.getLovCode());
		chartOfAccount.setSegment2LovRef(lovDefinitionTwo);
		
		chartOfAccount.setSegment3LovCode(lovDefinitionThree.getLovCode());
		chartOfAccount.setSegment3LovRef(lovDefinitionThree);
		
		chartOfAccount.setSegment4LovCode(lovDefinitionFour.getLovCode());
		chartOfAccount.setSegment4LovRef(lovDefinitionFour);
			
		chartOfAccount.setSegment5LovCode(lovDefinitionFive.getLovCode());
		chartOfAccount.setSegment5LovRef(lovDefinitionFive);	
		
		LovValuesEntity lovValuesOne = makeLovValues();
		lovValuesOne.setLovCode(lovDefinitionOne.getLovCode());
		lovValuesOne.setLovDefRef(lovDefinitionOne);
		
		LovValuesEntity lovValuesTwo = makeLovValues();
		lovValuesTwo.setLovCode(lovDefinitionTwo.getLovCode());
		lovValuesTwo.setLovDefRef(lovDefinitionTwo);
		
		LovValuesEntity lovValuesThree = makeLovValues();
		lovValuesThree.setLovCode(lovDefinitionThree.getLovCode());
		lovValuesThree.setLovDefRef(lovDefinitionThree);
		
		LovValuesEntity lovValuesFour = makeLovValues();
		lovValuesFour.setLovCode(lovDefinitionFour.getLovCode());
		lovValuesFour.setLovDefRef(lovDefinitionFour);
		
		LovValuesEntity lovValuesFive = makeLovValues();
		lovValuesFive.setLovCode(lovDefinitionFive.getLovCode());
		lovValuesFive.setLovDefRef(lovDefinitionFive);
		
		List<LovValuesEntity> lovValues1 = new ArrayList<>();
		List<LovValuesEntity> lovValues2 = new ArrayList<>();
		List<LovValuesEntity> lovValues3 = new ArrayList<>();
		List<LovValuesEntity> lovValues4 = new ArrayList<>();
		List<LovValuesEntity> lovValues5 = new ArrayList<>();
		
		lovValues1.add(lovValuesOne);
		lovValues2.add(lovValuesTwo);
		lovValues3.add(lovValuesThree);
		lovValues4.add(lovValuesFour);
		lovValues5.add(lovValuesFive);
		lovDefinitionOne.setLovValues(lovValues1);	
		lovDefinitionTwo.setLovValues(lovValues2);
		lovDefinitionThree.setLovValues(lovValues3);
		lovDefinitionFour.setLovValues(lovValues4);
		lovDefinitionFive.setLovValues(lovValues5);
		
		lovValuesService.add(lovValuesOne);
		lovValuesService.add(lovValuesTwo);
		lovValuesService.add(lovValuesThree);
		lovValuesService.add(lovValuesFour);
		lovValuesService.add(lovValuesFive);
		
		lovDefinitionService.add(lovDefinitionOne);
		lovDefinitionService.add(lovDefinitionTwo);
		lovDefinitionService.add(lovDefinitionThree);
		lovDefinitionService.add(lovDefinitionFour);
		lovDefinitionService.add(lovDefinitionFive);
		
		chartOfAccountsService.add(chartOfAccount);
	}
	
	@Test
	public void testLovDefinition() {
		List<ChartOfAccountsEntity> chartOfAccounts = chartOfAccountsService.findAll();
		assertNotNull("chartOfAccounts list cannot be null", chartOfAccounts);
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
	
	
	public static ChartOfAccountsEntity makeChartOfAccounts(){
		ChartOfAccountsEntity entity = new ChartOfAccountsEntity();
		
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setCoaCode("COA_"+str);
		entity.setCoaName("COA_NA"+str);
		entity.setNumSegments(5);
		entity.setTenantId(tid);
		entity.setUseAccountNum(false);
		entity.setLifecycleStatus(LifeCycleStatus.INACTIVE);
		
		return entity;
	}
	
	
}
