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

@XmlRootElement(name = "unitofmeasure", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "categoryCode","defaultDeprScheduleRef", "defaultDeprScheduleCode", "defaultPrefix", "lifeCycleStage",
		 "revisionControl" })
public class AssetCategory implements Serializable  {

	private static final long serialVersionUID = -1L;
	
	@XmlElement(name = "categoryId")
	private Long id;

	@XmlTransient
	private String tenantId;
	
	@XmlElement(name = "categoryCode")
	private String categoryCode;
	
	@XmlElement
	private DeprSchedule defaultDeprScheduleRef;
	
	@XmlElement(name = "defaultDeprScheduleCode")
	private String defaultDeprScheduleCode;
	
	@XmlElement(name = "default-Prefix")
	private String defaultPrefix;
	
	@XmlElement(name = "lifecycle-stage")
	private LifeCycleStage lifeCycleStage;
	
	private RevisionControlBean revisionControl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the defaultDeprScheduleRef
	 */
	public DeprSchedule getDefaultDeprScheduleRef() {
		return defaultDeprScheduleRef;
	}

	/**
	 * @param defaultDeprScheduleRef the defaultDeprScheduleRef to set
	 */
	public void setDefaultDeprScheduleRef(DeprSchedule defaultDeprScheduleRef) {
		this.defaultDeprScheduleRef = defaultDeprScheduleRef;
	}

	/**
	 * @return the defaultDeprScheduleCode
	 */
	public String getDefaultDeprScheduleCode() {
		return defaultDeprScheduleCode;
	}

	/**
	 * @param defaultDeprScheduleCode the defaultDeprScheduleCode to set
	 */
	public void setDefaultDeprScheduleCode(String defaultDeprScheduleCode) {
		this.defaultDeprScheduleCode = defaultDeprScheduleCode;
	}

	/**
	 * @return the defaultPrefix
	 */
	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	/**
	 * @param defaultPrefix the defaultPrefix to set
	 */
	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
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

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	/**
	 * @param revisionControl the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}
	
	public AssetCategoryEntity convertTo(int mode) {
		AssetCategoryEntity entity = new AssetCategoryEntity();
		entity.setId(this.id);
		entity.setTenantId(this.tenantId);
		entity.setCategoryCode(categoryCode);
		entity.setDefaultDeprScheduleCode(defaultDeprScheduleCode); 
		DeprScheduleEntity deprScheduleEntity = new DeprScheduleEntity();
		deprScheduleEntity.setDeprScheduleCode(defaultDeprScheduleRef.getDeprScheduleCode());
		
		entity.setDefaultDeprScheduleRef(deprScheduleEntity);
		entity.setDefaultPrefix(defaultPrefix);
		entity.setLifeCycleStage(lifeCycleStage);
		entity.setRevisionControl(revisionControl);
		
		return entity;	
	}
	
}
