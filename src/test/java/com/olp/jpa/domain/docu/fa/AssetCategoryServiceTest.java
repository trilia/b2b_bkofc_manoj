package com.olp.jpa.domain.docu.fa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.fa.model.AssetCategory;
import com.olp.jpa.domain.docu.fa.model.AssetCategoryEntity;
import com.olp.jpa.domain.docu.fa.model.DeprSchedule;
import com.olp.jpa.domain.docu.fa.model.DeprScheduleEntity;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;
import com.olp.jpa.domain.docu.fa.model.FaEnums.DepreciationType;
import com.olp.jpa.domain.docu.fa.repo.AssetCategoryService;
import com.olp.jpa.domain.docu.fa.repo.DeprScheduleService;

public class AssetCategoryServiceTest extends BaseSpringAwareTest {
	
	@Autowired
	@Qualifier("deprScheduleService")
	private DeprScheduleService deprScheduleService;
	
	@Autowired
	@Qualifier("assetCategoryService")
	private AssetCategoryService assetCategoryService;
	
	@Before
	public void before() {
		deprScheduleService.deleteAll(false);
		assetCategoryService.deleteAll(false);
		setup();
	}
	
	@Test
	public void testAssetCategory() {
		List<AssetCategoryEntity> assetCategories = assetCategoryService.findAll();
		assertNotNull("AssetCategory list cannot be null", assetCategories);
		
		assertEquals("AssetCategoryEntity size", assetCategories.size(), 1);
	}
	
	@Test
	public void testUpdateAssetCategory() {
		List<AssetCategoryEntity> assetCategories = assetCategoryService.findAll();
		AssetCategory assetCategory = assetCategories.get(0).convertTo(0);
		
		assertEquals("AssetCategoryEntity size", assetCategories.size(), 1);
	
		assetCategory.setDefaultPrefix("updatedPref");
		//assetCategory.setDefaultDeprScheduleCode("111");
		AssetCategoryEntity entity = assetCategory.convertTo(0);
		
		//TODO - needs to check 
		/*assetCategoryService.update(entity);
		
		assetCategories = assetCategoryService.findAll();
		
		assertEquals("updatedPref",assetCategories.get(0).getDefaultPrefix() );*/
		///assertEquals("111",assetCategories.get(0).getDefaultDeprScheduleCode() );
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
		
	}
}
