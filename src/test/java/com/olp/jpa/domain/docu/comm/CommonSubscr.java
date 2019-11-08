/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.comm;

import com.olp.fwk.common.BaseTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.comm.model.PlanAttributeBean;
import com.olp.jpa.domain.docu.comm.model.SubscriberEntity;
import com.olp.jpa.domain.docu.comm.model.SubscriptionPlanEntity;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author raghosh
 */
public class CommonSubscr extends BaseTest {
    
    public static SubscriptionPlanEntity makeSubscrPlan() {
        
        SubscriptionPlanEntity spe = new SubscriptionPlanEntity();
        String str = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        spe.setIsEnabled(true);
        
        ArrayList<PlanAttributeBean> attrList = new ArrayList<>();
        PlanAttributeBean bean1 = new PlanAttributeBean();
        bean1.setAttrName("one"); bean1.setAttrValue("one"); bean1.setAttrSequence(10);
        
        PlanAttributeBean bean2 = new PlanAttributeBean();
        bean2.setAttrName("two"); bean2.setAttrValue("two"); bean2.setAttrSequence(20);
        
        PlanAttributeBean bean3 = new PlanAttributeBean();
        bean3.setAttrName("three"); bean3.setAttrValue("three"); bean3.setAttrSequence(30);
        
        attrList.add(bean1); attrList.add(bean2); attrList.add(bean3);
        
        //spe.setPlanAttrs(attrList);
        spe.setPlanCategory("TEST");
        spe.setPlanCode("PLAN_" + str);
        spe.setPlanName("Plan " + str);
        spe.setStartDate(new Date());
        spe.setTenantId(tid);
        
        return(spe);
    }
    
    public static SubscriberEntity makeSubscriber() {
        
        SubscriberEntity s = new SubscriberEntity();
        String str = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        RevisionControlBean ctrl = new RevisionControlBean();
        ctrl.setCreatedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setRevisedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setObjectVersionNumber("1.0.0");
        
        
        s.setSubscriberName("Test_" + str);
        s.setPartitionCode(str);
        s.setSubscriberEmail(str + "@abc.com");
        s.setSubscriberPhone("+11334455");
        s.setRevisionControl(ctrl);

        return(s);
    }
    
}
