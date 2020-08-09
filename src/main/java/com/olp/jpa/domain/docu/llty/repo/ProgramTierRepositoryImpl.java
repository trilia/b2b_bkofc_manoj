package com.olp.jpa.domain.docu.llty.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;

@Repository("programTierRepository")
public class ProgramTierRepositoryImpl extends AbstractRepositoryImpl<ProgramTierEntity, Long> implements ProgramTierRepository {

	@Override
	public ProgramTierEntity findByTierCode(String programCode, String tierCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findByProgramCode", ProgramTierEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("tierCode", tierCode);
        query.setParameter("tenant", tid);
        ProgramTierEntity bean = query.getSingleResult();
        
        return bean;
	}

	@Override
	public ProgramTierEntity findByTierSequence(String programCode, int sequence) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findByTierSequence", ProgramTierEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("sequence", sequence);
        query.setParameter("tenant", tid);
        ProgramTierEntity bean = query.getSingleResult();
        
        return bean;
	}
	
	@Override
	public String getLazyLoadElements() {
		return null;
	}


}
