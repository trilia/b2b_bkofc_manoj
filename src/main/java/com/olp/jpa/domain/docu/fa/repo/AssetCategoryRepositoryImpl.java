package com.olp.jpa.domain.docu.fa.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;

@Repository("assetCategoryRepository")
public class AssetCategoryRepositoryImpl extends AbstractRepositoryImpl<AssetCategoryEntity, Long>
		implements AssetCategoryRepository {

	@Override
	public AssetCategoryEntity findByCategoryCode(String categoryCode) {
		 IContext ctx = ContextManager.getContext();
	        String tid = ctx.getTenantId();
	        
	        TypedQuery<AssetCategoryEntity> query = getEntityManager().createNamedQuery("AssetCategoryEntity.findByCategoryCode", AssetCategoryEntity.class);
	        query.setParameter("code", categoryCode);
	        query.setParameter("tenant", tid);
	        AssetCategoryEntity bean = query.getSingleResult();
	        
	        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
