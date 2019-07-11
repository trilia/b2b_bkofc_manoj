package com.olp.jpa.domain.docu.fa.repo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import com.olp.jpa.domain.docu.fa.model.AssetEntity;

@Repository("assetRepository")
public class AssetRepositoryImpl extends AbstractRepositoryImpl<AssetEntity, Long> implements AssetRepository {

	@Override
	public AssetEntity findByAssetCode(String assetCode) {
		IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<AssetEntity> query = getEntityManager().createNamedQuery("AssetEntity.findByAssetCode", AssetEntity.class);
        query.setParameter("code", assetCode);
        query.setParameter("tenant", tid);
        AssetEntity bean = query.getSingleResult();
        
        return(bean);
	}

	@Override
	public String getLazyLoadElements() {
		return "";
	}

}
