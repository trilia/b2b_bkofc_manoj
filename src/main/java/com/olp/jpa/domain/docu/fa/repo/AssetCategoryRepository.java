package com.olp.jpa.domain.docu.fa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;

@NoRepositoryBean
public interface AssetCategoryRepository extends JpaRepository<AssetCategoryEntity, Long>, ITextRepository<AssetCategoryEntity, Long> {
	
	public AssetCategoryEntity findByCategoryCode(String categoryCode);
}
