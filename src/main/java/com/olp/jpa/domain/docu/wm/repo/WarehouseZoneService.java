/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import java.util.List;

/**
 *
 * @author raghosh
 * 
 */
@NoRepositoryBean
public interface WarehouseZoneService  extends IJpaService<WarehouseZoneEntity, Long> { 
    
    public WarehouseZoneEntity findByZoneCode(String whCode, String zoneCode);
    
    public List<WarehouseZoneEntity> findByWarehouseCode(String whCode);

    public void validate(WarehouseZoneEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;
    
    public boolean checkForUpdate(WarehouseZoneEntity neu, WarehouseZoneEntity old);
  
}