package com.olp.jpa.domain.docu.fa.repo;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fa.model.AssetEntity;

public interface AssetService extends IJpaService<AssetEntity, Long>{
	public void validate(AssetEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;
	 
	public boolean checkForUpdate(AssetEntity neu, AssetEntity old) throws EntityValidationException;

	public AssetEntity findByAssetCode(String assetCode);
}
