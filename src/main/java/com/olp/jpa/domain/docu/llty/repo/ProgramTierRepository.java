package com.olp.jpa.domain.docu.llty.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;

@NoRepositoryBean
public interface ProgramTierRepository extends JpaRepository<ProgramTierEntity, Long>, ITextRepository<ProgramTierEntity, Long>{

	public ProgramTierEntity findByTierCode(String programCode, String tierCode);
	
	public ProgramTierEntity findByTierSequence(String programCode, int sequence);
}
