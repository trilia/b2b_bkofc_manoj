package com.olp.jpa.domain.docu.wm.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;

@Repository("waveTasksRepository")
public class WaveTasksRepositoryImpl extends AbstractRepositoryImpl<WaveTasksEntity, Long>
		implements WaveTasksRepository {

	@Override
	public WaveTasksEntity getBatchByNum(String batchNum) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<WaveTasksEntity> query = getEntityManager().createNamedQuery("WaveTasksEntity.findByBatch", WaveTasksEntity.class);
        query.setParameter("batchNum", batchNum);
        query.setParameter("tenant", tid);
        
        WaveTasksEntity waveResourcesEntity = query.getSingleResult();
        return waveResourcesEntity;
	}

	@Override
	public String getLazyLoadElements() {
		return ("t.waveBatchRef");
	}
}
