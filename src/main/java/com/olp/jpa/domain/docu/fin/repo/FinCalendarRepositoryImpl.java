package com.olp.jpa.domain.docu.fin.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fin.model.FinCalendarEntity;

@Repository("finCalendarRepository")
public class FinCalendarRepositoryImpl extends AbstractRepositoryImpl<FinCalendarEntity, Long>
implements FinCalendarRepository {

	@Override
	public FinCalendarEntity findbyCalendarCode(String calCode, String periodMan) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<FinCalendarEntity> query = getEntityManager().createNamedQuery("FinCalendarEntity.findByCalendarCode", FinCalendarEntity.class);
	        query.setParameter("calCode", calCode);
	        query.setParameter("periodMan", periodMan);
	        query.setParameter("tenant", tid);
	        FinCalendarEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
