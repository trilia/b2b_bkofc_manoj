package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;

@NoRepositoryBean
public interface OrgCalendarService extends IJpaService<OrgCalendarEntity, Long>{
	public OrgCalendarEntity findbyCalendarCode(String  calCode);

	public void validate(OrgCalendarEntity entity, EntityVdationType type) throws EntityValidationException;

	Long requestStatusChange(String uomCode, LifeCycleStage status) throws EntityValidationException;
}
