/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm;

import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.Constants;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.domain.docu.be.model.ManufacturerEntity;
import com.olp.jpa.domain.docu.inv.model.InvEnums.SubInventory;
import com.olp.jpa.domain.docu.org.CommonOrg;
import com.olp.jpa.domain.docu.org.model.LocationEntity;
import com.olp.jpa.domain.docu.po.model.PoEnums;
import com.olp.jpa.domain.docu.po.model.PurchaseOrderEntity;
import com.olp.jpa.domain.docu.wm.model.BatchControlEntity;
import com.olp.jpa.domain.docu.wm.model.BatchInfoBean;
import com.olp.jpa.domain.docu.wm.model.InwardShipmentEntity;
import com.olp.jpa.domain.docu.wm.model.InwardShipmentLineEntity;
import com.olp.jpa.domain.docu.wm.model.LocatorTypeEntity;
import com.olp.jpa.domain.docu.wm.model.SerialControlEntity;
import com.olp.jpa.domain.docu.wm.model.SerialInfoBean;
import com.olp.jpa.domain.docu.wm.model.StorageInfoBean;
import com.olp.jpa.domain.docu.wm.model.WarehouseEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseLocatorEntity;
import com.olp.jpa.domain.docu.wm.model.WarehouseZoneEntity;
import com.olp.jpa.domain.docu.wm.model.WaveBatchEntity;
import com.olp.jpa.domain.docu.wm.model.WaveResourcesEntity;
import com.olp.jpa.domain.docu.wm.model.WaveTasksEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceRole;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceType;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveTaskStatus;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveType;
import com.olp.jpa.domain.docu.wm.model.WmEnums.ZoneType;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author raghosh
 */
public class CommonWM extends BaseSpringAwareTest {

	public static WarehouseEntity makeWarehouse() {

		WarehouseEntity wh = new WarehouseEntity();

		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		wh.setWarehouseCode("WH_" + str);
		wh.setWarehouseName("Warehouse " + str);
		wh.setWmControlEnabled(true);

		LocationEntity le = CommonOrg.makeLocation();
		le.setLocationAlias("WH_" + str + "_LOC");

		wh.setWhLocation(le);

		return (wh);
	}

	public static WarehouseZoneEntity makeWhZone() {

		WarehouseZoneEntity zone = new WarehouseZoneEntity();

		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		zone.setZoneCode("ZONE_" + str);
		zone.setZoneName("Zone " + str);
		zone.setZoneType(ZoneType.STORAGE);
		zone.setZoneSubType("Bulk");
		zone.setAllowDynamicLocator(true);
		zone.setIslocatorEnabled(true);
		zone.setSubInventory(SubInventory.STORAGE);

		return (zone);
	}

	public static LocatorTypeEntity makeLocType() {

		String str = getRandom().toUpperCase();
		LocatorTypeEntity locType = new LocatorTypeEntity();
		locType.setLocatorTypeCode("LOC_TYPE_" + str);
		locType.setLocatorTypeDesc("Locator Type " + str);

		locType.setDimensionUom("Inch");
		locType.setLocatorCubicVolume(182.86F);
		locType.setWeightUom("Kg");

		return (locType);
	}

	public static WarehouseLocatorEntity makeWhLocator(String row, String rack, String bin) {

		WarehouseLocatorEntity locator = new WarehouseLocatorEntity();

		StorageInfoBean store = new StorageInfoBean();

		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		store.setRowValue(row);
		store.setRackValue(rack);
		store.setBinValue(bin);

		locator.setAllowLoadIfFull(false);
		locator.setAllowMultipleObject(true);
		locator.setIsEnabled(Boolean.TRUE);
		locator.setLocLoadType(WmEnums.LocatorLoadType.BOTH_PALETTE_N_MATERIAL);
		// locator.setLocUtilization(WmEnums.LocatorUtilization.EMPTY);
		locator.setStorageInfo(store);

		return (locator);

	}

	public static InwardShipmentLineEntity makeShipmentLine(int lineNum) {

		InwardShipmentLineEntity line = new InwardShipmentLineEntity();

		// line.setAsnLine(lineNum);
		line.setExpectedQty(BigDecimal.valueOf(100.10));
		line.setLineNumber(lineNum);
		line.setManufacturer("ABC Ltd");
		line.setModelNumber("XYZ123");
		line.setProductDesc("Sample item");
		line.setExpectedQty(BigDecimal.valueOf(92.47));
		line.setReturnedQty(BigDecimal.valueOf(0));
		line.setShippedQty(BigDecimal.valueOf(92.47));
		line.setBaseUnitCost(BigDecimal.valueOf(123.75));
		line.setShippingUom("Kg");

		return (line);
	}

	public static InwardShipmentEntity makeShipmentExtAsn() {

		InwardShipmentEntity ship = new InwardShipmentEntity();

		String str = getRandom().toUpperCase();

		ship.setShipmentNumber(str);
		ship.setShipmentPart(0);

		Calendar cal1 = Calendar.getInstance();
		cal1.set(2018, 6, 10);
		// ship.setAsnDate(cal1.getTime());
		// ship.setAsnRef("Sample ASN ref");
		ship.setAwbNumber("xyz123def456");
		ship.setExternalAsnRef("Sample ASN ref");
		ship.setLogisticAgent("Blue Dart");
		ship.setReceivedDate(cal1.getTime());
		ship.setShipmentBasis(WmEnums.ShipmentBasis.EXT_ASN);
		ship.setFrtDistr(PoEnums.FreightDistrType.NO_FREIGHT);

		return (ship);
	}

