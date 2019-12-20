package com.olp.jpa.domain.docu.logist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;

@NoRepositoryBean
public interface LogisticsCostRepository extends JpaRepository<LogisticsCostEntity, Long>, ITextRepository<LogisticsCostEntity, Long>{

	//public LogisticsCostEntity findBySvcCategoryCode(String svcClassCode);
}
