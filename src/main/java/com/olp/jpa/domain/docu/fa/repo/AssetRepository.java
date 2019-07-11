package com.olp.jpa.domain.docu.fa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fa.model.AssetEntity;

@NoRepositoryBean
public interface AssetRepository extends JpaRepository<AssetEntity, Long>, ITextRepository<AssetEntity, Long> {

	public AssetEntity findByAssetCode(String assetCode);

}
