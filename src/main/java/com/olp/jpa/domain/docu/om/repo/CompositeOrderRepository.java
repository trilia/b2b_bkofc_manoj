package com.olp.jpa.domain.docu.om.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.om.model.CompositeOrderEntity;

@NoRepositoryBean
public interface CompositeOrderRepository extends JpaRepository<CompositeOrderEntity, Long>, ITextRepository<CompositeOrderEntity, Long> {
	public CompositeOrderEntity findByCompOrderNum(String compOrderNum);
}
