package com.olp.jpa.domain.docu.wm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;

@NoRepositoryBean
public interface WaveTasksRepository
		extends JpaRepository<WaveTasksEntity, Long>, ITextRepository<WaveTasksEntity, Long> {
	
	public WaveTasksEntity getBatchByNum(String batchNum);
}
