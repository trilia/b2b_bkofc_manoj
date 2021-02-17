package com.olp.jpa.domain.docu.llty.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.Scope;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.SpendConversionAlgo;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierMoveType;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierReviewFrequency;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierValidity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgram;
import com.olp.jpa.domain.docu.llty.model.LoyaltyProgramEntity;
import com.olp.jpa.domain.docu.llty.model.ProgramTier;
import com.olp.jpa.domain.docu.llty.model.ProgramTierEntity;

public class LoyaltyProgramServiceImplTest extends BaseSpringAwareTest{
	
	@Autowired
	@Qualifier("programTierService")
	private ProgramTierService programTierService;
	
	@Autowired
	@Qualifier("loyaltyProgramService")
	private LoyaltyProgramService loyaltyProgramService;
	
	@Before
	public void before() {
		//loyaltyProgramService.deleteAll(false);
		//programTierService.deleteAll(false);
		setup();
	}
	
	@Test
	public void testLoyaltyProgram() {
		List<LoyaltyProgramEntity> loyaltyPrograms = loyaltyProgramService.findAll();
		assertNotNull("loyaltyPrograms list cannot be null", loyaltyPrograms);
		
	}

	@Test
	public void updateLoyaltyProgram(){
		List<LoyaltyProgramEntity> loyaltyPrograms = loyaltyProgramService.findAll();
		LoyaltyProgram loyaltyProgram = loyaltyPrograms.get(0).convertTo(0);
		
		List<ProgramTier> pt = loyaltyProgram.getProgramTiers();
		assertEquals("ProgramTier size", pt.size(), 1);
		ProgramTierEntity pt2 = makeProgramTier();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DATE, 10);
		
		pt2.setEffectiveFrom(cal.getTime());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.set(Calendar.DATE, 15);
		pt2.setEffectiveUpto(cal1.getTime());
		pt2.setProgramCode(loyaltyProgram.getProgramCode());
		pt2.setProgramRef(loyaltyPrograms.get(0));
		
		ProgramTier ptier = pt2.convertTo(0);
		pt.add(ptier);
		
		LoyaltyProgramEntity lpe = loyaltyProgram.convertTo(0);
		loyaltyProgramService.update(lpe);

		loyaltyPrograms= loyaltyProgramService.findAll();
		assertEquals("ProgramTier size", loyaltyPrograms.get(0).getProgramTiers().size(), 2);
	}
	
	public void setup() {
		LoyaltyProgramEntity loyaltyProgram = makeLoyaltyProgram();
		
		ProgramTierEntity pt1 = makeProgramTier();
		pt1.setProgramCode(loyaltyProgram.getProgramCode());
		pt1.setProgramRef(loyaltyProgram);
		
		List<ProgramTierEntity> programTiers = new ArrayList<>();
		programTiers.add(pt1);
		
		programTierService.add(pt1);
		loyaltyProgram.setProgramTiers(programTiers);
		
		loyaltyProgramService.add(loyaltyProgram);
	}
	
	public static LoyaltyProgramEntity makeLoyaltyProgram(){
		LoyaltyProgramEntity entity = new LoyaltyProgramEntity();
    	
		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		String str = getRandom().toUpperCase();
		entity.setDescription("test");
		entity.setEffectiveFrom(new java.util.Date());
		entity.setEffectiveUpto(new java.util.Date());
		entity.setLifecycleStatus(LifecycleStatus.ACTIVE);
		entity.setProgramCode("LP_"+str);
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
	
	public static ProgramTierEntity makeProgramTier(){
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
		programTierEntity.setTierCode("PT_"+str);
		programTierEntity.setTierPointFrom(100);
		programTierEntity.setTierPointUpto(200);
		//programTierEntity.setTierSequence(4);
		programTierEntity.setTierName("testTier");
		return programTierEntity;
		
	}

}