	public static InwardShipmentEntity makeShipmentInvoice() {

		InwardShipmentEntity ship = new InwardShipmentEntity();

		String str = getRandom().toUpperCase();

		ship.setShipmentNumber(str);
		ship.setShipmentPart(0);

		Calendar cal1 = Calendar.getInstance();
		cal1.set(2018, 6, 10);
		// ship.setAsnDate(cal1.getTime());
		// ship.setAsnRef("Sample ASN ref");
		ship.setAwbNumber("xyz123def456");
		ship.setSupplierInvoiceNum("DSDAKA_1232");
		ship.setSupplierInvoiceDate(cal1.getTime());
		ship.setLogisticAgent("Blue Dart");
		ship.setReceivedDate(cal1.getTime());
		ship.setShipmentBasis(WmEnums.ShipmentBasis.INVOICE);
		ship.setFrtDistr(PoEnums.FreightDistrType.NO_FREIGHT);

		return (ship);
	}

	public static InwardShipmentEntity makeShipmentPo(String poNum) {

		InwardShipmentEntity ship = new InwardShipmentEntity();

		String str = getRandom().toUpperCase();

		ship.setShipmentNumber(str);
		ship.setShipmentPart(0);

		Calendar cal1 = Calendar.getInstance();
		cal1.set(2018, 6, 10);
		// ship.setAsnDate(cal1.getTime());
		// ship.setAsnRef("Sample ASN ref");
		ship.setAwbNumber("xyz123def456");
		ship.setLogisticAgent("Blue Dart");
		ship.setReceivedDate(cal1.getTime());

		ship.setShipmentBasis(WmEnums.ShipmentBasis.PO);
		PurchaseOrderEntity po = new PurchaseOrderEntity();
		po.setPoNumber(poNum);
		ship.setPoRef(po);

		return (ship);
	}

	public static BatchInfoBean makeBatchInfo(String mfgCode) {

		String str = getRandom().toUpperCase();

		BatchInfoBean bean = new BatchInfoBean();
		bean.setBatchNumber("BATCH_" + str);
		bean.setExpiryDate(new Date(119, 12, 20));
		bean.setManufactureDate(new Date(118, 6, 15));
		bean.setMfgCode(mfgCode);

		return (bean);
	}

	public static BatchControlEntity makeBatch(String mfgCode) {

		BatchControlEntity entity = new BatchControlEntity();
		BatchInfoBean bean = makeBatchInfo(mfgCode);
		entity.setBatchInfo(bean);
		entity.setConsumed(false);
		ManufacturerEntity mfg = new ManufacturerEntity();
		mfg.setManufacturerCode(mfgCode);
		entity.setManufacturerRef(mfg);

		return (entity);
	}

	public static SerialInfoBean makeSerialInfo(String mfgCode) {

		String str = getRandom().toUpperCase();

		SerialInfoBean bean = new SerialInfoBean();
		bean.setManufactureDate(new Date(118, 6, 15));
		bean.setMfgCode(mfgCode);
		bean.setSerialNumber("SERIAL_" + str);
		bean.setLifeCycleStatus(WmEnums.MaterialLifeCycleStage.RECEIVED);

		return (bean);
	}

	public static SerialControlEntity makeSerialControl(String mfgCode) {

		SerialControlEntity entity = new SerialControlEntity();
		entity.setConsumed(false);
		ManufacturerEntity mfg = new ManufacturerEntity();
		mfg.setManufacturerCode(mfgCode);
		entity.setManufacturerRef(mfg);
		SerialInfoBean bean = makeSerialInfo(mfgCode);
		entity.setSerialInfo(bean);

		return (entity);
	}

	public static WaveBatchEntity makeWaveBatch() {
		WaveBatchEntity waveBatch = new WaveBatchEntity();

		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();

		waveBatch.setBatchNumber("BT_" + str);
		waveBatch.setWarehouseCode(waveBatch.getWarehouseCode());
		waveBatch.setWaveType(WaveType.INSPECTION);
		waveBatch.setWorkshiftCode("1234");

		waveBatch.setActualFinishDate(new Date());
		waveBatch.setActualStartDate(new Date());
		waveBatch.setEstimatedFinishDate(new Date());
		waveBatch.setEstimatedStartDate(new Date());

		return (waveBatch);
	}

	public static WaveResourcesEntity makeWaveResources(){
		WaveResourcesEntity waveResources = new WaveResourcesEntity();
		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		waveResources.setResourceRole(WaveResourceRole.FIELDSTAFF);
		waveResources.setResourceType(WaveResourceType.EMPLOYEE);
		waveResources.setBatchNumber("BT_" + str);
		
		return waveResources;
	}

	public static WaveTasksEntity makeWaveTasks(){
		WaveTasksEntity waveTasks = new WaveTasksEntity();
		String str = getRandom().toUpperCase();

		IContext ctx = ContextManager.getContext();
		String tid = ctx.getTenantId();
		
		waveTasks.setBatchNumber("BT_" + str);
		waveTasks.setActualFinishDate(new Date());
		waveTasks.setActualStartDate(new Date());
		waveTasks.setTaskStatus(WaveTaskStatus.IN_PROGRESS);
		waveTasks.setEstimatedFinishDate(new Date());
		waveTasks.setEstimatedStartDate(new Date());
		waveTasks.setMatPipeCode("123");
		waveTasks.setUomCode("123Uom");
		waveTasks.setPalletNum("12");
		waveTasks.setQuantity(new BigDecimal("12.23"));
		
		return waveTasks;
	}
	
}
