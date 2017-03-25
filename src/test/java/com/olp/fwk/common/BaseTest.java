/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.fwk.common;

import com.olp.fwk.config.ConfigurationBuilder;
import com.olp.fwk.config.error.ConfigurationException;
import org.junit.BeforeClass;

/**
 *
 * @author raghosh
 */

public abstract class BaseTest {
    
    @BeforeClass
    public static void beforeClass() throws ConfigurationException {
        //ConfigurationBuilder.startup();
        ContextManager.getContext("1001");
    }
    
    public static void afterClass() throws ConfigurationException {
        //ConfigurationBuilder.shutdown();
    }
    
    protected static String getRandom() {
        
        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        
        return(s);
    }
    
}
