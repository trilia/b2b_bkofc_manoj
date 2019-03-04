/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface WarehouseZoneRepository extends JpaRepository<WarehouseZoneEntity, Long>, ITextRepository<WarehouseZoneEntity, Long> {
    
    public WarehouseZoneEntity findByZoneCode(String whCode, String zoneCode);
    
    public List<WarehouseZoneEntity> findByWarehouseCode(String whCode);
    
}
