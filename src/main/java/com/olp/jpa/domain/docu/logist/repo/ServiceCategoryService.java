package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;

@NoRepositoryBean
public interface ServiceCategoryService extends IJpaService<ServiceCategoryEntity, Long>{
	public List<ServiceCategoryEntity> findBySrcSvcClassCode(String svcClassCode);
	
	public List<ServiceCategoryEntity> findByDestSvcClassCode(String svcClassCode);

	public ServiceCategoryEntity findBySvcClassCode(String svcClassCode);

	public boolean checkForUpdate(ServiceCategoryEntity newServiceCategoryEntity,
			ServiceCategoryEntity oldServiceCategoryEntity);

	public void validate(ServiceCategoryEntity newAccountSubCategory, boolean b, EntityVdationType preInsert) throws EntityValidationException;

}
