package com.olp.jpa.domain.docu.fa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.fa.model.Asset;
import com.olp.jpa.domain.docu.fa.model.AssetCategory;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;
import com.olp.jpa.domain.docu.fa.model.AssetEntity;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.FaEnums.AssetLifeCycleStage;
import com.olp.jpa.domain.docu.fa.model.FaEnums.DepreciationType;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.domain.docu.fa.repo.AssetCategoryService;
import com.olp.jpa.domain.docu.fa.repo.AssetService;
import com.olp.jpa.domain.docu.fa.repo.DeprScheduleService;

public class AssetServiceTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("deprScheduleService")
	private DeprScheduleService deprScheduleService;
	
	@Autowired
	@Qualifier("assetCategoryService")
	private AssetCategoryService assetCategoryService;
	
	@Autowired
	@Qualifier("assetService")
	private AssetService assetService;
	
	
	@Before
	public void before() {
		deprScheduleService.deleteAll(false);
		assetCategoryService.deleteAll(false);
		assetService.deleteAll(false);
		setup();
	}
	
	@Test
	public void testAssetService() {
		List<AssetEntity> assets = assetService.findAll();
		assertNotNull("assets list cannot be null", assets);
		
		assertEquals("deprSchedules size", assets.size(), 1);
	}
	
	@Test
	public void testUpdateAssets() {
		List<AssetEntity> assets = assetService.findAll();
		Asset asset = assets.get(0).convertTo(0);
		
		assertEquals("AssetCategoryEntity size", assets.size(), 1);
	
		asset.setAcquisitionCost(new BigDecimal(11.11));
		//TODO -need to check for update proper setup 
		/*AssetEntity entity = asset.convertTo(0);
		assetService.update(entity);
		assets = assetService.findAll();
		assertEquals("AcquisitionCost", assets.get(0).getAcquisitionCost(), 11.11);*/
		
	}
	
	private void setup(){
		DeprScheduleEntity deprScheduleEntity = new DeprScheduleEntity();
		deprScheduleEntity.setDeprPct(1.1F);
		deprScheduleEntity.setDeprScheduleCode("123");
		deprScheduleEntity.setDeprType(DepreciationType.SLM);
		deprScheduleEntity.setDeprTypeImpl("testimpl");
		deprScheduleEntity.setDeprScheduleName("TestDepr");
		deprScheduleEntity.setLifecycleStage(LifeCycleStage.NEW);
		
		deprScheduleService.add(deprScheduleEntity);
		
		AssetCategoryEntity assetCategoryEntity = new AssetCategoryEntity();
		assetCategoryEntity.setCategoryCode("12");
		assetCategoryEntity.setDefaultDeprScheduleCode("1234");
		assetCategoryEntity.setDefaultDeprScheduleRef(deprScheduleEntity);
		assetCategoryEntity.setDefaultPrefix("testPref");
		assetCategoryEntity.setLifeCycleStage(LifeCycleStage.NEW);
		
		assetCategoryService.add(assetCategoryEntity);
		
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setAcquisitionCost(new BigDecimal(1.2));
		assetEntity.setAcquisitionDate(new java.util.Date());
		assetEntity.setAssetCode("123");
		assetEntity.setCategoryCode("123");
		assetEntity.setDateWrittenOff(new java.util.Date());
		assetEntity.setDeprScheduleCode("1234");
		assetEntity.setDeprScheduleRef(deprScheduleEntity);
		assetEntity.setCategoryRef(assetCategoryEntity);
		assetEntity.setWrittenOffValue(new BigDecimal(3.4));
		assetEntity.setRetiredDate(new java.util.Date());
		assetEntity.setResidualValue(new BigDecimal(11.4));
		assetEntity.setAssetLifecycle(AssetLifeCycleStage.NEW);
		
		assetService.add(assetEntity);
			
	}
	
}
