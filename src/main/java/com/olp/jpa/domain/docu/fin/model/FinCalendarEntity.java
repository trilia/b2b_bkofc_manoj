package com.olp.jpa.domain.docu.fin.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.CalendarMonth;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.comm.model.OrgCalendarEntity;
import com.olp.jpa.domain.docu.fin.model.FinEnums.CalPeriodStatus;

@Entity
@Table(name = "trl_fin_cal_periods", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "period_name" }))
@NamedQueries({
		@NamedQuery(name = "FinCalendarEntity.findByCalendarCode", query = "SELECT t FROM FinCalendarEntity t WHERE t.orgCalendarCode = :calCode and t.periodMonth = :periodMonth and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class FinCalendarEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "period_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "period_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String periodName;

	@ManyToOne
	@JoinColumn(name = "org_calendar_ref")
	@ContainedIn
	private OrgCalendarEntity orgCalendarRef;

	@Column(name = "org_calendar_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String orgCalendarCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "period_month", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalendarMonth periodMonth;

	@Column(name = "period_year", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer periodYear;

	@Column(name = "period_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CalPeriodStatus periodStatus;

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
	public OrgCalendarEntity getOrgCalendarRef() {
		return orgCalendarRef;
	}

	/**
	 * @param orgCalendarRef
	 *            the orgCalendarRef to set
	 */
	public void setOrgCalendarRef(OrgCalendarEntity orgCalendarRef) {
		this.orgCalendarRef = orgCalendarRef;
	}

	/**
	 * @return the orgCalendarCode
	 */
	public String getOrgCalendarCode() {
		return orgCalendarCode;
	}

	/**
	 * @param orgCalendarCode
	 *            the orgCalendarCode to set
	 */
	public void setOrgCalendarCode(String orgCalendarCode) {
		this.orgCalendarCode = orgCalendarCode;
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

	public FinCalendar convertTo(int mode) {
		FinCalendar bean = new FinCalendar();
		bean.setId(id);
		bean.setTenantId(tenantId);

		bean.setOrgCalendarRef(orgCalendarRef.getCalendarCode());
		bean.setPeriodMonth(periodMonth);
		bean.setPeriodName(periodName);
		bean.setPeriodStatus(periodStatus);
		bean.setPeriodYear(periodYear);

		return bean;
	}
}
