package com.olp.jpa.domain.docu.fin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.fin.model.FinCalendarEntity;

@NoRepositoryBean
public interface FinCalendarRepository extends JpaRepository<FinCalendarEntity, Long>, ITextRepository<FinCalendarEntity, Long> {

	public FinCalendarEntity findbyCalendarCode(String  calCode, String periodMan);
}
