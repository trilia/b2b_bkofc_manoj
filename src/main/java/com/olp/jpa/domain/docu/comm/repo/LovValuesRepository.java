package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.comm.model.LovValuesEntity;

@NoRepositoryBean
public interface LovValuesRepository extends JpaRepository<LovValuesEntity, Long>, ITextRepository<LovValuesEntity, Long>{
	public LovValuesEntity findByLovValue(String  lovCode, String value);
}
