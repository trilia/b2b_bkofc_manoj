/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;

@Repository("waveBatchRepository")
public class WaveBatchRepositoryImpl extends AbstractRepositoryImpl<WaveBatchEntity, Long> implements 
	WaveBatchRepository{

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	@Override
	public WaveBatchEntity getBatchByNum(String batchNum){		
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<WaveBatchEntity> query = getEntityManager().createNamedQuery("WaveBatchEntity.findByBatchNum", WaveBatchEntity.class);
        query.setParameter("batchNum", batchNum);
        query.setParameter("tenant", tid);
        WaveBatchEntity bean = null ;//query.getSingleResult();
        
		return (bean);
	}
	
	@Override
	public String getLazyLoadElements() {
		return("t.waveResources t1 ");
	}
}
