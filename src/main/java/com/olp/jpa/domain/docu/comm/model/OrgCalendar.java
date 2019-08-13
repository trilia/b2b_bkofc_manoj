package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.CalendarMonth;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;

@XmlRootElement(name = "orgcalendar", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "calendarCode", "calendarName", "usageType", "periodicity",
		"startMonth", "endMonth", "startYear", "endYear", "startDay", "endDay",
		"lifecycleStatus", "revisionControl" })
public class OrgCalendar implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "calendarId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String calendarCode;

	@XmlElement
	private String calendarName;

	@XmlElement
	private CalUsageType usageType;

	@XmlElement
	private CalPeriodicity periodicity;

	@XmlElement
	private CalendarMonth startMonth;

	@XmlElement
	private CalendarMonth endMonth;

	@XmlElement
	private Integer startYear;

	@XmlElement
	private Integer endYear;

	@XmlElement
	private Integer startDay;

	@XmlElement
	private Integer endDay;

	@XmlElement
	private LifeCycleStage lifecycleStatus;

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
	 * @return the calendarCode
	 */
	public String getCalendarCode() {
		return calendarCode;
	}

	/**
	 * @param calendarCode the calendarCode to set
	 */
	public void setCalendarCode(String calendarCode) {
		this.calendarCode = calendarCode;
	}

	/**
	 * @return the calendarName
	 */
	public String getCalendarName() {
		return calendarName;
	}

	/**
	 * @param calendarName the calendarName to set
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	/**
	 * @return the usageType
	 */
	public CalUsageType getUsageType() {
		return usageType;
	}

	/**
	 * @param usageType the usageType to set
	 */
	public void setUsageType(CalUsageType usageType) {
		this.usageType = usageType;
	}

	/**
	 * @return the periodicity
	 */
	public CalPeriodicity getPeriodicity() {
		return periodicity;
	}

	/**
	 * @param periodicity the periodicity to set
	 */
	public void setPeriodicity(CalPeriodicity periodicity) {
		this.periodicity = periodicity;
	}

	/**
	 * @return the startMonth
	 */
	public CalendarMonth getStartMonth() {
		return startMonth;
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(CalendarMonth startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @return the endMonth
	 */
	public CalendarMonth getEndMonth() {
		return endMonth;
	}

	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(CalendarMonth endMonth) {
		this.endMonth = endMonth;
	}

	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return the startDay
	 */
	public Integer getStartDay() {
		return startDay;
	}

	/**
	 * @param startDay the startDay to set
	 */
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	/**
	 * @return the endDay
	 */
	public Integer getEndDay() {
		return endDay;
	}

	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	/**
	 * @return the lifecycleStatus
	 */
	public LifeCycleStage getLifecycleStatus() {
		return lifecycleStatus;
	}

	/**
	 * @param lifecycleStatus the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifeCycleStage lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
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
	
	public OrgCalendarEntity convertTo(int mode){
		OrgCalendarEntity bean = new OrgCalendarEntity();
		bean.setId(this.id);
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
		
		bean.setRevisionControl(revisionControl);
		return bean;
	}

}
