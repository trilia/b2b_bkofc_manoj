package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.fin.model.FinCalendarEntity;

@NoRepositoryBean
public interface FinCalendarService extends IJpaService<FinCalendarEntity, Long> {
	public FinCalendarEntity findbyCalendarCode(String  calCode, String periodMan);

	public void validate(FinCalendarEntity entity,boolean valParent, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(FinCalendarEntity neu, FinCalendarEntity old);
}
