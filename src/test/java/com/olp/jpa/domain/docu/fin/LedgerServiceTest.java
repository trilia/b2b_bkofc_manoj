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
import com.olp.jpa.domain.docu.fin.model.AccountCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategory;
import com.olp.jpa.domain.docu.fin.model.AccountSubCategoryEntity;
import com.olp.jpa.domain.docu.fin.model.CoaAccountsEntity;
import com.olp.jpa.domain.docu.fin.model.Ledger;
import com.olp.jpa.domain.docu.fin.model.LedgerEntity;
import com.olp.jpa.domain.docu.fin.model.LedgerLine;
import com.olp.jpa.domain.docu.fin.model.LedgerLineEntity;
import com.olp.jpa.domain.docu.fin.repo.CoaAccountsService;
import com.olp.jpa.domain.docu.fin.repo.LedgerLineService;
import com.olp.jpa.domain.docu.fin.repo.LedgerService;

public class LedgerServiceTest extends BaseSpringAwareTest{

	@Autowired
	@Qualifier("ledgerService")
	private LedgerService ledgerService;
	
	@Autowired
	@Qualifier("ledgerLineService")
	private LedgerLineService ledgerLineService;
	
	@Autowired
	@Qualifier("coaAccountsService")
	private CoaAccountsService coaAccountsService;
	
	@Before
	public void before() {
		coaAccountsService.deleteAll(false);
		ledgerLineService.deleteAll(false);
		ledgerService.deleteAll(false);
		setup();

	}
	
	private void setup(){
		LedgerEntity ledger = CommonFin.makeLedger();
		LedgerLineEntity ledgerLine1 = CommonFin.makeLedgerLine();

		CoaAccountsEntity coaEntity = createCoaEntity();
		coaAccountsService.add(coaEntity);
		
		ledgerLine1.setLedgerName(ledger.getLedgerName());
		ledgerLine1.setLedgerRef(ledger);
		ledgerLine1.setAccountRef(coaEntity);
		ledgerLine1.setAccountCode(coaEntity.getAccountCode());
		
		LedgerLineEntity ledgerLine2 = CommonFin.makeLedgerLine();
		ledgerLine2.setLedgerName(ledger.getLedgerName());
		ledgerLine2.setLedgerRef(ledger);
		ledgerLine2.setAccountRef(coaEntity);
		ledgerLine2.setAccountCode(coaEntity.getAccountCode());
		
		
		List<LedgerLineEntity> ledgerLineList = new ArrayList<>();
		ledgerLineList.add(ledgerLine1);
		ledgerLineList.add(ledgerLine2);
		
		ledger.setLedgerLines(ledgerLineList);
			
		ledgerLineService.add(ledgerLine1);
		ledgerLineService.add(ledgerLine2);
		
		ledgerService.add(ledger);		
	}
	
	@Test
	public void testLedger() {
		List<LedgerEntity> ledgers = ledgerService.findAll();
		assertNotNull("ledgers list cannot be null", ledgers);
		assertEquals("ledgers size", ledgers.size(),1);
	}
	
	@Test
	public void updateLedger(){
		List<LedgerEntity> ledgers = ledgerService.findAll();
		
		Ledger ledger = ledgers.get(0).convertTo(0);
		List<LedgerLine> ledgerLines = ledger.getLedgerLines();
 		assertEquals("LedgerLine size",ledgerLines.size(),2);
 				
 		LedgerLineEntity ledgerLineEntity =  CommonFin.makeLedgerLine();
		
 		ledgerLineEntity.setLedgerName(ledger.getLedgerName());
 		ledgerLineEntity.setLedgerRef(ledgers.get(0));
		
		CoaAccountsEntity coa = coaAccountsService.findbyAccountCatg("AC_1", "ASC_1");
		ledgerLineEntity.setAccountRef(coa);
		ledgerLineEntity.setAccountCode(coa.getAccountCode());
		
		LedgerLine ledgerLine = ledgerLineEntity.convertTo(0);
		ledgerLines.add(ledgerLine);
		
		LedgerEntity le = ledger.convertTo(0);
		le.getLedgerLines().get(2).setAccountRef(coa);
		ledgerService.update(le);
		
		ledgers = ledgerService.findAll();
 		assertEquals("LedgerLine size",ledgers.get(0).getLedgerLines().size(),3);
	}
	
	private CoaAccountsEntity createCoaEntity(){
		CoaAccountsEntity coaEntity = new CoaAccountsEntity();
		coaEntity.setAccountCode("CO_"+getRandom().toUpperCase());
		coaEntity.setAccountName("TestA");
		coaEntity.setCoaCode("CO_"+12);
		coaEntity.setAccountCatgCode("AC_1");
		coaEntity.setAccountSubCatgCode("ASC_1");
		coaEntity.setParentAccountCode("PAC_1");
		coaEntity.setAccountCatgCode("AC_1");
		coaEntity.setSegment1Value("SG1");
		coaEntity.setSegment1LovCode("SG1LC");
		coaEntity.setSegment2Value("SG2");
		coaEntity.setSegment2LovCode("SG2LC");
		coaEntity.setSegment3Value("SG3");
		coaEntity.setSegment3LovCode("SG3LC");
		coaEntity.setSegment4Value("SG4");
		coaEntity.setSegment4LovCode("SG4LC");
		coaEntity.setSegment5Value("SG5");
		coaEntity.setSegment5LovCode("SG5LC");
		
		return coaEntity;
	}
}
