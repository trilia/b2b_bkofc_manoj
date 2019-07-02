/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.repo;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;

@Repository("uomConversionRepository")
public class UomConversionRepositoryImpl extends AbstractRepositoryImpl<UomConversionEntity, Long>
		implements UomConversionRepository {

	@Override
	public List<UomConversionEntity> findBySrcUom(String srcUomCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<UomConversionEntity> query = getEntityManager().createNamedQuery("UomConversionEntity.findBySrcUom",
				UomConversionEntity.class);
		query.setParameter("srcUomCode", srcUomCode);
		query.setParameter("tenant", tid);
		List<UomConversionEntity> bean = query.getResultList();

		return (bean);
	}

	@Override
	public List<UomConversionEntity> findByDestUom(String destUomCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<UomConversionEntity> query = getEntityManager().createNamedQuery("UomConversionEntity.findByDestUom",
				UomConversionEntity.class);
		query.setParameter("destUomCode", destUomCode);
		query.setParameter("tenant", tid);
		List<UomConversionEntity> bean = query.getResultList();
		return (bean);
	}

	@Override
	public UomConversionEntity findBySrcTarget(String srcUomCode, String destUomCode) {
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		TypedQuery<UomConversionEntity> query = getEntityManager()
				.createNamedQuery("UomConversionEntity.findBySrcTarget", UomConversionEntity.class);
		query.setParameter("srcUomCode", srcUomCode);
		query.setParameter("destUomCode", destUomCode);
		query.setParameter("tenant", tid);
		UomConversionEntity bean = query.getSingleResult();
		return (bean);
	}

	@Override
	public String getLazyLoadElements() {
		return ("t.srcUomRef , t.destUomRef");// need to check if this is how multiple child lazy loads 
	}

}
