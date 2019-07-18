package com.olp.jpa.domain.docu.fa.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;

@Repository("deprScheduleRepository")
public class DeprScheduleRepositoryImpl extends AbstractRepositoryImpl<DeprScheduleEntity, Long>
		implements DeprScheduleRepository {

	@Override
	public DeprScheduleEntity findByScheduleCode(String deprScheduleCode) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<DeprScheduleEntity> query = getEntityManager().createNamedQuery("DeprScheduleEntity.findByScheduleCode", DeprScheduleEntity.class);
	        query.setParameter("code", deprScheduleCode);
	        query.setParameter("tenant", tid);
	        DeprScheduleEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {		
		return null;
	}

}
