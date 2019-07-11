package com.olp.jpa.domain.docu.fa.repo;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;

public interface DeprScheduleService extends IJpaService<DeprScheduleEntity, Long>{
	public void validate(DeprScheduleEntity entity, boolean valParent, EntityVdationType type) throws EntityValidationException;
	 
	public boolean checkForUpdate(DeprScheduleEntity neu, DeprScheduleEntity old) throws EntityValidationException;

	public DeprScheduleEntity findByScheduleCode(String deprScheduleCode);

}
