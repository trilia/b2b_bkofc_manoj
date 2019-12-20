package com.olp.jpa.domain.docu.logist;

import java.math.BigDecimal;

import com.olp.fwk.common.BaseTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.FinEnums;
import com.olp.jpa.domain.docu.fin.model.FinEnums.TaxApplicationType;
import com.olp.jpa.domain.docu.fin.model.FinEnums.TaxDirectionType;
import com.olp.jpa.domain.docu.fin.model.FinEnums.TaxOffsetType;
import com.olp.jpa.domain.docu.fin.model.TaxElementEntity;
import com.olp.jpa.domain.docu.fin.model.TaxGroupEntity;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;

public class LogistCommon extends BaseTest {

	public static ServiceClassEntity makeServiceClass(){
	   	String str = getRandom();
	   	IContext ctx = ContextManager.getContext();
	   	
	   	ServiceClassEntity entity = new ServiceClassEntity();   	 
	   	entity.setPartnerId("123");
	   	entity.setSvcClassCode("SC" + str);
	   	entity.setSvcClassName("TestName");
	   	entity.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
	   	entity.setRevisionControl(new RevisionControlBean());
		   	
	   	return entity;
	   }
	
	public static ServiceCategoryEntity makeServiceCategory(){
	   	String str = getRandom();
	   	IContext ctx = ContextManager.getContext();
	   	
	   	ServiceCategoryEntity entity = new ServiceCategoryEntity();  
	   	entity.setBothWaysFlag(false);
	   	entity.setEtaMaxHours(20.1f);
	   	entity.setEtaMinHours(1.1f);
	   	entity.setPartnerId("123");
	   	entity.setRevisionControl(new RevisionControlBean());
	   	entity.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
	   	
	   	return entity;
	   }
	
	public static LogisticsCostEntity makeLogisticsCost(){
		LogisticsCostEntity entity = new LogisticsCostEntity();
		String str = getRandom();
	   	IContext ctx = ContextManager.getContext();
	   	
		entity.setBasicCost(new BigDecimal(20));
		entity.setEffectiveDate(new java.util.Date());
		entity.setNonOffsetTax(new BigDecimal(12));
		entity.setNonOffsetTaxPct(new BigDecimal(13));
		entity.setOffsetTax(new BigDecimal(12));
		entity.setOffsetTaxPct(new BigDecimal(13));
		entity.setTotalCost(new BigDecimal(13));
		entity.setVolWeightMax(new BigDecimal(13));
		entity.setVolWeightMin(new BigDecimal(13));
		entity.setRevisionControl(new RevisionControlBean());
		entity.setLifecycleStatus(LifeCycleStatus.ACTIVE);
		
		return entity;
	}
	
	public static TaxGroupEntity makeTaxGroup(){
		String str = getRandom();
	   	IContext ctx = ContextManager.getContext();
	   	
		TaxGroupEntity entity = new TaxGroupEntity ();
		entity.setGroupCode("TG_"+str);
		entity.setDirection(TaxDirectionType.IN);
		entity.setRevisionControl(new RevisionControlBean());
		entity.setTenantId(ctx.getTenantId());
		entity.setApplication(TaxApplicationType.LINE);
		return entity;	
	}
	
	public static TaxElementEntity makeTaxElement(){
		String str = getRandom();
	   	IContext ctx = ContextManager.getContext();
	   	
		TaxElementEntity taxElementEntity = new TaxElementEntity();
		taxElementEntity.setElementCode("TE_"+str);
		taxElementEntity.setFixedAmount(new BigDecimal(10));
		taxElementEntity.setMaxValue(new BigDecimal(10));
		taxElementEntity.setMinValue(new BigDecimal(1));
		taxElementEntity.setTaxFormula("Test");
		taxElementEntity.setTaxOffsetPct(new BigDecimal(10));
		taxElementEntity.setTaxRate(new BigDecimal(3));
		taxElementEntity.setTenantId(ctx.getTenantId());
		taxElementEntity.setTaxCompType(FinEnums.TaxComputationType.PERCENTAGE);
		taxElementEntity.setTaxOffset(TaxOffsetType.OFFSET);
		return taxElementEntity;
	}
}
