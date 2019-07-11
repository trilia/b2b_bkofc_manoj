/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.fa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.FaEnums.AssetLifeCycleStage;

@XmlRootElement(name = "unitofmeasure", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "assetCode", "categoryRef", "categoryCode", "acquisitionDate", "retiredDate",
		"deprScheduleRef", "deprScheduleCode", "acquisitionCost", "residualValue", "isWrittenOff", "dateWrittenOff",
		"writtenOffValue", "assetLifecycle", "revisionControl" })
public class Asset implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "categoryId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement(name = "assetCode")
	private String assetCode;

	@XmlElement(name = "categoryCode")
	private String categoryCode;

	@XmlElement
	private Date acquisitionDate;

	@XmlElement
	private Date retiredDate;

	@XmlElement
	private DeprSchedule deprScheduleRef;

	@XmlElement(name = "deprScheduleCode")
	private String deprScheduleCode;

	@XmlElement(name = "acquisitionCost")
	private BigDecimal acquisitionCost;

	@XmlElement(name = "residualValue")
	private BigDecimal residualValue;

	@XmlElement(name = "isWrittenOff")
	private boolean isWrittenOff;

	@XmlElement(name = "dateWrittenOff")
	private Date dateWrittenOff;

	@XmlElement(name = "writtenOffValue")
	private BigDecimal writtenOffValue;

	@XmlElement(name = "assetLifecycle")
	private AssetLifeCycleStage assetLifecycle;

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
	public DeprSchedule getDeprScheduleRef() {
		return deprScheduleRef;
	}

	/**
	 * @param deprScheduleRef
	 *            the deprScheduleRef to set
	 */
	public void setDeprScheduleRef(DeprSchedule deprScheduleRef) {
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
	 * @param assetLifecycle the assetLifecycle to set
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

	public AssetEntity convertTo(int mode) {
		AssetEntity entity = new AssetEntity();
		entity.setId(this.id);
		entity.setTenantId(this.tenantId);
		entity.setAssetCode(assetCode);
		AssetCategoryEntity assetCategoryEntity = new AssetCategoryEntity ();
		assetCategoryEntity.setCategoryCode(categoryCode);		
		entity.setCategoryRef(assetCategoryEntity);
		
		entity.setCategoryCode(categoryCode);
		entity.setAcquisitionDate(acquisitionDate);
		entity.setRetiredDate(retiredDate);
		DeprScheduleEntity deprScheduleEntity = new DeprScheduleEntity();
		deprScheduleEntity.setDeprScheduleCode(deprScheduleCode);
		
		entity.setDeprScheduleRef(deprScheduleEntity);
		
		entity.setDeprScheduleCode(deprScheduleCode);
		entity.setAcquisitionCost(acquisitionCost);
		entity.setResidualValue(residualValue);
		entity.setWrittenOff(isWrittenOff);
		entity.setDateWrittenOff(dateWrittenOff);
		entity.setWrittenOffValue(writtenOffValue);
		entity.setAssetLifecycle(assetLifecycle);
		entity.setRevisionControl(revisionControl);
		
		return entity;
	}
}
