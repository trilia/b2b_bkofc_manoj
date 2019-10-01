package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;

@NoRepositoryBean
public interface CompositeOrderLineService extends IJpaService<CompositeOrderLineEntity, Long> {

	public CompositeOrderLineEntity findByCompOrderLine(String compOrderNum, int lineNum);
	
	public void validate(CompositeOrderLineEntity lovValues, boolean valParent, EntityVdationType type)
			throws EntityValidationException;

	public boolean checkForUpdate(CompositeOrderLineEntity neu, CompositeOrderLineEntity old);
}
