package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;

@Repository("programTierRepository")
public class ProgramTierRepositoryImpl extends AbstractRepositoryImpl<ProgramTierEntity, Long> implements ProgramTierRepository {

	@Override
	@Transactional(readOnly=true)
	public ProgramTierEntity findByTierCode( String tierCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findByTierCode", ProgramTierEntity.class);
        //query.setParameter("programCode", programCode);
        query.setParameter("tierCode", tierCode);
        query.setParameter("tenantId", tid);
        ProgramTierEntity bean = query.getSingleResult();
        
        return bean;
	}

	@Override
	@Transactional(readOnly=true)
	public ProgramTierEntity findByTierSequence(String programCode, int sequence) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findByTierSequence", ProgramTierEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("sequence", sequence);
        query.setParameter("tenantId", tid);
        ProgramTierEntity bean = query.getSingleResult();
        
        return bean;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ProgramTierEntity findByProgramCode(String programCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findByProgramCode", ProgramTierEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("tenantId", tid);
        ProgramTierEntity bean = query.getSingleResult();
        
        return bean;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ProgramTierEntity> findAllSequencesByProgramCode(String programCode){
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<ProgramTierEntity> query = getEntityManager().createNamedQuery("ProgramTierEntity.findAllSequencesByProgramCode", ProgramTierEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("tenantId", tid);
        List<ProgramTierEntity> listOfSequences = query.getResultList();
        
        return listOfSequences;
	}
	
	@Override
	public String getLazyLoadElements() {
		return null;
	}


}
