/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Repository("warehouseZoneRepository")
public class WarehouseZoneRepositoryImpl extends AbstractRepositoryImpl<WarehouseZoneEntity, Long> implements WarehouseZoneRepository {
    
    @Override
    @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
    public WarehouseZoneEntity findByZoneCode(String whCode, String zoneCode) {
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<WarehouseZoneEntity> query = getEntityManager().createNamedQuery("WarehouseZone.findByZoneCode", WarehouseZoneEntity.class);
        query.setParameter("whCode", whCode); 
        query.setParameter("zoneCode", zoneCode); 
        query.setParameter("tenant", tid);
        
        WarehouseZoneEntity bean = query.getSingleResult();
        
        return(bean);
    }
    
    @Override
    @Transactional(readOnly=true, noRollbackFor={javax.persistence.NoResultException.class})
    public List<WarehouseZoneEntity> findByWarehouseCode(String whCode) {
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<WarehouseZoneEntity> query = getEntityManager().createNamedQuery("WarehouseZone.findByWhCode", WarehouseZoneEntity.class);
        query.setParameter("whCode", whCode); 
        query.setParameter("tenant", tid);
        
        List<WarehouseZoneEntity> bean = query.getResultList();
        
        return(bean);
        
    }

    @Override
    public String getLazyLoadElements() {
        return("t.locators");
    }
}
