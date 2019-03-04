/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface WarehouseService  extends IJpaService<WarehouseEntity, Long> { 
    
    public WarehouseEntity findByWarehouseCode(String warehouseCode);
    
    public Long requestStatusChange(String warehouseCode, CommonEnums.LifeCycleStatus status) throws EntityValidationException;

    public void validate(WarehouseEntity entity, EntityVdationType type) throws EntityValidationException;
  
}

