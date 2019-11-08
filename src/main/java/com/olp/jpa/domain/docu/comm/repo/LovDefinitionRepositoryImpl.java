package com.olp.jpa.domain.docu.comm.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;

@Repository("lovDefinitionRepository")
public class LovDefinitionRepositoryImpl extends AbstractRepositoryImpl<LovDefinitionEntity, Long>
implements LovDefinitionRepository {

	@Override
	@Transactional(readOnly=true)
	public LovDefinitionEntity findByLovCode(String lovCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<LovDefinitionEntity> query = getEntityManager().createNamedQuery("LovDefinitionEntity.findByLovCode", LovDefinitionEntity.class);
        query.setParameter("lovCode", lovCode);
        query.setParameter("tenant", tid);
        LovDefinitionEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}
}
