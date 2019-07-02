/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;

@NoRepositoryBean
public interface UomConversionRepository
		extends JpaRepository<UomConversionEntity, Long>, ITextRepository<UomConversionEntity, Long> {

	public List<UomConversionEntity> findBySrcUom(String srcUomCode);

	public List<UomConversionEntity> findByDestUom(String destUomCode);

	public UomConversionEntity findBySrcTarget(String srcUomCode, String destUomCode);

}
