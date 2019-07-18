package com.olp.jpa.domain.docu.fa.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;

@NoRepositoryBean
public interface AssetCategoryService extends IJpaService<AssetCategoryEntity, Long>{
	public void validate(AssetCategoryEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;
	 
	public boolean checkForUpdate(AssetCategoryEntity neu, AssetCategoryEntity old) throws EntityValidationException;

	public AssetCategoryEntity findByCategoryCode(String categoryCode);

}
