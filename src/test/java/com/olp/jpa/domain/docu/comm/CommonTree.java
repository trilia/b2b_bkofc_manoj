/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.comm;

import com.olp.fwk.common.BaseTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.comm.model.CategoryTreeEntity;

/**
 *
 * @author raghosh
 */
public class CommonTree extends BaseTest {
    
    public static CategoryTreeEntity makeCatgTree() {
        
        String s = getRandom();
        IContext ctx = ContextManager.getContext();
        
        CategoryTreeEntity tree = new CategoryTreeEntity();
        tree.setCategoryCode("CATG_" + s);
        tree.setTenantId(ctx.getTenantId());
        
        tree.setCategoryName("Category " + s);
        tree.setCategorySource("UT_SOURCE");
        tree.setCategoryClass("UT_CLASS");
        tree.setIsEnabled(true);
        
        return(tree);
    }
    
}
