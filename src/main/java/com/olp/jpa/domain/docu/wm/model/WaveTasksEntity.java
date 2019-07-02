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

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasure;
import com.olp.jpa.domain.docu.inv.model.UnitOfMeasureEntity;
import com.olp.jpa.domain.docu.org.model.Employee;
import com.olp.jpa.domain.docu.org.model.EmployeeEntity;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveTaskStatus;

@Entity
@Table(name = "trl_wh_wave_tasks", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "batch_number" }))
/*@NamedQueries({
	@NamedQuery(name = "WaveTasksEntity.findByBatch", query = "SELECT t FROM WaveTasksEntity t JOIN FETCH t.waveBatchRef WHERE t.batchNumber = :batchNum and "
			+ "t.tenantId = :tenant ") })*/
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class WaveTasksEntity implements Serializable {

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

	@Column(name = "mat_pipe_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String matPipeCode;

	@ManyToOne
	@JoinColumn(name = "mat_pipe_ref")
	@ContainedIn
	private MaterialPipelineEntity matPipeRef;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "trl_pallet_serials", joinColumns = { @JoinColumn(name = "wave_task_id") })
	private Set<SerialInfoBean> serials;

	@Column(name = "task_quantity", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal quantity;

	@ManyToOne
	@JoinColumn(name = "quantity_uom_ref")
	@ContainedIn
	private UnitOfMeasureEntity quantityUomRef;

	@Column(name = "uom_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String uomCode;

	@ManyToOne
	@JoinColumn(name = "pallet_ref")
	@ContainedIn
	private PalletEntity palletRef;

	@Column(name = "pallet_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String palletNum;

	@ManyToOne
	@JoinColumn(name = "assigned_to_emp")
	@ContainedIn
	private EmployeeEntity assignedTo;

	@Column(name = "employee_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String employeeCode;

	@Column(name = "estimated_start", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	@DateBridge(resolution = Resolution.DAY)
	private Date estimatedStartDate;

	@Column(name = "actual_start", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date actualStartDate;

	@Column(name = "estimated_finish", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date estimatedFinishDate;

	@Column(name = "actual_finish", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date actualFinishDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "task_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private WaveTaskStatus taskStatus;

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
	 * @return the matPipeRef
	 */
	public MaterialPipelineEntity getMatPipeRef() {
		return matPipeRef;
	}

	/**
	 * @param matPipeRef
	 *            the matPipeRef to set
	 */
	public void setMatPipeRef(MaterialPipelineEntity matPipeRef) {
		this.matPipeRef = matPipeRef;
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
	public UnitOfMeasureEntity getQuantityUomRef() {
		return quantityUomRef;
	}

	/**
	 * @param quantityUomRef
	 *            the quantityUomRef to set
	 */
	public void setQuantityUomRef(UnitOfMeasureEntity quantityUomRef) {
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
	 * @return the palletRef
	 */
	public PalletEntity getPalletRef() {
		return palletRef;
	}

	/**
	 * @param palletRef
	 *            the palletRef to set
	 */
	public void setPalletRef(PalletEntity palletRef) {
		this.palletRef = palletRef;
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
	public EmployeeEntity getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo
	 *            the assignedTo to set
	 */
	public void setAssignedTo(EmployeeEntity assignedTo) {
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

	public WaveTasks convertTo(int mode) {
		WaveTasks bean = new WaveTasks();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);
		bean.setActualFinishDate(actualFinishDate);
		bean.setActualStartDate(actualStartDate);

		Employee employee = assignedTo.convertTo(mode);
		bean.setAssignedTo(employee);
		bean.setBatchNumber(batchNumber);
		bean.setEmployeeCode(employeeCode);
		bean.setEmployeeCode(employeeCode);
		bean.setEstimatedFinishDate(estimatedFinishDate);
		bean.setEstimatedStartDate(estimatedStartDate);
		bean.setMatPipeCode(matPipeCode);

		bean.setPalletNum(palletNum);
		bean.setQuantity(quantity); 
		if(quantityUomRef != null){
			UnitOfMeasure uom = quantityUomRef.convertTo(mode);
			bean.setQuantityUomRef(uom);
		}

		bean.setRevisionControl(revisionControl);
		bean.setSerials(serials);
		bean.setTaskStatus(taskStatus);
		bean.setUomCode(uomCode);
		bean.setWarehouseCode(warehouseCode);

		return bean;
	}
}
