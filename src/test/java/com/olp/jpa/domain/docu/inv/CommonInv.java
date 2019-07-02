/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv;

import java.math.BigDecimal;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.inv.model.InvEnums;
import com.olp.jpa.domain.docu.inv.model.InvEnums.SkuInvCategory;
import com.olp.jpa.domain.docu.inv.model.InvEnums.SkuSource;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomClass;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomType;
import com.olp.jpa.domain.docu.inv.model.ProductSkuEntity;
import com.olp.jpa.domain.docu.inv.model.SkuBean;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;

/**
 *
 * @author raghosh
 */
public class CommonInv extends BaseSpringAwareTest {
    
    public static ProductSkuEntity makeSku() {
        
        ProductSkuEntity sku = new ProductSkuEntity();
        
        String s = getRandom().toUpperCase();
        
        SkuBean bean  = new SkuBean();
        bean.setFamily("DEFAULT");
        bean.setProduct(s);
        bean.setVariant("0000");
        
        sku.setSku(bean);
        sku.setIsEnabled(true);
        sku.setSource(SkuSource.MANUAL);
        sku.setInvCategory(SkuInvCategory.FINISHED_GOODS);
        sku.setTrackingType(InvEnums.SkuTrackingType.NONE);
        sku.setStatus("ACTIVE");
        sku.setAllowMultiTrackingCode(false);
        sku.setAllowTrackCodeByWarehouse(false);
        sku.setNeedInspection(true);
        
        
        return(sku);
    }
    
    public static UnitOfMeasureEntity makeUom() {
    	
    	UnitOfMeasureEntity uom = new UnitOfMeasureEntity();
    	uom.setLifecycleStatus(LifeCycleStatus.INACTIVE);
    	uom.setStrictConv(false);
    	uom.setUomClass(UomClass.SI);
    	uom.setUomCode("Each");
    	uom.setUomDesc("Each");
    	uom.setUomType(UomType.QUANTITY);
    	
    	return(uom);
    }
    
    public static UomConversionEntity makeUomConversion(BigDecimal factor) {
    	
    	UomConversionEntity conv = new UomConversionEntity();
    	conv.setConvFactor(factor);
    	conv.setEntrySequence(10);
    	conv.setLifecycleStatus(LifeCycleStatus.INACTIVE);
    	
    	return(conv);
    }
}
