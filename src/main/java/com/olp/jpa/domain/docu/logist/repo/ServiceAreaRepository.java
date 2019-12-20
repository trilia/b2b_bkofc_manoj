package com.olp.jpa.domain.docu.logist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.Country;
import com.olp.jpa.domain.docu.logist.model.ServiceAreaEntity;

@NoRepositoryBean
public interface ServiceAreaRepository extends JpaRepository<ServiceAreaEntity, Long>, ITextRepository<ServiceAreaEntity, Long>{

	public List<ServiceAreaEntity>  findBySvcClassCode(String svcClassCode);
	
	public Boolean isServiceable(String postalCode);

	public List<ServiceAreaEntity> findByCountry(Country country);

	public List<ServiceAreaEntity> findByPartnerId(String partnerId);
}
