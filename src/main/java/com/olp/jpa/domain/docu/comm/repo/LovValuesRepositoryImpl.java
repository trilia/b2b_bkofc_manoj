package com.olp.jpa.domain.docu.comm.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;

@Repository("lovValuesRepository")
public class LovValuesRepositoryImpl extends AbstractRepositoryImpl<LovValuesEntity, Long>
implements LovValuesRepository {

	@Override
	@Transactional(readOnly=true)
	public LovValuesEntity findByLovValue(String lovCode, String value) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<LovValuesEntity> query = getEntityManager().createNamedQuery("LovValuesEntity.findByLovValue", LovValuesEntity.class);
        query.setParameter("lovCode", lovCode);
        query.setParameter("value", value);
        query.setParameter("tenant", tid);
        LovValuesEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}
}
