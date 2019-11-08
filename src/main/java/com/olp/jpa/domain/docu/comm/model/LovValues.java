package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "lovVlaues", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "lovCode", "lovDefRef", "value", "isEnabled", "revisionControl" })
public class LovValues implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "lovDefId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String lovCode;

	@XmlElement
	private String lovDefRef;

	@XmlElement
	private String value;

	@XmlElement
	private boolean isEnabled;

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
	 * @return the lovCode
	 */
	public String getLovCode() {
		return lovCode;
	}

	/**
	 * @param lovCode
	 *            the lovCode to set
	 */
	public void setLovCode(String lovCode) {
		this.lovCode = lovCode;
	}

	/**
	 * @return the lovDefRef
	 */
	public String getLovDefRef() {
		return lovDefRef;
	}

	/**
	 * @param lovDefRef
	 *            the lovDefRef to set
	 */
	public void setLovDefRef(String lovDefRef) {
		this.lovDefRef = lovDefRef;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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

	public LovValuesEntity convertTo(int mode) {
		LovValuesEntity bean = new LovValuesEntity();
		bean.setEnabled(isEnabled);
		bean.setId(id);
		bean.setLovCode(lovCode);
		bean.setRevisionControl(revisionControl);
		bean.setTenantId(tenantId);
		bean.setValue(value);
		
		LovDefinitionEntity lovDefintionEntity = new LovDefinitionEntity();
		lovDefintionEntity.setLovCode(lovDefRef);
		bean.setLovDefRef(lovDefintionEntity);
		
		return bean;
	}

}
