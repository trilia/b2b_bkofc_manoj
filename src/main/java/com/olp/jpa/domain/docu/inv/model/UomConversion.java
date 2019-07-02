/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;

@XmlRootElement(name = "unitofconversion", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "entrySequence", "srcUomCode", "destUomCode", "convFactor", "convFunction",
		"lifecycleStatus" })
public class UomConversion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "uomConvId")
	private Long id;

	@XmlElement
	private String tenantId;

	@XmlElement
	private int entrySequence;

	@XmlElement
	private String srcUomCode;

	@XmlElement
	private String destUomCode;

	@XmlElement
	private BigDecimal convFactor;

	@XmlElement
	private String convFunction;

	@XmlElement
	private LifeCycleStatus lifecycleStatus;

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
	 * @return the entrySequence
	 */
	public int getEntrySequence() {
		return entrySequence;
	}

	/**
	 * @param entrySequence
	 *            the entrySequence to set
	 */
	public void setEntrySequence(int entrySequence) {
		this.entrySequence = entrySequence;
	}

	/**
	 * @return the srcUomCode
	 */
	public String getSrcUomCode() {
		return srcUomCode;
	}

	/**
	 * @param srcUomCode
	 *            the srcUomCode to set
	 */
	public void setSrcUomCode(String srcUomCode) {
		this.srcUomCode = srcUomCode;
	}

	/**
	 * @return the destUomCode
	 */
	public String getDestUomCode() {
		return destUomCode;
	}

	/**
	 * @param destUomCode
	 *            the destUomCode to set
	 */
	public void setDestUomCode(String destUomCode) {
		this.destUomCode = destUomCode;
	}

	/**
	 * @return the convFactor
	 */
	public BigDecimal getConvFactor() {
		return convFactor;
	}

	/**
	 * @param convFactor
	 *            the convFactor to set
	 */
	public void setConvFactor(BigDecimal convFactor) {
		this.convFactor = convFactor;
	}

	/**
	 * @return the convFunction
	 */
	public String getConvFunction() {
		return convFunction;
	}

	/**
	 * @param convFunction
	 *            the convFunction to set
	 */
	public void setConvFunction(String convFunction) {
		this.convFunction = convFunction;
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

	public UomConversionEntity covertTo(int mode) {
		UomConversionEntity bean = new UomConversionEntity();
		bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setEntrySequence(entrySequence);
		bean.setSrcUomCode(srcUomCode);
		bean.setDestUomCode(destUomCode);
		bean.setConvFactor(convFactor);
		bean.setConvFunction(convFunction);
		return bean;
	}
}
