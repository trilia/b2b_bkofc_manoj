package com.olp.jpa.domain.docu.logist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.fin.model.TaxElementEntity;
import com.olp.jpa.domain.docu.fin.model.TaxGroupEntity;
import com.olp.jpa.domain.docu.fin.repo.TaxElementService;
import com.olp.jpa.domain.docu.fin.repo.TaxGroupService;
import com.olp.jpa.domain.docu.logist.model.LogisticsCostEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceCategoryEntity;
import com.olp.jpa.domain.docu.logist.model.ServiceClass;
import com.olp.jpa.domain.docu.logist.model.ServiceClassEntity;
import com.olp.jpa.domain.docu.logist.repo.LogisticsCostService;
import com.olp.jpa.domain.docu.logist.repo.ServiceCategoryService;
import com.olp.jpa.domain.docu.logist.repo.ServiceClassRepository;
import com.olp.jpa.domain.docu.logist.repo.ServiceClassService;

public class ServiceClassTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("serviceClassRepository")
	private ServiceClassRepository serviceClassRepository;

	@Autowired
	@Qualifier("serviceClassSvc")
	private ServiceClassService serviceClassService;

	@Autowired
	@Qualifier("serviceCategorySvc")
	private ServiceCategoryService serviceCategoryService;

	@Autowired
	@Qualifier("logisticsCostService")
	private LogisticsCostService logisticsCostService;

	@Autowired
	@Qualifier("taxGroupService")
	private TaxGroupService taxGroupService;

	@Autowired
	@Qualifier("taxElementService")
	private TaxElementService taxElementService;

	@Before
	public void before() {
		taxElementService.deleteAll(false);
		taxGroupService.deleteAll(false);
		logisticsCostService.deleteAll(false);
		serviceCategoryService.deleteAll(false);
		serviceClassService.deleteAll(false);

		setup();
	}

	public void setup() {
		ServiceClassEntity serviceClass = LogistCommon.makeServiceClass();
		LogisticsCostEntity logisticsCost = LogistCommon.makeLogisticsCost();
		TaxGroupEntity taxEntity = LogistCommon.makeTaxGroup();

		TaxElementEntity taxElementEntity = LogistCommon.makeTaxElement();
		taxElementEntity.setGroupCode(taxEntity.getGroupCode());
		taxElementEntity.setTaxGroupRef(taxEntity);

		List<TaxElementEntity> taxElementList = new ArrayList<>();
		taxElementList.add(taxElementEntity);
		taxEntity.setTaxElements(taxElementList);

		logisticsCost.setTaxGroupRef(taxEntity);
		logisticsCost.setTaxGroupCode(taxEntity.getGroupCode());

		List<LogisticsCostEntity> listOfLogisticsCost = new ArrayList<>();
		listOfLogisticsCost.add(logisticsCost);

		ServiceCategoryEntity serviceCategoryOne = LogistCommon.makeServiceCategory();
		serviceCategoryOne.setDestSvcClassCode(serviceClass.getSvcClassCode());
		serviceCategoryOne.setDestSvcClassRef(serviceClass);

		ServiceCategoryEntity serviceCategoryTwo = LogistCommon.makeServiceCategory();
		serviceCategoryTwo.setSrcSvcClassCode(serviceClass.getSvcClassCode());
		serviceCategoryTwo.setSrcSvcClassRef(serviceClass);
		serviceCategoryTwo.setCostEstimate(listOfLogisticsCost);

		Set<ServiceCategoryEntity> srcServiceCatgegories = new HashSet<>();
		srcServiceCatgegories.add(serviceCategoryOne);

		Set<ServiceCategoryEntity> destServiceCatgegories = new HashSet<>();
		destServiceCatgegories.add(serviceCategoryTwo);

		taxElementService.add(taxElementEntity);
		taxGroupService.add(taxEntity);
		logisticsCostService.add(logisticsCost);

		serviceCategoryService.add(serviceCategoryOne);
		serviceCategoryService.add(serviceCategoryTwo);

		serviceClass.setDestSvcCategories(destServiceCatgegories);
		serviceClass.setSrcSvcCategories(srcServiceCatgegories);

		serviceClassService.add(serviceClass);
	}

	@Test
	public void testServiceClass() {
		List<ServiceClassEntity> svcClass = serviceClassService.findAll();
		assertNotNull("ServiceClass list cannot be null", svcClass);
	}

	@Test
	public void updateServiceClass() {
		List<ServiceClassEntity> svcClass = serviceClassService.findAll();
	
		ServiceClassEntity svcClassEntity = svcClass.get(0);
		ServiceClass serviceClass = svcClassEntity.convertTo(0);
		serviceClass.setSvcClassName("updated one");
		serviceClass.getDestSvcCategories().clear();
		serviceClass.getSrcSvcCategories().clear();
		
		serviceClassService.update(serviceClass.convertTo(0));
		svcClass = serviceClassService.findAll();
		assertEquals("serviceDestCategory size", svcClass.get(0).getDestSvcCategories().size(), 0);
		assertEquals("serviceSvcCategory size", svcClass.get(0).getSrcSvcCategories().size(), 0);
		assertEquals("SvcClassName", svcClass.get(0).getSvcClassName(),"updated one");

	}
}
