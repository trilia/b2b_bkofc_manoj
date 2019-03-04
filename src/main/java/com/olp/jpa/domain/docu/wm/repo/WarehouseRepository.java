/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long>, ITextRepository<WarehouseEntity, Long> {
    
    public WarehouseEntity findByWarehouseCode(String warehouseCode);
    
}
