package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "lovDefinition", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "lovCode", "lovName", "lovType",
		"isEnabled", "lovValues", "revisionControl" })
public class LovDefinition implements Serializable{

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "lovDefId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String lovCode;
	
	@XmlElement
	private String lovName;

	@XmlElement
	private LovType lovType;
	
	@XmlElement
	private boolean isEnabled;
	
	private List<LovValuesEntity> lovValues;
	
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
	 * @return the lovCode
	 */
	public String getLovCode() {
		return lovCode;
	}

	/**
	 * @param lovCode the lovCode to set
	 */
	public void setLovCode(String lovCode) {
		this.lovCode = lovCode;
	}

	/**
	 * @return the lovName
	 */
	public String getLovName() {
		return lovName;
	}

	/**
	 * @param lovName the lovName to set
	 */
	public void setLovName(String lovName) {
		this.lovName = lovName;
	}

	/**
	 * @return the lovType
	 */
	public LovType getLovType() {
		return lovType;
	}

	/**
	 * @param lovType the lovType to set
	 */
	public void setLovType(LovType lovType) {
		this.lovType = lovType;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the lovValues
	 */
	public List<LovValuesEntity> getLovValues() {
		return lovValues;
	}

	/**
	 * @param lovValues the lovValues to set
	 */
	public void setLovValues(List<LovValuesEntity> lovValues) {
		this.lovValues = lovValues;
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
	
	public LovValuesEntity convertTo(int mode){
		LovValuesEntity bean = new LovValuesEntity();
		/*bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setCalendarCode(calendarCode);
		bean.setCalendarName(calendarName);
		bean.setUsageType(usageType);		
		bean.setPeriodicity(periodicity);
		bean.setStartMonth(startMonth);
		bean.setEndMonth(endMonth);
		bean.setStartYear(startYear);
		bean.setEndYear(endYear);
		bean.setStartDay(startDay);
		bean.setEndDay(endDay);
		
		bean.setRevisionControl(revisionControl);*/
		return bean;
	}
	
}
