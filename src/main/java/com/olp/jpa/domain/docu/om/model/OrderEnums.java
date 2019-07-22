/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.om.model;

/**
 *
 * @author raghosh
 */
public class OrderEnums {
    
    public static enum OrderType {
        FIXED_COST,
        VARIABLE_COST,
        HYBRID
    };
    
    public static enum OrderSource {
        CUSTOMER,
        ORCHAESTRATION,
        INTERNAL
    };
    
    public static enum DeliveryType {
        STANDARD,
        PRIORITY,
        COMMITTED
    };
    
    public static enum OrderStatus {
        RECEIVED,
        PROCESSING,
        FULFILLED
    };
    
    public static enum OrderLineType {
        ITEM,
        PROMOTION
    }
    
    public static enum OrderLineStatus {
        RECEIVED,
        RELEASED_TO_INVENTORY,
        PICKED,
        PACKED,
        DISPATCHED,
        IN_TRANSIT,
        DELIVERED
    }
    
}
