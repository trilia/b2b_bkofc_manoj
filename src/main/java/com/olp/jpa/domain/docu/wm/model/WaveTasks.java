/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasure;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.org.model.Employee;
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveTaskStatus;

@XmlRootElement(name = "wavebatch", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "warehouseCode", "batchNumber", "matPipeCode", "serials", "quantity",
		"quantityUomRef", "uomCode", "palletNum", "assignedTo", "employeeCode", "estimatedStartDate", "actualStartDate",
		"estimatedFinishDate", "actualFinishDate", "taskStatus", "revisionControl" })
public class WaveTasks implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "waveTaskId")
	private Long id;

	@XmlElement
	private String tenantId;

	@XmlElement
	private String warehouseCode;

	@XmlElement
	private String batchNumber;

	@XmlElement
	private String matPipeCode;

	@XmlElement
	private Set<SerialInfoBean> serials;

	@XmlElement
	private BigDecimal quantity;

	@XmlElement
	private UnitOfMeasure quantityUomRef;

	@XmlElement
	private String uomCode;

	@XmlElement
	private String palletNum;

	@XmlElement
	private Employee assignedTo;

	@XmlElement
	private String employeeCode;

	@XmlElement
	private Date estimatedStartDate;

	@XmlElement
	private Date actualStartDate;

	@XmlElement
	private Date estimatedFinishDate;

	@XmlElement
	private Date actualFinishDate;

	@XmlElement
	private WaveTaskStatus taskStatus;

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
	 * @return the matPipeCode
	 */
	public String getMatPipeCode() {
		return matPipeCode;
	}

	/**
	 * @param matPipeCode
	 *            the matPipeCode to set
	 */
	public void setMatPipeCode(String matPipeCode) {
		this.matPipeCode = matPipeCode;
	}

	/**
	 * @return the serials
	 */
	public Set<SerialInfoBean> getSerials() {
		return serials;
	}

	/**
	 * @param serials
	 *            the serials to set
	 */
	public void setSerials(Set<SerialInfoBean> serials) {
		this.serials = serials;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantityUomRef
	 */
	public UnitOfMeasure getQuantityUomRef() {
		return quantityUomRef;
	}

	/**
	 * @param quantityUomRef
	 *            the quantityUomRef to set
	 */
	public void setQuantityUomRef(UnitOfMeasure quantityUomRef) {
		this.quantityUomRef = quantityUomRef;
	}

	/**
	 * @return the uomCode
	 */
	public String getUomCode() {
		return uomCode;
	}

	/**
	 * @param uomCode
	 *            the uomCode to set
	 */
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	/**
	 * @return the palletNum
	 */
	public String getPalletNum() {
		return palletNum;
	}

	/**
	 * @param palletNum
	 *            the palletNum to set
	 */
	public void setPalletNum(String palletNum) {
		this.palletNum = palletNum;
	}

	/**
	 * @return the assignedTo
	 */
	public Employee getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo
	 *            the assignedTo to set
	 */
	public void setAssignedTo(Employee assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * @param employeeCode
	 *            the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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
	 * @return the taskStatus
	 */
	public WaveTaskStatus getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus
	 *            the taskStatus to set
	 */
	public void setTaskStatus(WaveTaskStatus taskStatus) {
		this.taskStatus = taskStatus;
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

	public WaveTasksEntity convertTo(int mode) {
		WaveTasksEntity bean = new WaveTasksEntity();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);
		bean.setActualFinishDate(actualFinishDate);
		bean.setActualStartDate(actualStartDate);
		EmployeeEntity employeeEntity = assignedTo.convertTo(mode);
		bean.setAssignedTo(employeeEntity);
		bean.setBatchNumber(batchNumber);
		bean.setEmployeeCode(employeeCode);
		bean.setEstimatedFinishDate(estimatedFinishDate);
		bean.setEstimatedStartDate(estimatedStartDate);
		bean.setMatPipeCode(matPipeCode);
		bean.setPalletNum(palletNum);
		bean.setQuantity(quantity);
		if (quantityUomRef != null) {
			UnitOfMeasureEntity quantityUom = quantityUomRef.convertTo(mode);
			bean.setQuantityUomRef(quantityUom);
		}
		bean.setRevisionControl(revisionControl);
		bean.setSerials(serials);
		bean.setTaskStatus(taskStatus);
		bean.setUomCode(uomCode);
		bean.setWarehouseCode(warehouseCode);

		return bean;
	}
}
