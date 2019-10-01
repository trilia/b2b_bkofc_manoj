package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.CompositeOrderLineEntity;

@NoRepositoryBean
public interface CompositeOrderLineRepository
		extends JpaRepository<CompositeOrderLineEntity, Long>, ITextRepository<CompositeOrderLineEntity, Long> {
	public CompositeOrderLineEntity findByCompOrderLine(String compOrderNum, int lineNum);
}
