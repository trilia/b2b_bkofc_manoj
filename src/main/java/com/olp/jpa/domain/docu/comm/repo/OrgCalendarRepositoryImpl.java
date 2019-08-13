package com.olp.jpa.domain.docu.comm.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;

@Repository("orgCalendarRepository")
public class OrgCalendarRepositoryImpl extends AbstractRepositoryImpl<OrgCalendarEntity, Long>
implements OrgCalendarRepository {

	@Override
	public OrgCalendarEntity findbyCalendarCode(String calCode) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<OrgCalendarEntity> query = getEntityManager().createNamedQuery("OrgCalendarEntity.findByCalendarCode", OrgCalendarEntity.class);
	        query.setParameter("calCode", calCode);
	        query.setParameter("tenant", tid);
	        OrgCalendarEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return null;
	}
}
