package com.olp.jpa.domain.docu.llty.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;

@NoRepositoryBean
public interface ProgramTierService extends IJpaService<ProgramTierEntity, Long> {
	
	public void validate(ProgramTierEntity entity,boolean valParent, EntityVdationType type) throws EntityValidationException;

	public boolean checkForUpdate(ProgramTierEntity newprogramTierEntity,
			ProgramTierEntity oldprogramTierEntity);
	
	public ProgramTierEntity findByTierCode(String tierCode);

	public ProgramTierEntity findByTierSequence(String programCode, int sequence);
	
	public List<ProgramTierEntity> findAllSequencesByProgramCode(String programCode);

}
