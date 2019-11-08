package com.olp.jpa.domain.docu.wm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.jpa.domain.docu.org.CommonOrg;
import com.olp.jpa.domain.docu.org.model.DepartmentEntity;
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.org.model.OrganizationEntity;
import com.olp.jpa.domain.docu.org.repo.EmployeeService;
import com.olp.jpa.domain.docu.org.repo.OrganizationService;
import com.olp.jpa.domain.docu.wm.model.LocatorTypeEntity;
import com.olp.jpa.domain.docu.wm.model.Warehouse;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseLocatorEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import com.olp.jpa.domain.docu.wm.model.WaveBatch;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;
import com.olp.jpa.domain.docu.wm.model.WaveResources;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;
import com.olp.jpa.domain.docu.wm.model.WaveTasks;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.ZoneType;
import com.olp.jpa.domain.docu.wm.repo.LocatorTypeService;
import com.olp.jpa.domain.docu.wm.repo.WarehouseService;
import com.olp.jpa.domain.docu.wm.repo.WaveBatchService;
import com.olp.jpa.domain.docu.wm.repo.WaveResourcesService;
import com.olp.jpa.domain.docu.wm.repo.WaveTasksService;

public class WaveBatchServiceTest extends BaseSpringAwareTest {

	@Autowired
	@Qualifier("waveResourcesService")
	private WaveResourcesService waveResourcesService;

	@Autowired
	@Qualifier("waveTasksService")
	private WaveTasksService waveTasksService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService orgSvc;

	@Autowired
	@Qualifier("warehouseService")
	private WarehouseService whSvc;

	@Autowired
	@Qualifier("locatorTypeService")
	private LocatorTypeService locTypeSvc;

	@Autowired
	@Qualifier("waveBatchService")
	private WaveBatchService waveBatchSvc;

	@Autowired
	@Qualifier("employeeService")
	private EmployeeService empSvc;

	@Before
	public void before() {
		waveTasksService.deleteAll(false);
		waveBatchSvc.deleteAll(false);
		waveResourcesService.deleteAll(false);
		locTypeSvc.deleteAll(false);
		orgSvc.deleteAll(false);
		empSvc.deleteAll(false);
		setup();

	}

	@After
	public void after() {
		/*
		 * waveBatchSvc.deleteAll(false); waveTasksService.deleteAll(false);
		 * waveResourcesService.deleteAll(false); empSvc.deleteAll(false);
		 */
	}

	@Test
	public void testWaveBatch() {
		List<WaveBatchEntity> waveBatches = waveBatchSvc.findAll();
		assertNotNull("wavebatch list cannot be null", waveBatches);
	}

	@Test
	public void updateWaveBatch() {
		List<WaveBatchEntity> waveBatches = waveBatchSvc.findAll();
		WaveBatch waveBatch = waveBatches.get(0).convertTo(0);

		List<WaveTasks> waveTaks = waveBatch.getWaveTasks();
		assertEquals("waveTasks size", waveTaks.size(), 2);
		Set<WaveResources> waveResources = waveBatch.getWaveResoures();
		assertEquals("waveResources size", waveResources.size(), 2);

		List<EmployeeEntity> employees = empSvc.findAll();

		WaveResourcesEntity waveResource3 = CommonWM.makeWaveResources();
		waveResource3.setEmployeeCode("1234");
		waveResource3.setWarehouseCode(waveBatch.getWarehouseCode());
		waveResource3.setEmployeeRef(employees.get(0));

		waveResources.add(waveResource3.convertTo(0));

		WaveTasksEntity waveTasks1 = CommonWM.makeWaveTasks();
		waveTasks1.setWarehouseCode(waveBatch.getWarehouseCode());
		waveTasks1.setEmployeeCode("1234");
		waveTasks1.setAssignedTo(employees.get(0));
		waveTaks.add(waveTasks1.convertTo(0));

		WaveBatchEntity wbe = waveBatch.convertTo(0);
		waveBatchSvc.update(wbe);

		waveBatches = waveBatchSvc.findAll();
		assertEquals("waveResources size", waveBatches.get(0).getWaveResoures().size(), 3);
	}

