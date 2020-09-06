package com.olp.jpa.domain.docu.llty.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyalty;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTier;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTierEntity;
import com.olp.jpa.domain.docu.llty.model.CustomerLoyaltyTxnEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.Scope;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.SpendConversionAlgo;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierMoveType;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierReviewFrequency;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierValidity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TxnType;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;
import com.olp.jpa.domain.docu.om.LogisticsWM;
import com.olp.jpa.domain.docu.om.repo.CustomerService;

public class CustomerLoyaltyServiceImplTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("customerLoyaltyTierService")
	private CustomerLoyaltyTierService customerLoyaltyTierService;

	@Autowired
	@Qualifier("customerLoyaltyTxnService")
	private CustomerLoyaltyTxnService customerLoyaltyTxnService;

	@Autowired
	@Qualifier("customerLoyaltyService")
	private CustomerLoyaltyService customerLoyaltyService;

	@Autowired
	@Qualifier("customerService")
	private CustomerService customerService;

	@Autowired
	@Qualifier("loyaltyProgramService")
	private LoyaltyProgramService loyaltyProgramService;

	@Autowired
	@Qualifier("programTierService")
	private ProgramTierService programTierService;

	@Before
	public void before() {
		customerLoyaltyService.deleteAll(false);
		customerLoyaltyTxnService.deleteAll(false);
		customerLoyaltyTierService.deleteAll(false);
		customerService.deleteAll(false);
		loyaltyProgramService.deleteAll(false);
		programTierService.deleteAll(false);
		setup();
	}

	@Test
	public void testLoyaltyProgram() {
		List<CustomerLoyaltyEntity> customerLoyalty = customerLoyaltyService.findAll();
		assertNotNull("customerloyalty list cannot be null", customerLoyalty);

	}

	//@Test
	public void updateCustomerLoyalty(){
		List<CustomerLoyaltyEntity> customerLoyalties = customerLoyaltyService.findAll();
		CustomerLoyalty customerLoyalty = customerLoyalties.get(0).convertTo(0);
		
		LoyaltyProgramEntity loyaltyProgram = makeLoyaltyProgram();
		//loyaltyProgramService.add(loyaltyProgram);
		
		ProgramTierEntity pt1 = makeProgramTier();
		pt1.setProgramRef(loyaltyProgram);
		pt1.setProgramCode(loyaltyProgram.getProgramCode());
		//programTierService.add(pt1);
		
		List<CustomerLoyaltyTier> clts = customerLoyalty.getCsLoyaltyTiers();
		assertEquals("CustomerLoyaltyTier size",clts.size(), 1);
		CustomerLoyaltyTierEntity customerLoyaltyTier1 = makeCustomerLoyaltyTier();
		customerLoyaltyTier1.setCsLoyaltyCode(customerLoyalty.getCsLoyaltyCode());
		customerLoyaltyTier1.setCsLoyaltyRef(customerLoyalty.convertTo(0));
		
		customerLoyaltyTier1.setProgramTierRef(pt1);
		customerLoyaltyTier1.setProgramTierCode(pt1.getTierCode());
		
		clts.add(customerLoyaltyTier1.convertTo(0));
		CustomerLoyaltyEntity cle = customerLoyalty.convertTo(0);
		customerLoyaltyService.update(cle);

		customerLoyalties= customerLoyaltyService.findAll();
		assertEquals("customerLoyaltyTier size", customerLoyalties.get(0).getCsLoyaltyTiers().size(), 2);
		
	}

	public void setup() {
		CustomerLoyaltyEntity customerLoyalty = makeCustomerLoyalty();
		LoyaltyProgramEntity loyaltyProgram = makeLoyaltyProgram();

		CustomerEntity customerEntity = LogisticsWM.makeCustomer();
		ProgramTierEntity pt1 = makeProgramTier();
		pt1.setProgramRef(loyaltyProgram);
		pt1.setProgramCode(loyaltyProgram.getProgramCode());
		
		List<ProgramTierEntity> programTierList = new ArrayList<>();
		programTierList.add(pt1);
		loyaltyProgram.setProgramTiers(programTierList);

		CustomerLoyaltyTxnEntity customerLoyaltyTxn1 = makeCustomerLoyaltyTxn();
		customerLoyaltyTxn1.setCsLoyaltyCode(customerLoyalty.getCsLoyaltyCode());
		customerLoyaltyTxn1.setCsLoyaltyRef(customerLoyalty);
		customerLoyaltyTxn1.setCustomerRef(customerEntity);
		customerLoyaltyTxn1.setCustomerCode(customerEntity.getCustomerCode());

		CustomerLoyaltyTierEntity customerLoyaltyTier1 = makeCustomerLoyaltyTier();
		customerLoyaltyTier1.setCsLoyaltyCode(customerLoyalty.getCsLoyaltyCode());
	    customerLoyaltyTier1.setCsLoyaltyRef(customerLoyalty);
		customerLoyaltyTier1.setProgramTierCode(customerLoyalty.getProgramCode());
		customerLoyaltyTier1.setProgramTierRef(pt1);
		customerLoyaltyTier1.setProgramTierCode(loyaltyProgram.getProgramCode());

		customerService.add(customerEntity);
		loyaltyProgramService.add(loyaltyProgram);
		programTierService.add(pt1);
		customerLoyaltyTierService.add(customerLoyaltyTier1);
		customerLoyaltyTxnService.add(customerLoyaltyTxn1);
		
		
		Set<CustomerLoyaltyTxnEntity> customerLoyaltyTxns = new HashSet<>();
		customerLoyaltyTxns.add(customerLoyaltyTxn1);
		List<CustomerLoyaltyTierEntity> customerLoyaltyTiers = new ArrayList<>();
		customerLoyaltyTiers.add(customerLoyaltyTier1);
		
		customerLoyalty.setCsLoyaltyTxns(customerLoyaltyTxns);
		customerLoyalty.setCsLoyaltyTiers(customerLoyaltyTiers);
		customerLoyalty.setProgramRef(loyaltyProgram);
		customerLoyalty.setProgramCode(loyaltyProgram.getProgramCode());
		customerLoyalty.setCustomerRef(customerEntity);
		customerLoyalty.setCustomerCode(customerEntity.getCustomerCode());
		customerLoyaltyService.add(customerLoyalty);
	}

	public static CustomerLoyaltyEntity makeCustomerLoyalty() {
		CustomerLoyaltyEntity entity = new CustomerLoyaltyEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();
		entity.setActiveCredit(new BigDecimal(10.1));
		entity.setCsLoyaltyCode("LC_" + str);
		entity.setEndDate(new java.util.Date());
		entity.setExpiredCredit(new BigDecimal(12.1));
		entity.setRedeemedCredit(new BigDecimal(13.1));
		entity.setTotalCredit(new BigDecimal(13.1));
		RevisionControlBean revisionControl = new RevisionControlBean();
		entity.setRevisionControl(revisionControl);
		entity.setStartDate(new java.util.Date());
		entity.setStatus(ParticipationStatus.ACTIVE);
		entity.setTenantId(tid);

		return entity;
	}

	public static CustomerLoyaltyTxnEntity makeCustomerLoyaltyTxn() {
		CustomerLoyaltyTxnEntity entity = new CustomerLoyaltyTxnEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();

		entity.setCreditPoints(new BigDecimal(1.1));

		entity.setDescription("test txn entity");
		RevisionControlBean revisionControl = new RevisionControlBean();
		entity.setRevisionControl(revisionControl);

		entity.setTenantId(tid);
		entity.setTotalCreditPoints(new BigDecimal(1.1));
		entity.setTotalTxnValue(new BigDecimal(2.1));
		entity.setTxnCode("TC_" + str);
		entity.setTxnDate(new java.util.Date());
		entity.setTxnType(TxnType.MISC_CREDIT);
		entity.setTxnValue(new BigDecimal(3.1));
		entity.setValueDate(new java.util.Date());
		entity.setTierName("TN1234");

		return entity;
	}

	public static CustomerLoyaltyTierEntity makeCustomerLoyaltyTier() {
		CustomerLoyaltyTierEntity entity = new CustomerLoyaltyTierEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();

		entity.setCsLoyaltyTierCode("TC_" + str);
		entity.setCustomerCode("1234");
		entity.setEndDate(new java.util.Date());

		RevisionControlBean revisionControl = new RevisionControlBean();
		entity.setRevisionControl(revisionControl);
		entity.setStartDate(new java.util.Date());
		entity.setStatus(ParticipationStatus.ACTIVE);
		entity.setTenantId(tid);

		return entity;
	}

	public static LoyaltyProgramEntity makeLoyaltyProgram() {
		LoyaltyProgramEntity entity = new LoyaltyProgramEntity();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		String str = getRandom().toUpperCase();
		entity.setDescription("test");
		entity.setEffectiveFrom(new java.util.Date());
		entity.setEffectiveUpto(new java.util.Date());
		entity.setLifecycleStatus(LifecycleStatus.ACTIVE);
		entity.setProgramCode("LP_" + str);
		entity.setProgramName("Name");
		entity.setProgramScope(Scope.LOCAL);
		RevisionControlBean revisionControl = new RevisionControlBean();
		entity.setRevisionControl(revisionControl);
		entity.setSpendConvAlgo(SpendConversionAlgo.EXTERNAL_PROCESS);
		entity.setTierMoveType(TierMoveType.AUTOMATIC);
		entity.setTierReviewFreq(TierReviewFrequency.DAILY);
		entity.setTierValidity(TierValidity.CAL_HALF_YEAR);
		entity.setTenantId(tid);

		return entity;
	}

	public static ProgramTierEntity makeProgramTier() {
		ProgramTierEntity programTierEntity = new ProgramTierEntity();
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		String str = getRandom().toUpperCase();

		programTierEntity.setDescription("program tier decription");
		programTierEntity.setEffectiveFrom(new java.util.Date());
		programTierEntity.setEffectiveUpto(new java.util.Date());
		programTierEntity.setLifecycleStatus(LifecycleStatus.ACTIVE);
		RevisionControlBean revisionControl = new RevisionControlBean();
		programTierEntity.setRevisionControl(revisionControl);
		programTierEntity.setSpendConvFormula("spendConvFormula");
		programTierEntity.setSpendConvProcess("spendConvProcess");
		programTierEntity.setSpendConvRate(1.12f);
		programTierEntity.setTenantId(tid);
		programTierEntity.setTierCode("PT_" + str);
		programTierEntity.setTierPointFrom(100);
		programTierEntity.setTierPointUpto(200);
		// programTierEntity.setTierSequence(4);
		programTierEntity.setTierName("testTier");
		return programTierEntity;

	}

}
