package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.Country;
import com.olp.jpa.domain.docu.logist.model.ServiceAreaEntity;

@Repository("serviceAreaRepository")
public class ServiceAreaRepositoryImpl extends AbstractRepositoryImpl<ServiceAreaEntity, Long>
		implements ServiceAreaRepository {

	@Override
	@Transactional(readOnly = true)
	public List<ServiceAreaEntity> findBySvcClassCode(String svcClassCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<ServiceAreaEntity> query = getEntityManager()
				.createNamedQuery("ServiceAreaEntity.findBySvcClassCode", ServiceAreaEntity.class);
		query.setParameter("svcClassCode", svcClassCode);
		List<ServiceAreaEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isServiceable(String postalCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServiceAreaEntity> findByCountry(Country country) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<ServiceAreaEntity> query = getEntityManager().createNamedQuery("ServiceAreaEntity.findByCountry",
				ServiceAreaEntity.class);
		query.setParameter("country", country);
		List<ServiceAreaEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServiceAreaEntity> findByPartnerId(String partnerId) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<ServiceAreaEntity> query = getEntityManager().createNamedQuery("ServiceAreaEntity.findByPartnerId",
				ServiceAreaEntity.class);
		query.setParameter("partnerId", partnerId);
		List<ServiceAreaEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
