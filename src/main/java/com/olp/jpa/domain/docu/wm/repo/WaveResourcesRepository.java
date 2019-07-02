/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;

@NoRepositoryBean
public interface WaveResourcesRepository
		extends JpaRepository<WaveResourcesEntity, Long>, ITextRepository<WaveResourcesEntity, Long> {

	public List<WaveResourcesEntity> findByEmp(String whCode, String empCode);
}
