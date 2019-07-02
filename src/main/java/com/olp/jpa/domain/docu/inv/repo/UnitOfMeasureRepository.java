/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;

@NoRepositoryBean
public interface UnitOfMeasureRepository
		extends JpaRepository<UnitOfMeasureEntity, Long>, ITextRepository<UnitOfMeasureEntity, Long> {

	public UnitOfMeasureEntity  findByUomCode(String uomCode);
}
