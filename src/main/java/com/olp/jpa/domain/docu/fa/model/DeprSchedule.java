/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.fa.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.FaEnums.DepreciationType;

@XmlRootElement(name = "unitofmeasure", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "deprScheduleCode", "deprScheduleName", "deprType", "deprTypeImpl", "deprPct",
		"lifecycleStatus", "revisionControl" })
public class DeprSchedule implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "deprScheduleId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement(name = "deprScheduleCode")
	private String deprScheduleCode;

	@XmlElement(name = "deprScheduleName")
	private String deprScheduleName;

	@XmlElement(name = "deprType")
	private DepreciationType deprType;

	@XmlElement(name = "deprTypeImpl")
	private String deprTypeImpl;

	@XmlElement(name = "deprPct")
	private Float deprPct;

	@XmlElement
	private LifeCycleStage lifeCycleStage;

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
	 * @return the deprScheduleName
	 */
	public String getDeprScheduleName() {
		return deprScheduleName;
	}

	/**
	 * @param deprScheduleName
	 *            the deprScheduleName to set
	 */
	public void setDeprScheduleName(String deprScheduleName) {
		this.deprScheduleName = deprScheduleName;
	}

	/**
	 * @return the deprType
	 */
	public DepreciationType getDeprType() {
		return deprType;
	}

	/**
	 * @param deprType
	 *            the deprType to set
	 */
	public void setDeprType(DepreciationType deprType) {
		this.deprType = deprType;
	}

	/**
	 * @return the deprPct
	 */
	public Float getDeprPct() {
		return deprPct;
	}

	/**
	 * @param deprPct
	 *            the deprPct to set
	 */
	public void setDeprPct(Float deprPct) {
		this.deprPct = deprPct;
	}

	/**
	 * @return the lifecycleStage
	 */
	public LifeCycleStage getLifecycleStage() {
		return lifeCycleStage;
	}

	/**
	 * @param lifecycleStage
	 *            the lifecycleStage to set
	 */
	public void setLifecycleStage(LifeCycleStage lifecycleStage) {
		this.lifeCycleStage = lifecycleStage;
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
	 * @return the deprTypeImpl
	 */
	public String getDeprTypeImpl() {
		return deprTypeImpl;
	}

	/**
	 * @param deprTypeImpl the deprTypeImpl to set
	 */
	public void setDeprTypeImpl(String deprTypeImpl) {
		this.deprTypeImpl = deprTypeImpl;
	}

	/**
	 * @return the lifeCycleStage
	 */
	public LifeCycleStage getLifeCycleStage() {
		return lifeCycleStage;
	}

	/**
	 * @param lifeCycleStage the lifeCycleStage to set
	 */
	public void setLifeCycleStage(LifeCycleStage lifeCycleStage) {
		this.lifeCycleStage = lifeCycleStage;
	}

	public DeprScheduleEntity convertTo(int mode) {
		DeprScheduleEntity entity = new DeprScheduleEntity();
		entity.setId(this.id);
		entity.setTenantId(this.tenantId);
		entity.setDeprScheduleCode(deprScheduleCode);
		entity.setDeprScheduleName(deprScheduleName);
		entity.setDeprType(deprType);
		entity.setDeprTypeImpl(deprTypeImpl);
		entity.setDeprPct(deprPct);
		entity.setLifecycleStage(lifeCycleStage);
		entity.setRevisionControl(revisionControl);

		return entity;
	}

}