	public void setup() {
		WaveBatchEntity waveBatch = CommonWM.makeWaveBatch();
		WarehouseEntity wh = setupWireHouseEntity();
		waveBatch.setWarehouseRef(wh);
		waveBatch.setWarehouseCode(wh.getWarehouseCode());
		EmployeeEntity emp = new EmployeeEntity();

		emp.setFirstName("John");
		emp.setLastName("Doe");

		Calendar cal = Calendar.getInstance();
		cal.set(2000, 01, 02);
		Date date = cal.getTime();
		emp.setDateOfJoining(date);
		// bean.setManagerId(-1L);

		DepartmentEntity dept = new DepartmentEntity();
		dept.setId(30L);
		emp.setEmployeeCode("123");
		emp.setSalutation("testSalutation");
		emp.setGender("M");
		emp.setDateOfBirth(new Date());
		emp.setIdentificationType("identificationType");
		emp.setIdentificationNumber("123");
		empSvc.add(emp);

		// set the child relationships
		WaveResourcesEntity waveResource1 = CommonWM.makeWaveResources();
		waveResource1.setEmployeeCode("123");
		waveResource1.setWarehouseCode(wh.getWarehouseCode());
		waveResource1.setEmployeeRef(emp);
		Set<WaveResourcesEntity> waveResourcesList = new HashSet<WaveResourcesEntity>();

		WaveResourcesEntity waveResource2 = CommonWM.makeWaveResources();
		waveResource2.setEmployeeCode("123");
		waveResource2.setWarehouseCode(wh.getWarehouseCode());
		waveResource2.setEmployeeRef(emp);
		waveResourcesList.add(waveResource1);
		waveResourcesList.add(waveResource2);

		waveBatch.setWaveResoures(waveResourcesList);

		List<WaveTasksEntity> waveTasksList = new ArrayList<WaveTasksEntity>();
		WaveTasksEntity waveTasks1 = CommonWM.makeWaveTasks();
		waveTasks1.setWarehouseCode(wh.getWarehouseCode());
		waveTasks1.setEmployeeCode("123");
		waveTasks1.setAssignedTo(emp);

		WaveTasksEntity waveTasks2 = CommonWM.makeWaveTasks();
		waveTasks2.setWarehouseCode(wh.getWarehouseCode());
		waveTasks2.setEmployeeCode("123");
		waveTasks2.setAssignedTo(emp);

		waveTasksList.add(waveTasks1);
		waveTasksList.add(waveTasks2);

		waveBatch.setWaveTasks(waveTasksList);
		waveBatchSvc.add(waveBatch);
	}

	public WarehouseEntity setupWireHouseEntity() {
		OrganizationEntity org = CommonOrg.makeOrg();
		org.setOrgType("WAREHOUSE");

		orgSvc.add(org);

		WarehouseEntity wh = CommonWM.makeWarehouse();

		LocatorTypeEntity locType = CommonWM.makeLocType();
		locTypeSvc.add(locType);

		List<WarehouseLocatorEntity> locators = new ArrayList<>();

		WarehouseLocatorEntity loc1 = CommonWM.makeWhLocator("AA1", "1000", "00010");
		loc1.setLocatorTypeRef(locType);
		loc1.setWarehouseCode(wh.getWarehouseCode());
		WarehouseLocatorEntity loc2 = CommonWM.makeWhLocator("AA2", "2000", "00020");
		loc2.setLocatorTypeRef(locType);
		loc2.setWarehouseCode(wh.getWarehouseCode());
		WarehouseLocatorEntity loc3 = CommonWM.makeWhLocator("AA2", "3000", "00030");
		loc3.setLocatorTypeRef(locType);
		loc3.setWarehouseCode(wh.getWarehouseCode());

		locators.add(loc1);
		locators.add(loc2);
		locators.add(loc3);

		List<WarehouseZoneEntity> zones = new ArrayList<>();

		WarehouseZoneEntity zone1 = CommonWM.makeWhZone();
		WarehouseZoneEntity zone2 = CommonWM.makeWhZone();
		zone1.setZoneType(ZoneType.RECEIVING_DOCK);
		zone1.setZoneSubType("DOCK1");
		zone2.setLocators(locators);

		zones.add(zone1);
		zones.add(zone2);

		wh.setOrganizationRef(org);
		wh.setZones(zones);

		whSvc.add(wh);

		return wh;
	}
}
