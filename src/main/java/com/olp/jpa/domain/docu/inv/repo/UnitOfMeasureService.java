/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;

@NoRepositoryBean
public interface UnitOfMeasureService extends IJpaService<UnitOfMeasureEntity, Long> {
	public UnitOfMeasureEntity findByUomCode(String uomCode);

	public void validate(UnitOfMeasureEntity entity, EntityVdationType type) throws EntityValidationException;

	Long requestStatusChange(String uomCode, LifeCycleStatus status) throws EntityValidationException;
}
