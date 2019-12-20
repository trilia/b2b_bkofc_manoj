package com.olp.jpa.domain.docu.logist.repo;

import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;

@Repository("serviceClassRepository")
public class ServiceClassRepositoryImpl extends AbstractRepositoryImpl<ServiceClassEntity, Long>
		implements ServiceClassRepository {

	@Override
	@Transactional(readOnly = true)
	public ServiceClassEntity findBySvcClassCode(String svcClassCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<ServiceClassEntity> query = getEntityManager()
				.createNamedQuery("ServiceClassEntity.findBySvcClassCode", ServiceClassEntity.class);
		query.setParameter("svcClassCode", svcClassCode);
		ServiceClassEntity bean = query.getSingleResult();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
