package com.olp.jpa.domain.docu.wm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;

@NoRepositoryBean
public interface WaveTasksService extends IJpaService<WaveTasksEntity, Long> {
	
	public WaveTasksEntity getBatchByNum(String batchNum);
	
	public void validate(WaveTasksEntity entity, boolean valParent, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(WaveTasksEntity newWaveResource, WaveTasksEntity oldWaveResource);

}
