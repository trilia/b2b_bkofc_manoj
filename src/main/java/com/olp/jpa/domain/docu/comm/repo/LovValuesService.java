package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;

@NoRepositoryBean
public interface LovValuesService extends IJpaService<LovValuesEntity, Long> {
	public LovValuesEntity findbyLovValue(String lovCode, String value);

	public void validate(LovValuesEntity lovValues, boolean b, EntityVdationType type)throws EntityValidationException;

	public boolean checkForUpdate(LovValuesEntity newLovValues, LovValuesEntity oldLuvValues2);

}
