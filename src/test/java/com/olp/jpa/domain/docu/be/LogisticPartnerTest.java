package com.olp.jpa.domain.docu.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.be.model.LogisticPartner;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLoc;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerLocEntity;
import com.olp.jpa.domain.docu.be.repo.LogisticPartnerLocService;
import com.olp.jpa.domain.docu.be.repo.LogisticPartnerService;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;

public class LogisticPartnerTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("logisticPartnerService")
	private LogisticPartnerService logisticPartnerService;

	@Autowired
	@Qualifier("logisticPartnerLocService")
	private LogisticPartnerLocService logisticPartnerLocService;

	@Before
	public void before() {
		logisticPartnerService.deleteAll(false);
		logisticPartnerLocService.deleteAll(false);
		setup();
	}

	public void setup() {
		LogisticPartnerEntity logisticPartner = CommonBE.makeLogisticPartner();

		LogisticPartnerLocEntity LogisticPartnerLocOne = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocOne.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocOne.setPartnerRef(logisticPartner);

		LogisticPartnerLocEntity LogisticPartnerLocTwo = CommonBE.makeLogisticPartnerLoc();
		LogisticPartnerLocTwo.setPartnerCode(logisticPartner.getPartnerCode());
		LogisticPartnerLocTwo.setPartnerRef(logisticPartner);

		List<LogisticPartnerLocEntity> logPartnerLocs = new ArrayList<>();
		logPartnerLocs.add(LogisticPartnerLocOne);
		logPartnerLocs.add(LogisticPartnerLocTwo);

		logisticPartnerLocService.add(LogisticPartnerLocOne);
		logisticPartnerLocService.add(LogisticPartnerLocTwo);

		logisticPartner.setPartnerLocations(logPartnerLocs);

		logisticPartnerService.add(logisticPartner);
	}

	@Test
	public void testLogisticPartner() {
		List<LogisticPartnerEntity> logPartner = logisticPartnerService.findAll();
		assertNotNull("logisticpartner list cannot be null", logPartner);
	}
	
	@Test
	public void updateLogisticPartner(){
		List<LogisticPartnerEntity> logisticPartners = logisticPartnerService.findAll();
		LogisticPartner logisticsPartner = logisticPartners.get(0).convertTo(0);
		
		List<LogisticPartnerLoc> logistcisPartnersLocs = logisticsPartner.getPartnerLocations();
		
		//List<LogisticPartnerLocEntity> logisticPartnerLocs = logisticsPartner.getPartnerLocations();
		assertEquals("logisticPartnerLocs size", logistcisPartnersLocs.size(), 2);
		
		/*LogisticPartnerLocEntity logisticsPartnerLoc3 = CommonBE.makeLogisticPartnerLoc();
		logisticsPartnerLoc3.setPartnerCode(logisticsPartner.getPartnerCode());
		logisticsPartnerLoc3.setPartnerRef(logisticPartners.get(0));
		
		logistcisPartnersLocs.add(logisticsPartnerLoc3.convertTo(0));
		
		LogisticPartnerEntity lpe = logisticsPartner.convertTo(0);*/
		logistcisPartnersLocs.remove(1);
		LogisticPartnerEntity lpe = logisticsPartner.convertTo(0);
		logisticPartnerService.update(lpe);

		logisticPartners= logisticPartnerService.findAll();
		assertEquals("logisticPartnerLocs size", logisticPartners.get(0).getPartnerLocations().size(), 1);
	}
}
