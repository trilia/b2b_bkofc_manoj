package com.olp.jpa.domain.docu.comm.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.CommonEnums.CalendarMonth;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.LifeCycleStage;

@Entity
@Table(name = "trl_org_calendar", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "calendar_code" }))
@NamedQueries({
		@NamedQuery(name = "OrgCalendarEntity.findByCalendarCode", query = "SELECT t FROM OrgCalendarEntity t WHERE t.calendarCode = :calCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class OrgCalendarEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "calendar_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "calendar_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String calendarCode;

	@Column(name = "calendar_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String calendarName;

	@Enumerated(EnumType.STRING)
	@Column(name = "usage_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalUsageType usageType;

	@Enumerated(EnumType.STRING)
	@Column(name = "periodicity", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalPeriodicity periodicity;

	@Enumerated(EnumType.STRING)
	@Column(name = "start_month", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalendarMonth startMonth;

	@Enumerated(EnumType.STRING)
	@Column(name = "end_month", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalendarMonth endMonth;

	@Column(name = "start_year", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer startYear;

	@Column(name = "end_year", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer endYear;

	@Column(name = "start_day", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer startDay;

	@Column(name = "end_day", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer endDay;

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStage lifecycleStage;

	@Embedded
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
	 * @return the calendarCode
	 */
	public String getCalendarCode() {
		return calendarCode;
	}

	/**
	 * @param calendarCode
	 *            the calendarCode to set
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
	 * @param calendarName
	 *            the calendarName to set
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
	 * @param usageType
	 *            the usageType to set
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
	 * @param periodicity
	 *            the periodicity to set
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
	 * @param startMonth
	 *            the startMonth to set
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
	 * @param endMonth
	 *            the endMonth to set
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
	 * @param startYear
	 *            the startYear to set
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
	 * @param endYear
	 *            the endYear to set
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
	 * @param startDay
	 *            the startDay to set
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
	 * @param endDay
	 *            the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	/**
	 * @return the lifecycleStage
	 */
	public LifeCycleStage getLifecycleStage() {
		return lifecycleStage;
	}

	/**
	 * @param lifecycleStage the lifecycleStage to set
	 */
	public void setLifecycleStage(LifeCycleStage lifecycleStage) {
		this.lifecycleStage = lifecycleStage;
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

	public OrgCalendar convertTo(int mode) {
		OrgCalendar bean = new OrgCalendar();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
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

		return (bean);
	}
}
