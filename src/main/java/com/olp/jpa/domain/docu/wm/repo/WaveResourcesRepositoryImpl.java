/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;

@Repository("waveResourcesRepository")
public class WaveResourcesRepositoryImpl extends AbstractRepositoryImpl<WaveResourcesEntity, Long>
		implements WaveResourcesRepository {
	
	public List<WaveResourcesEntity> findByEmp(String whCode, String empCode){
		
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<WaveResourcesEntity> query = getEntityManager().createNamedQuery("WaveResourcesEntity.findByEmp", WaveResourcesEntity.class);
        query.setParameter("whCode", whCode);
        query.setParameter("tenant", tid);
        query.setParameter("empCode", empCode);
        
        List<WaveResourcesEntity> waveResourcesEntityList = query.getResultList();
       	
		return waveResourcesEntityList;
	}
	
	@Override
	public String getLazyLoadElements() {
		return("t.waveBatchRef");
	}

}
