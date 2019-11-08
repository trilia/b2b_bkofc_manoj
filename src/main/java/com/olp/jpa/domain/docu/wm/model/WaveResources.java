/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.Employee;
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceRole;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceType;

@XmlRootElement(name = "waveresources", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "warehouseCode", "batchNumber", "employeeCode",
		"resourceType", "resourceRole", "revisionControl" })
public class WaveResources implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "waveResourceId")
	private Long id;

	@XmlElement
	private String tenantId;

	@XmlElement
	private String warehouseCode;

	@XmlElement
	private String batchNumber;

	@XmlElement
	private String employeeCode;
	
	@XmlElement
	private Employee employeeRef;

	@XmlElement
	private WaveResourceType resourceType;

	@XmlElement
	private WaveResourceRole resourceRole;

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
	 * @return the resourceType
	 */
	public WaveResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            the resourceType to set
	 */
	public void setResourceType(WaveResourceType resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the resourceRole
	 */
	public WaveResourceRole getResourceRole() {
		return resourceRole;
	}

	/**
	 * @param resourceRole
	 *            the resourceRole to set
	 */
	public void setResourceRole(WaveResourceRole resourceRole) {
		this.resourceRole = resourceRole;
	}

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	/**
	 * @return the employeeRef
	 */
	public Employee getEmployeeRef() {
		return employeeRef;
	}

	/**
	 * @param employeeRef the employeeRef to set
	 */
	public void setEmployeeRef(Employee employeeRef) {
		this.employeeRef = employeeRef;
	}

	/**
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public WaveResourcesEntity convertTo(int mode) {
		WaveResourcesEntity bean = new WaveResourcesEntity();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);
		bean.setBatchNumber(batchNumber);
		bean.setWarehouseCode(warehouseCode);

		bean.setEmployeeCode(employeeCode);
		EmployeeEntity employeeEntity = employeeRef.convertTo(mode);	
		bean.setEmployeeRef(employeeEntity);
		bean.setResourceRole(resourceRole);
		bean.setResourceType(resourceType);
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
