/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.wm.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.Employee;
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceRole;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveResourceType;

@Entity
@Table(name = "trl_wh_wave_resources", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"batch_number" }))
/*@NamedQueries({
		@NamedQuery(name = "WaveResourcesEntity.findByEmp", query = "SELECT t FROM WaveResourcesEntity t JOIN FETCH t.waveBatchRef WHERE t.warehouseCode = :whCode and "
				+ "t.employeeCode = :empCode and t.tenantId = :tenant ") })*/
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class WaveResourcesEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wave_resource_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "warehouse_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String warehouseCode;

	@Column(name = "batch_number", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String batchNumber;

	@ManyToOne
	@JoinColumn(name = "wave_batch_ref")
	@ContainedIn
	private WaveBatchEntity waveBatchRef;

	@Column(name = "employee_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String employeeCode;

	@ManyToOne
	@JoinColumn(name = "employee_ref")
	@ContainedIn
	private EmployeeEntity employeeRef;

	@Enumerated(EnumType.STRING)
	@Column(name = "resource_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private WaveResourceType resourceType;

	@Enumerated(EnumType.STRING)
	@Column(name = "resource_role", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private WaveResourceRole resourceRole;

	@IndexedEmbedded
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
	 * @return the waveBatchRef
	 */
	public WaveBatchEntity getWaveBatchRef() {
		return waveBatchRef;
	}

	/**
	 * @param waveBatchRef
	 *            the waveBatchRef to set
	 */
	public void setWaveBatchRef(WaveBatchEntity waveBatchRef) {
		this.waveBatchRef = waveBatchRef;
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
	 * @return the employeeRef
	 */
	public EmployeeEntity getEmployeeRef() {
		return employeeRef;
	}

	/**
	 * @param employeeRef
	 *            the employeeRef to set
	 */
	public void setEmployeeRef(EmployeeEntity employeeRef) {
		this.employeeRef = employeeRef;
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public WaveResources convertTo(int mode) {
		WaveResources bean = new WaveResources();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);
		bean.setBatchNumber(batchNumber);
		bean.setWarehouseCode(warehouseCode);
		bean.setEmployeeCode(employeeCode);
		
		Employee employee = employeeRef.convertTo(mode);
		bean.setEmployeeRef(employee);
		
		bean.setResourceType(resourceType);
		bean.setRevisionControl(revisionControl);
		bean.setResourceRole(resourceRole);

		return bean;
	}

}
