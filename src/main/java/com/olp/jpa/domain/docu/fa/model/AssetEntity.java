/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.fa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

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
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.FaEnums.AssetLifeCycleStage;

@Entity
@Table(name = "trl_assets")
@NamedQueries({
	@NamedQuery(name = "AssetEntity.findByAssetCode", query = "SELECT t FROM AssetEntity t WHERE t.assetCode = :assetCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class AssetEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "asset_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String assetCode;

	@ManyToOne(optional = true)
	@JoinColumn(name = "category_ref")
	@ContainedIn
	private AssetCategoryEntity categoryRef;

	@Column(name = "category_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String categoryCode;

	@Column(name = "acquisition_date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date acquisitionDate;

	@Column(name = "retired_date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date retiredDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "depr_schedule_ref")
	@ContainedIn
	private DeprScheduleEntity deprScheduleRef;

	@Column(name = "depr_schedule_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String deprScheduleCode;

	@Column(name = "acquisition_cost", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal acquisitionCost;

	@Column(name = "residual_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal residualValue;

	@Column(name = "written_off_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isWrittenOff;

	@Column(name = "date_written_off", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Field(analyze = Analyze.NO, store = Store.YES)
	private Date dateWrittenOff;

	@Column(name = "written_off_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal writtenOffValue;

	@Column(name = "asset_lifecycle", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private AssetLifeCycleStage assetLifecycle;

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
	 * @return the assetCode
	 */
	public String getAssetCode() {
		return assetCode;
	}

	/**
	 * @param assetCode
	 *            the assetCode to set
	 */
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	/**
	 * @return the categoryRef
	 */
	public AssetCategoryEntity getCategoryRef() {
		return categoryRef;
	}

	/**
	 * @param categoryRef
	 *            the categoryRef to set
	 */
	public void setCategoryRef(AssetCategoryEntity categoryRef) {
		this.categoryRef = categoryRef;
	}

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode
	 *            the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the acquisitionDate
	 */
	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	/**
	 * @param acquisitionDate
	 *            the acquisitionDate to set
	 */
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	/**
	 * @return the retiredDate
	 */
	public Date getRetiredDate() {
		return retiredDate;
	}

	/**
	 * @param retiredDate
	 *            the retiredDate to set
	 */
	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}

	/**
	 * @return the deprScheduleRef
	 */
	public DeprScheduleEntity getDeprScheduleRef() {
		return deprScheduleRef;
	}

	/**
	 * @param deprScheduleRef
	 *            the deprScheduleRef to set
	 */
	public void setDeprScheduleRef(DeprScheduleEntity deprScheduleRef) {
		this.deprScheduleRef = deprScheduleRef;
	}

	/**
	 * @return the deprScheduleCode
	 */
	public String getDeprScheduleCode() {
		return deprScheduleCode;
	}

	/**
	 * @param deprScheduleCode
	 *            the deprScheduleCode to set
	 */
	public void setDeprScheduleCode(String deprScheduleCode) {
		this.deprScheduleCode = deprScheduleCode;
	}

	/**
	 * @return the acquisitionCost
	 */
	public BigDecimal getAcquisitionCost() {
		return acquisitionCost;
	}

	/**
	 * @param acquisitionCost
	 *            the acquisitionCost to set
	 */
	public void setAcquisitionCost(BigDecimal acquisitionCost) {
		this.acquisitionCost = acquisitionCost;
	}

	/**
	 * @return the residualValue
	 */
	public BigDecimal getResidualValue() {
		return residualValue;
	}

	/**
	 * @param residualValue
	 *            the residualValue to set
	 */
	public void setResidualValue(BigDecimal residualValue) {
		this.residualValue = residualValue;
	}

	/**
	 * @return the isWrittenOff
	 */
	public boolean isWrittenOff() {
		return isWrittenOff;
	}

	/**
	 * @param isWrittenOff
	 *            the isWrittenOff to set
	 */
	public void setWrittenOff(boolean isWrittenOff) {
		this.isWrittenOff = isWrittenOff;
	}

	/**
	 * @return the dateWrittenOff
	 */
	public Date getDateWrittenOff() {
		return dateWrittenOff;
	}

	/**
	 * @param dateWrittenOff
	 *            the dateWrittenOff to set
	 */
	public void setDateWrittenOff(Date dateWrittenOff) {
		this.dateWrittenOff = dateWrittenOff;
	}

	/**
	 * @return the writtenOffValue
	 */
	public BigDecimal getWrittenOffValue() {
		return writtenOffValue;
	}

	/**
	 * @param writtenOffValue
	 *            the writtenOffValue to set
	 */
	public void setWrittenOffValue(BigDecimal writtenOffValue) {
		this.writtenOffValue = writtenOffValue;
	}

	/**
	 * @return the assetLifecycle
	 */
	public AssetLifeCycleStage getAssetLifecycle() {
		return assetLifecycle;
	}

	/**
	 * @param assetLifecycle
	 *            the assetLifecycle to set
	 */
	public void setAssetLifecycle(AssetLifeCycleStage assetLifecycle) {
		this.assetLifecycle = assetLifecycle;
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

	public Asset convertTo(int mode) {
		Asset bean = new Asset();

		bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setAssetCode(assetCode);

		bean.setCategoryCode(categoryCode);
		bean.setAcquisitionDate(acquisitionDate);
		bean.setRetiredDate(retiredDate);
		DeprSchedule deprSchedule = new DeprSchedule();
		deprSchedule.setDeprScheduleCode(deprScheduleCode);
		bean.setDeprScheduleRef(deprSchedule);

		bean.setDeprScheduleCode(deprScheduleCode);
		bean.setAcquisitionCost(acquisitionCost);
		bean.setResidualValue(residualValue);
		bean.setWrittenOff(isWrittenOff);
		bean.setDateWrittenOff(dateWrittenOff);
		bean.setWrittenOffValue(writtenOffValue);
		bean.setAssetLifecycle(assetLifecycle);
		bean.setRevisionControl(revisionControl);

		return bean;
	}

}
