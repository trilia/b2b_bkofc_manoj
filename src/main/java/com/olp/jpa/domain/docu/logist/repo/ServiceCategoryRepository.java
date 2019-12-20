package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;

@NoRepositoryBean
public interface ServiceCategoryRepository
		extends JpaRepository<ServiceCategoryEntity, Long>, ITextRepository<ServiceCategoryEntity, Long> {
	public List<ServiceCategoryEntity> findBySrcSvcClassCode(String svcClassCode);

	public List<ServiceCategoryEntity> findByDestSvcClassCode(String svcClassCode);
	
	public ServiceCategoryEntity findBySvcClassCode(String svcClassCode);
	
}
