/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;

@Repository("unitOfMeasureRepository")
public class UnitOfMeasureRepositoryImpl extends AbstractRepositoryImpl<UnitOfMeasureEntity, Long>
		implements UnitOfMeasureRepository {

	@Override
	public UnitOfMeasureEntity findByUomCode(String uomCode) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<UnitOfMeasureEntity> query = getEntityManager().createNamedQuery("UnitOfMeasureEntity.findByUomCode", UnitOfMeasureEntity.class);
	        query.setParameter("code", uomCode);
	        query.setParameter("tenant", tid);
	        UnitOfMeasureEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return("t.srcConversions");
	}
}
