/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.fin;

import java.math.BigDecimal;
import java.util.Date;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.FinEnums;
import com.olp.jpa.domain.docu.fin.model.FinEnums.AccountClass;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerLineType;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerStatus;
import com.olp.jpa.domain.docu.fin.model.FinEnums.TaxApplicationType;
import com.olp.jpa.domain.docu.fin.model.FinEnums.TaxDirectionType;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;
import com.olp.jpa.domain.docu.fin.model.TaxElementEntity;
import com.olp.jpa.domain.docu.fin.model.TaxGroupEntity;

/**
 *
 * @author raghosh
 */
public class CommonFin extends BaseSpringAwareTest {
    
    public static TaxGroupEntity makeTaxGroup() {
        
        String s = getRandom().toUpperCase();
        
        TaxGroupEntity grp = new TaxGroupEntity();
        grp.setGroupCode("TAX_GRP_" + s);
        grp.setDirection(TaxDirectionType.BOTH);
        grp.setApplication(TaxApplicationType.LINE);
        
        return(grp);
    }
    
    public static TaxElementEntity makeTaxElement() {
        
        String s = getRandom().toUpperCase();
        
        TaxElementEntity elem = new TaxElementEntity();
        elem.setElementCode("TAX_ELEM_" + s);
        elem.setTaxCompType(FinEnums.TaxComputationType.PERCENTAGE);
        double d = Double.parseDouble("12.24");
        elem.setTaxRate(new BigDecimal(12.24)); 
        
        //elem.setFixedAmount(BigDecimal.ZERO);
        //elem.setMaxValue(BigDecimal.ZERO);
        //elem.setMinValue(BigDecimal.ZERO);
        //elem.setTaxOffsetPct(BigDecimal.ZERO);
        
        return(elem);
    }
    
    public static AccountCategoryEntity makeAccountCategory(){
    	AccountCategoryEntity entity = new AccountCategoryEntity();
    	
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setAccountClass(AccountClass.ASSET);
		entity.setCategoryCode("AC_"+str);
		entity.setCategoryName("TestCategory");
		entity.setTenantId(tid);
				
    	return entity;
    }
    
    public static AccountSubCategoryEntity makeAccountSubCategory(){
    	AccountSubCategoryEntity entity = new AccountSubCategoryEntity();
    	
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setSubCategoryCode("ASC_"+str);
		entity.setSubCategoryName("SubCategoryTest");
		entity.setTenantId(tid);
		entity.setLifecycleStatus(LifeCycleStatus.ACTIVE);
		
    	return entity;
    }
    
    public static LedgerEntity makeLedger(){
    	LedgerEntity entity = new LedgerEntity();
    	
    	IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();
		
		entity.setLedgerDesc("LedgerDesc");
		entity.setLedgerName("LN_"+str);
		entity.setTenantId(tid);
		entity.setPostingDate(new Date());
		entity.setLifecycleStatus(LedgerStatus.POSTED);
    	
    	return entity;
    }
    
    public static LedgerLineEntity makeLedgerLine(){
    	LedgerLineEntity entity = new LedgerLineEntity();
    	IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		RevisionControlBean reviisonControl = new RevisionControlBean();
		
		entity.setLineType(LedgerLineType.CREDIT);
		entity.setLineAmount(new BigDecimal(12));
		entity.setLineDesc("LineDesc");
		entity.setLineNum(11);
		
    	
    	return entity;
    }
    
    
}
