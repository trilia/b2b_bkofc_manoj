package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;

@Repository("serviceCategoryRepository")
public class ServiceCategoryRepositoryImpl extends AbstractRepositoryImpl<ServiceCategoryEntity, Long>
		implements ServiceCategoryRepository {

	@Override
	@Transactional(readOnly = true)
	public List<ServiceCategoryEntity> findBySrcSvcClassCode(String svcClassCode) {
		IContext ctx = ContextManager.getContext();

		TypedQuery<ServiceCategoryEntity> query = getEntityManager()
				.createNamedQuery("ServiceCategoryEntity.findBySrcSvcClassCode", ServiceCategoryEntity.class);
		query.setParameter("svcClassCode", svcClassCode);
		List<ServiceCategoryEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ServiceCategoryEntity> findByDestSvcClassCode(String svcClassCode) {
		IContext ctx = ContextManager.getContext();

		TypedQuery<ServiceCategoryEntity> query = getEntityManager()
				.createNamedQuery("ServiceCategoryEntity.findByDestSvcClassCode", ServiceCategoryEntity.class);
		query.setParameter("svcClassCode", svcClassCode);
		List<ServiceCategoryEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

	@Override
	public ServiceCategoryEntity findBySvcClassCode(String svcClassCode) {
		IContext ctx = ContextManager.getContext();

		TypedQuery<ServiceCategoryEntity> query = getEntityManager()
				.createNamedQuery("ServiceCategoryEntity.findBySvcClassCode", ServiceCategoryEntity.class);
		query.setParameter("svcClassCode", svcClassCode);
		ServiceCategoryEntity bean = query.getSingleResult();

		return (bean);
	}

}
