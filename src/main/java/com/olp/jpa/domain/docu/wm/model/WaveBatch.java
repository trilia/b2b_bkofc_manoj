/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveType;

@XmlRootElement(name = "wavebatch", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "batchNumber", "warehouseRef", "warehouseCode", "waveType", "workshiftCode",
		"estimatedStartDate", "actualStartDate", "estimatedFinishDate", "actualFinishDate", "lifecycleStatus",
		"waveResoures", "waveTasks", "revisionControl" })
public class WaveBatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "waveBatchId")
	private Long id;

	@XmlElement
	private String tenantId;

	@XmlElement
	private String batchNumber;

	@XmlElement
	private Warehouse warehouseRef;

	@XmlElement
	private String warehouseCode;

	@XmlElement
	private WaveType waveType;

	@XmlElement
	private String workshiftCode;

	@XmlElement
	private Date estimatedStartDate;

	@XmlElement
	private Date actualStartDate;

	@XmlElement
	private Date estimatedFinishDate;

	@XmlElement
	private Date actualFinishDate;

	@XmlElement
	private LifeCycleStatus lifecycleStatus;

	@XmlElement
	private Set<WaveResources> waveResoures;

	@XmlElement
	private List<WaveTasks> waveTasks;

	@XmlElement
	private RevisionControlBean revisionControl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 *            the batchNumber to set
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return the warehouseRef
	 */
	public Warehouse getWarehouseRef() {
		return warehouseRef;
	}

	/**
	 * @param warehouseRef
	 *            the warehouseRef to set
	 */
	public void setWarehouseRef(Warehouse warehouseRef) {
		this.warehouseRef = warehouseRef;
	}

	/**
	 * @return the warehouseCode
	 */
	public String getWarehouseCode() {
		return warehouseCode;
	}

	/**
	 * @param warehouseCode
	 *            the warehouseCode to set
	 */
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	/**
	 * @return the waveType
	 */
	public WaveType getWaveType() {
		return waveType;
	}

	/**
	 * @param waveType
	 *            the waveType to set
	 */
	public void setWaveType(WaveType waveType) {
		this.waveType = waveType;
	}

	/**
	 * @return the workshiftCode
	 */
	public String getWorkshiftCode() {
		return workshiftCode;
	}

	/**
	 * @param workshiftCode
	 *            the workshiftCode to set
	 */
	public void setWorkshiftCode(String workshiftCode) {
		this.workshiftCode = workshiftCode;
	}

	/**
	 * @return the estimatedStartDate
	 */
	public Date getEstimatedStartDate() {
		return estimatedStartDate;
	}

	/**
	 * @param estimatedStartDate
	 *            the estimatedStartDate to set
	 */
	public void setEstimatedStartDate(Date estimatedStartDate) {
		this.estimatedStartDate = estimatedStartDate;
	}

	/**
	 * @return the actualStartDate
	 */
	public Date getActualStartDate() {
		return actualStartDate;
	}

	/**
	 * @param actualStartDate
	 *            the actualStartDate to set
	 */
	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	/**
	 * @return the estimatedFinishDate
	 */
	public Date getEstimatedFinishDate() {
		return estimatedFinishDate;
	}

	/**
	 * @param estimatedFinishDate
	 *            the estimatedFinishDate to set
	 */
	public void setEstimatedFinishDate(Date estimatedFinishDate) {
		this.estimatedFinishDate = estimatedFinishDate;
	}

	/**
	 * @return the actualFinishDate
	 */
	public Date getActualFinishDate() {
		return actualFinishDate;
	}

	/**
	 * @param actualFinishDate
	 *            the actualFinishDate to set
	 */
	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

	/**
	 * @return the lifecycleStatus
	 */
	public LifeCycleStatus getLifecycleStatus() {
		return lifecycleStatus;
	}

	/**
	 * @param lifecycleStatus
	 *            the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifeCycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
	}

	/**
	 * @return the waveResoures
	 */
	public Set<WaveResources> getWaveResoures() {
		return waveResoures;
	}

	/**
	 * @param waveResoures
	 *            the waveResoures to set
	 */
	public void setWaveResoures(Set<WaveResources> waveResoures) {
		this.waveResoures = waveResoures;
	}

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	/**
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	/**
	 * @return the waveTasks
	 */
	public List<WaveTasks> getWaveTasks() {
		return waveTasks;
	}

	/**
	 * @param waveTasks
	 *            the waveTasks to set
	 */
	public void setWaveTasks(List<WaveTasks> waveTasks) {
		this.waveTasks = waveTasks;
	}

	public WaveBatchEntity convertTo(int mode) {
		WaveBatchEntity bean = new WaveBatchEntity();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);

		bean.setBatchNumber(batchNumber);
		bean.setWarehouseCode(warehouseCode);

		WarehouseEntity warehouseEntity = warehouseRef.convertTo(mode);
		bean.setWarehouseRef(warehouseEntity);// need to check

		bean.setWaveType(waveType);
		bean.setWorkshiftCode(workshiftCode);
		bean.setActualFinishDate(actualFinishDate);
		bean.setActualStartDate(actualStartDate);
		bean.setEstimatedFinishDate(estimatedFinishDate);
		bean.setEstimatedStartDate(estimatedStartDate);

		Set<WaveResourcesEntity> waveResourcesEntitySet = new HashSet<>();
		for (WaveResources waveResource : this.waveResoures) {
			WaveResourcesEntity waveResourcesEntity = waveResource.convertTo(mode);
			waveResourcesEntitySet.add(waveResourcesEntity);
		}
		bean.setWaveResoures(waveResourcesEntitySet);

		List<WaveTasksEntity> waveTasksEntityList = new ArrayList<>();
		for (WaveTasks waveTasks : this.waveTasks) {
			WaveTasksEntity waveTasksEntity = waveTasks.convertTo(mode);
			waveTasksEntityList.add(waveTasksEntity);
		}
		bean.setWaveTasks(waveTasksEntityList);

		bean.setRevisionControl(revisionControl);

		return (bean);
	}
}
