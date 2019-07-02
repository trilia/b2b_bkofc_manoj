package com.olp.jpa.domain.docu.wm.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;

@NoRepositoryBean
public interface WaveResourcesService extends IJpaService<WaveResourcesEntity, Long>{
	public void validate(WaveResourcesEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;
	 
	public boolean checkForUpdate(WaveResourcesEntity neu, WaveResourcesEntity old) throws EntityValidationException;

	public List<WaveResourcesEntity> findByEmp(String whCode, String empCode);
}
