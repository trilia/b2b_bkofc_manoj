package com.olp.jpa.domain.docu.llty.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;

@Repository("loyaltyProgramRepository")
public class LoyaltyProgramRepositoryImpl extends AbstractRepositoryImpl<LoyaltyProgramEntity, Long> implements LoyaltyProgramRepository {

	@Override
	public String getLazyLoadElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public LoyaltyProgramEntity findByProgramCode(String programCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<LoyaltyProgramEntity> query = getEntityManager().createNamedQuery("LoyaltyProgramEntity.findByProgramCode", LoyaltyProgramEntity.class);
        query.setParameter("programCode", programCode);
        query.setParameter("tenant", tid);
        LoyaltyProgramEntity bean = query.getSingleResult();
        
        return(bean);
	}

}
