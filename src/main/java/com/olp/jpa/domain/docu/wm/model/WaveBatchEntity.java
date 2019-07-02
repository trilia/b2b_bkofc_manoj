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

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.persistence.OneToMany;
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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.wm.model.WmEnums.WaveType;

@Entity
@Table(name = "trl_wh_wave_batches", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"batch_number" }))
@NamedQueries({
	@NamedQuery(name = "WaveBatchEntity.findByBatchNum", query = "SELECT t FROM WaveBatchEntity t JOIN FETCH t.waveResources t1 JOIN FETCH t.waveTasks t2 WHERE t.batchNumber = :batchNum and t.tenantId = :tenant ")
})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class WaveBatchEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wave_batch_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "batch_number", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String batchNumber;

	@ManyToOne
	@JoinColumn(name = "warehouse_ref")
	@ContainedIn
	private WarehouseEntity warehouseRef;

	@Column(name = "warehouse_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String warehouseCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "wave_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private WaveType waveType;

	@Column(name = "work_shift_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String workshiftCode;

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

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStatus lifecycleStatus;

	@OneToMany(mappedBy = "waveBatchRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<WaveResourcesEntity> waveResources = new HashSet<>();
	
	@OneToMany(mappedBy = "waveBatchRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<WaveTasksEntity> waveTasks = new ArrayList<>();

	@Embedded
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
	public WarehouseEntity getWarehouseRef() {
		return warehouseRef;
	}

	/**
	 * @param warehouseRef
	 *            the warehouseRef to set
	 */
	public void setWarehouseRef(WarehouseEntity warehouseRef) {
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
	public Set<WaveResourcesEntity> getWaveResoures() {
		return waveResources;
	}

	/**
	 * @param waveResoures
	 *            the waveResoures to set
	 */
	public void setWaveResoures(Set<WaveResourcesEntity> waveResources) {
		if (waveResources != null)
	          this.waveResources = waveResources;
	      else
	          this.waveResources.clear();
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
	public List<WaveTasksEntity> getWaveTasks() {
		return waveTasks;
	}

	/**
	 * @param waveTasks the waveTasks to set
	 */
	public void setWaveTasks(List<WaveTasksEntity> waveTasks) {
		if (waveTasks != null)
	          this.waveTasks = waveTasks;
	      else
	          this.waveTasks.clear();
	}

	public WaveBatch convertTo(int mode) {
		WaveBatch bean = new WaveBatch();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION) {
			bean.setId(this.id);
		}
		bean.setTenantId(this.tenantId);
		bean.setBatchNumber(batchNumber);
		bean.setWarehouseCode(warehouseCode);
		bean.setWorkshiftCode(workshiftCode);

		Warehouse wh = warehouseRef.convertTo(mode);
		bean.setWarehouseRef(wh);
		bean.setWaveType(waveType);
		bean.setActualFinishDate(actualFinishDate);
		bean.setActualStartDate(actualStartDate);
		bean.setEstimatedFinishDate(estimatedFinishDate);
		bean.setEstimatedStartDate(estimatedStartDate);
		bean.setRevisionControl(revisionControl);

		Set<WaveResources> waveResourcesSet = new HashSet<>();
		for (WaveResourcesEntity waveResourcesEntity : waveResources) {
			WaveResources waveResource = waveResourcesEntity.convertTo(mode);
			waveResourcesSet.add(waveResource);
		}
		bean.setWaveResoures(waveResourcesSet);
		
		List<WaveTasks> waveTasksList = new ArrayList<>();
		for(WaveTasksEntity waveTaksEntity : waveTasks){
			WaveTasks waveTask = waveTaksEntity.convertTo(mode);
			waveTasksList.add(waveTask);
		}
		bean.setWaveTasks(waveTasksList);
		
		return bean;
	}

}
