package com.olp.jpa.domain.docu.inv;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;
import com.olp.jpa.domain.docu.inv.repo.UnitOfMeasureService;
import com.olp.jpa.domain.docu.inv.repo.UomConversionService;

public class UomServiceTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("unitOfMeasureService")
	private UnitOfMeasureService uomSvc;
	
	@Autowired
	@Qualifier("uomConversionService")
	private UomConversionService uomConvSvc;
	
	@Before
    public void before() {
        
		uomConvSvc.deleteAll(false);
        uomSvc.deleteAll(false);
        
        setUp();
    }
    
    //@After
    public void after() {
        
    	uomConvSvc.deleteAll(false);
        uomSvc.deleteAll(false);
        
    }
    
    
    //@Test
    public void test_add1() {
    	
    	List<UnitOfMeasureEntity> uoms = uomSvc.findAll();
    	
    	assertNotNull("UOMs should not be null", uoms);
    }
    
    @Test
    public void test_update1() {
    	
    	List<UnitOfMeasureEntity> uoms = uomSvc.findAll();
    	
    	UnitOfMeasureEntity uom1 = null, uom2 = null;
    	
    	for (UnitOfMeasureEntity uom : uoms) {
    		if (Objects.equals("Dozen", uom.getUomCode())) {
    			uom1 = uom;
    			break;
    		}
    	}
    	
    	assertNotNull("UOM for Dozen must exist", uom1);
    	
    	uom2 = uom1.convertTo(0).convertTo(0);
    	
    	uom2.setUomDesc("One dozen");
    	
    	Set<UomConversionEntity> convs = uom2.getSrcConversions();
    	
    	assertNotNull("Conversion from dozen to each must exist", convs);
    	
    	UomConversionEntity conv = null;
    	for (UomConversionEntity uce : convs) {
    		//if (uce.getEntrySequence() == 10)
    			conv = uce;
    		break;
    	}
    	
    	assertNotNull("Conversion from dozen to each must exist 2", conv);
    	
    	conv.setConvFactor(BigDecimal.valueOf(10L));
    	
    	uomSvc.update(uom2);
    	
    	List<UnitOfMeasureEntity> uoms3 = uomSvc.findAll();
    	
    	UnitOfMeasureEntity uom3 = null;
    	
    	for (UnitOfMeasureEntity uom : uoms3) {
    		if (Objects.equals("Dozen", uom.getUomCode())) {
    			uom3 = uom;
    			break;
    		}
    	}
    	
    	assertEquals("One dozen", uom3.getUomDesc());
    	
    }
    
    private void setUp() {
    	
    	UnitOfMeasureEntity uom1 = CommonInv.makeUom();
    	uom1.setUomCode("Each"); uom1.setUomDesc("Each");
    	
    	UnitOfMeasureEntity uom2 = CommonInv.makeUom();
    	uom2.setUomCode("Dozen"); uom2.setUomDesc("Dozen");
    	
    	UomConversionEntity conv1 = CommonInv.makeUomConversion(BigDecimal.valueOf(12L));
    	conv1.setSrcUomRef(uom2); conv1.setDestUomRef(uom1);

    	uom1.getDestConversions().add(conv1);
    	uom2.getSrcConversions().add(conv1);
    	
    	List<UnitOfMeasureEntity> uoms = new ArrayList<>();
    	uoms.add(uom1); uoms.add(uom2);
    	
    	uomSvc.addAll(uoms, false);
    }
	
}
