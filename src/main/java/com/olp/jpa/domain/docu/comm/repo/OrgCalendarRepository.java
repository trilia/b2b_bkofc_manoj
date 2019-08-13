package com.olp.jpa.domain.docu.comm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;

@NoRepositoryBean
public interface OrgCalendarRepository extends JpaRepository<OrgCalendarEntity, Long>, ITextRepository<OrgCalendarEntity, Long> {

	public OrgCalendarEntity findbyCalendarCode(String  calCode);
}
