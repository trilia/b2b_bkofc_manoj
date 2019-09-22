package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;

@NoRepositoryBean
public interface LovDefinitionRepository extends JpaRepository<LovDefinitionEntity, Long>, ITextRepository<LovDefinitionEntity, Long>{
	public LovDefinitionEntity findByLovCode(String  lovCode);
}
