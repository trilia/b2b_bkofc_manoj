package com.olp.jpa.domain.docu.logist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;

@NoRepositoryBean
public interface ServiceClassRepository extends JpaRepository<ServiceClassEntity, Long>, ITextRepository<ServiceClassEntity, Long>{
	public ServiceClassEntity findBySvcClassCode(String svcClassCode);
}
