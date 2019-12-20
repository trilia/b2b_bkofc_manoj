/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.be;

import java.util.Calendar;
import java.util.Date;

import com.olp.fwk.common.BaseTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.BankAccountEntity;
//import com.olp.jpa.domain.docu.be.model.BankAccountEntity;
//import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
//import com.olp.jpa.domain.docu.be.model.BeEnums.BankAccountType;
import com.olp.jpa.domain.docu.be.model.BeEnum.LogisticPartnerLocType;
import com.olp.jpa.domain.docu.be.model.BeEnums.BankAccountType;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;
import com.olp.jpa.domain.docu.be.model.ManufacturerEntity;
import com.olp.jpa.domain.docu.be.model.MerchantEntity;
import com.olp.jpa.domain.docu.be.model.SupplierEntity;
import com.olp.jpa.domain.docu.be.model.SupplierLocationEntity;
import com.olp.jpa.domain.docu.org.model.LocationEntity;
import com.olp.jpa.domain.docu.org.model.Source;

/**
 *
 * @author raghosh
 */
public class CommonBE extends BaseTest {
    
    public static MerchantEntity makeMerchant() {
        
        MerchantEntity m = new MerchantEntity();
        
        String str = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        m.setTenantId(tid);
        m.setMerchantCode("MRCHNT_" + str);
        m.setMerchantName("Merchant " + str);
        
        return(m);
    }
    
    public static SupplierEntity makeSupplier() {
        
        SupplierEntity s = new SupplierEntity();
        
        String str = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        s.setSupplierCode("SUPPLIER_" + str);
        s.setSupplierName("Supplier " + str);
        //List<SupplierLocationEntity> locList = new ArrayList<>();
        //locList.add(makeSuppLoc());
        //s.setSupplierLocations(locList);
        
        return(s);
    }
    
    public static SupplierLocationEntity makeSuppLoc() {
        
        SupplierLocationEntity loc = new SupplierLocationEntity();
        
        String str = "LOC_" + getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        loc.setLocationCode(str);
        //List<BankAccountEntity> beList = new ArrayList<>();
        //beList.add(makeBankAccount());
        //loc.setBankAccounts(beList);
        loc.setBillingLocation(true);
        loc.setShippingLocation(true);
        
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        
        LocationEntity le = new LocationEntity();
        le.setLocationSource(Source.SUPPLIER_LOC);
        le.setLocationType("SUPPLIER_OFC");
        le.setLocationAlias(str);
        le.setAddressLine1("Address line 1");
        le.setAddressLine2("Address line 2");
        le.setAddressLine3("Address line 3");
        le.setCity("XYZ");
        le.setStateOrProvince("TS");
        le.setZipCode("010101");
        le.setCountry("IN");
        le.setStartDate(date);
        le.setEndDate(date);
        
        loc.setLocation(le);
        
        return(loc);
    }
    
    public static BankAccountEntity makeBankAccount() {
        
        BankAccountEntity b = new BankAccountEntity();
        
        String str = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        b.setAccountAlias("BANK_" + str);
        b.setBankAcctNum(str);
        //b.setTenantId(tid);
        
        b.setBankName("ABC Bank");
        b.setBranchName("XYZ Branch");
        b.setAcctType(BankAccountType.CURRENT);
        
        return(b);
    }
    
  public static ManufacturerEntity makeManufacturer() {
        
        String str = getRandom();
        
        ManufacturerEntity mfg = new ManufacturerEntity();
        mfg.setManufacturerCode("MFG_" + str);
        mfg.setManufacturerDesc("Mfg " + str);
        
        return(mfg);
    }
    
    public static LogisticPartnerEntity makeLogisticPartner(){
    	 String str = getRandom();
    	 IContext ctx = ContextManager.getContext();
    	 
    	 LogisticPartnerEntity entity = new LogisticPartnerEntity();
    	 entity.setPartnerCode("PC_" + str);
    	 entity.setPartnerName("TEst");
    	 entity.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
    	 
    	 return entity;
    }
    
    public static LogisticPartnerLocEntity makeLogisticPartnerLoc(){
   	 String str = getRandom();
   	 IContext ctx = ContextManager.getContext();
   	
   	 LogisticPartnerLocEntity entity = new LogisticPartnerLocEntity();
   	 entity.setLocationCode("LC_" + str);
   	 entity.setLocationType(LogisticPartnerLocType.ADMIN_OFC);
   	 entity.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
   	//entity.setRevisionControl(new RevisionControlBean());
   	 
   	 
   	 return entity;
   }
}
