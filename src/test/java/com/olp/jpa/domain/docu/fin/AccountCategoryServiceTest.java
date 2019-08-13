package com.olp.jpa.domain.docu.fin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.fin.model.AccountCategory;
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategory;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.domain.docu.fin.repo.AccountCategoryService;
import com.olp.jpa.domain.docu.fin.repo.AccountSubCategoryService;

public class AccountCategoryServiceTest extends BaseSpringAwareTest{

	@Autowired
	@Qualifier("accountCategoryService")
	private AccountCategoryService accountCategoryService;
	
	@Autowired
	@Qualifier("accountSubCategoryService")
	private AccountSubCategoryService accountSubCategoryService;
	
	@Before
	public void before() {
		accountCategoryService.deleteAll(false);
		accountSubCategoryService.deleteAll(false);
		setup();

	}
	
	@Test
	public void testAccountCategory() {
		List<AccountCategoryEntity> accountCategories = accountCategoryService.findAll();
		assertNotNull("wavebatch list cannot be null", accountCategories);
	}

	@Test
	public void updateAccountCategory(){
		List<AccountCategoryEntity> accountCategories = accountCategoryService.findAll();
		AccountCategory accountCategory = accountCategories.get(0).convertTo(0);
		
		List<AccountSubCategory> asc = accountCategory.getAccountSubCategories();
		assertEquals("AccountSubCategory size", asc.size(), 2);
		
		AccountSubCategoryEntity sc3 = CommonFin.makeAccountSubCategory();
		sc3.setCategoryCode(accountCategory.getCategoryCode());
		sc3.setCategoryRef(accountCategories.get(0));
		sc3.setLifecycleStatus(LifeCycleStatus.ACTIVE);
		
		AccountSubCategory asubCat = sc3.convertTo(0);
		asubCat.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
		asc.add(asubCat);
		AccountCategoryEntity ace = accountCategory.convertTo(0);
		accountCategoryService.update(ace);

		accountCategories= accountCategoryService.findAll();
		assertEquals("accountSubCategories size", accountCategories.get(0).getAccountSubCategories().size(), 3);
	}
	public void setup() {
		AccountCategoryEntity accountCategory = CommonFin.makeAccountCategory();
		
		AccountSubCategoryEntity sc1 = CommonFin.makeAccountSubCategory();
		sc1.setCategoryCode(accountCategory.getCategoryCode());
		sc1.setCategoryRef(accountCategory);
		
		AccountSubCategoryEntity sc2 = CommonFin.makeAccountSubCategory();
		sc2.setCategoryCode(accountCategory.getCategoryCode());
		sc2.setCategoryRef(accountCategory);
		
		List<AccountSubCategoryEntity> accountSubCategories = new ArrayList<>();
		accountSubCategories.add(sc1);
		accountSubCategories.add(sc2);
		
		accountSubCategoryService.add(sc1);
		accountSubCategoryService.add(sc2);
		
		accountCategory.setAccountSubCategories(accountSubCategories);
		
		accountCategoryService.add(accountCategory);
	}
}
