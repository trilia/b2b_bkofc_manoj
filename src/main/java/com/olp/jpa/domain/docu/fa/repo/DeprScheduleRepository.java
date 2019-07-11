package com.olp.jpa.domain.docu.fa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;

@NoRepositoryBean
public interface DeprScheduleRepository extends JpaRepository<DeprScheduleEntity, Long>, ITextRepository<DeprScheduleEntity, Long> {

	public DeprScheduleEntity findByScheduleCode(String deprScheduleCode);
}
