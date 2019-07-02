/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomClass;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomType;

@XmlRootElement(name = "unitofmeasure", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "uomCode", "uomDesc", "uomType", "uomClass", "lifecycleStatus", "isStrictConv",
		"srcConversions", "destConversions", "revisionControl" })
public class UnitOfMeasure implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "uomId")
	private Long id;

	@XmlElement
	private String tenantId;

	@XmlElement
	private String uomCode;

	@XmlElement
	private String uomDesc;

	@XmlElement
	private UomType uomType;

	@XmlElement
	private UomClass uomClass;

	@XmlElement
	private LifeCycleStatus lifecycleStatus;

	@XmlElement
	private boolean isStrictConv;

	@XmlElement
	private Set<UomConversion> srcConversions;

	@XmlElement
	private Set<UomConversion> destConversions;

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
	 * @return the uomDesc
	 */
	public String getUomDesc() {
		return uomDesc;
	}

	/**
	 * @param uomDesc
	 *            the uomDesc to set
	 */
	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	/**
	 * @return the uomType
	 */
	public UomType getUomType() {
		return uomType;
	}

	/**
	 * @param uomType
	 *            the uomType to set
	 */
	public void setUomType(UomType uomType) {
		this.uomType = uomType;
	}

	/**
	 * @return the uomClass
	 */
	public UomClass getUomClass() {
		return uomClass;
	}

	/**
	 * @param uomClass
	 *            the uomClass to set
	 */
	public void setUomClass(UomClass uomClass) {
		this.uomClass = uomClass;
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
	 * @return the isStrictConv
	 */
	public boolean isStrictConv() {
		return isStrictConv;
	}

	/**
	 * @param isStrictConv
	 *            the isStrictConv to set
	 */
	public void setStrictConv(boolean isStrictConv) {
		this.isStrictConv = isStrictConv;
	}

	/**
	 * @return the srcConversions
	 */
	public Set<UomConversion> getSrcConversions() {
		return srcConversions;
	}

	/**
	 * @param srcConversions
	 *            the srcConversions to set
	 */
	public void setSrcConversions(Set<UomConversion> srcConversions) {
		this.srcConversions = srcConversions;
	}

	/**
	 * @return the destConversions
	 */
	public Set<UomConversion> getDestConversions() {
		return destConversions;
	}

	/**
	 * @param destConversions
	 *            the destConversions to set
	 */
	public void setDestConversions(Set<UomConversion> destConversions) {
		this.destConversions = destConversions;
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

	public UnitOfMeasureEntity convertTo(int mode) {
		UnitOfMeasureEntity bean = new UnitOfMeasureEntity();

		bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setUomClass(uomClass);
		bean.setUomCode(uomCode);
		bean.setUomDesc(uomDesc);
		bean.setUomType(uomType);
		bean.setStrictConv(isStrictConv);

		Set<UomConversionEntity> srcConversionsSet = new HashSet<>();
		for (UomConversion uom : srcConversions) {
			UomConversionEntity uomConversionEntity = uom.covertTo(mode);
			srcConversionsSet.add(uomConversionEntity);
		}

		Set<UomConversionEntity> destConversionsSet = new HashSet<>();
		for (UomConversion uom : destConversions) {
			UomConversionEntity uomConversionEntity = uom.covertTo(mode);
			destConversionsSet.add(uomConversionEntity);
		}

		bean.setSrcConversions(srcConversionsSet);
		bean.setDestConversions(destConversionsSet);
		bean.setRevisionControl(revisionControl);
		return bean;
	}
}
