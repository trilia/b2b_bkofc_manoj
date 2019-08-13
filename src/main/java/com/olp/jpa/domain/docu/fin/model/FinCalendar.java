package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.CalendarMonth;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.fin.model.FinEnums.CalPeriodStatus;

@XmlRootElement(name = "fincalendar", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "periodName", "calendarName", "orgCalendarRef", "periodMonth", "periodYear",
		"periodStatus", "revisionControl" })
public class FinCalendar implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "periodId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String periodName;

	@XmlElement
	private String orgCalendarRef;

	@XmlElement
	private CalendarMonth periodMonth;

	@XmlElement
	private Integer periodYear;

	@XmlElement
	private CalPeriodStatus periodStatus;

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
	 * @return the periodName
	 */
	public String getPeriodName() {
		return periodName;
	}

	/**
	 * @param periodName
	 *            the periodName to set
	 */
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	/**
	 * @return the orgCalendarRef
	 */
	public String getOrgCalendarRef() {
		return orgCalendarRef;
	}

	/**
	 * @param orgCalendarRef
	 *            the orgCalendarRef to set
	 */
	public void setOrgCalendarRef(String orgCalendarRef) {
		this.orgCalendarRef = orgCalendarRef;
	}

	/**
	 * @return the periodMonth
	 */
	public CalendarMonth getPeriodMonth() {
		return periodMonth;
	}

	/**
	 * @param periodMonth
	 *            the periodMonth to set
	 */
	public void setPeriodMonth(CalendarMonth periodMonth) {
		this.periodMonth = periodMonth;
	}

	/**
	 * @return the periodYear
	 */
	public Integer getPeriodYear() {
		return periodYear;
	}

	/**
	 * @param periodYear
	 *            the periodYear to set
	 */
	public void setPeriodYear(Integer periodYear) {
		this.periodYear = periodYear;
	}

	/**
	 * @return the periodStatus
	 */
	public CalPeriodStatus getPeriodStatus() {
		return periodStatus;
	}

	/**
	 * @param periodStatus
	 *            the periodStatus to set
	 */
	public void setPeriodStatus(CalPeriodStatus periodStatus) {
		this.periodStatus = periodStatus;
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

	public FinCalendarEntity convertTo(int mode) {
		FinCalendarEntity bean = new FinCalendarEntity();
		bean.setId(id);
		bean.setOrgCalendarCode(orgCalendarRef);

		OrgCalendarEntity orgCalendarEntity = new OrgCalendarEntity();
		orgCalendarEntity.setCalendarCode(orgCalendarRef);

		bean.setOrgCalendarRef(orgCalendarEntity);
		bean.setPeriodMonth(periodMonth);
		bean.setPeriodName(periodName);
		bean.setPeriodStatus(periodStatus);
		bean.setPeriodStatus(periodStatus);
		bean.setPeriodYear(periodYear);

		if (mode <= 0) {
			bean.setRevisionControl(revisionControl);
		}

		return bean;
	}
}
