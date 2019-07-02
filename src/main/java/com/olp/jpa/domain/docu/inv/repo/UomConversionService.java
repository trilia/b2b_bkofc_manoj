package com.olp.jpa.domain.docu.inv.repo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.olp.fwk.common.error.EntityValidationException;
import com.olp.jpa.common.CommonEnums.EntityVdationType;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.domain.docu.inv.model.UomConversionEntity;

@NoRepositoryBean
public interface UomConversionService extends IJpaService<UomConversionEntity, Long> {

	void validate(UomConversionEntity srcConversion, boolean b, EntityVdationType type) throws EntityValidationException;

	boolean checkForUpdate(UomConversionEntity newUoc, UomConversionEntity oldUoc2);
	
	public List<UomConversionEntity> findBySrcUom(String srcUomCode);

	public List<UomConversionEntity> findByDestUom(String destUomCode);

	public UomConversionEntity findBySrcTarget(String srcUomCode, String destUomCode);

}
