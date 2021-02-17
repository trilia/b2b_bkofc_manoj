package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.Date;

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
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;

@Entity
@Table(name = "trl_cs_loyalty_tier", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"cs_loyalty_tier_code" }))
@NamedQueries({
		@NamedQuery(name = "CustomerLoyaltyTierEntity.findByCustTierCode", query = "SELECT t FROM CustomerLoyaltyTierEntity t WHERE t.programTierCode = :programCode and t.customerCode = :customerCode and t.csLoyaltyTierCode = :tierCode and t.tenantId = :tenantId ") ,
		@NamedQuery(name = "CustomerLoyaltyTierEntity.findByCustomerTierCode", query = "SELECT t FROM CustomerLoyaltyTierEntity t WHERE t.customerCode = :customerCode and t.tenantId = :tenantId order by t.startDate ") ,
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class CustomerLoyaltyTierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cs_loyalty_tier_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "cs_loyalty_tier_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String csLoyaltyTierCode;

	@ManyToOne(optional=true)
	@JoinColumn(name = "cs_loyalty_ref")
	@ContainedIn
	private CustomerLoyaltyEntity csLoyaltyRef;

	@Column(name = "cs_loyalty_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String csLoyaltyCode;

	@Column(name = "customer_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String customerCode;

	@ManyToOne
	@JoinColumn(name = "program_tier_ref")
	@ContainedIn
	private ProgramTierEntity programTierRef;

	@Column(name = "program_tier_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String programTierCode;

	@Column(name = "start_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date startDate;

	@Column(name = "end_date", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date endDate;

	@Column(name = "status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private ParticipationStatus status;

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
	 * @return the csLoyaltyTierCode
	 */
	public String getCsLoyaltyTierCode() {
		return csLoyaltyTierCode;
	}

	/**
	 * @param csLoyaltyTierCode
	 *            the csLoyaltyTierCode to set
	 */
	public void setCsLoyaltyTierCode(String csLoyaltyTierCode) {
		this.csLoyaltyTierCode = csLoyaltyTierCode;
	}

	/**
	 * @return the csLoyaltyRef
	 */
	public CustomerLoyaltyEntity getCsLoyaltyRef() {
		return csLoyaltyRef;
	}

	/**
	 * @param csLoyaltyRef
	 *            the csLoyaltyRef to set
	 */
	public void setCsLoyaltyRef(CustomerLoyaltyEntity csLoyaltyRef) {
		this.csLoyaltyRef = csLoyaltyRef;
	}

	/**
	 * @return the csLoyaltyCode
	 */
	public String getCsLoyaltyCode() {
		return csLoyaltyCode;
	}

	/**
	 * @param csLoyaltyCode
	 *            the csLoyaltyCode to set
	 */
	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the programTierRef
	 */
	public ProgramTierEntity getProgramTierRef() {
		return programTierRef;
	}

	/**
	 * @param programTierRef
	 *            the programTierRef to set
	 */
	public void setProgramTierRef(ProgramTierEntity programTierRef) {
		this.programTierRef = programTierRef;
	}

	/**
	 * @return the programTierCode
	 */
	public String getProgramTierCode() {
		return programTierCode;
	}

	/**
	 * @param programTierCode
	 *            the programTierCode to set
	 */
	public void setProgramTierCode(String programTierCode) {
		this.programTierCode = programTierCode;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public ParticipationStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ParticipationStatus status) {
		this.status = status;
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

	public CustomerLoyaltyTier convertTo(int mode) {
		CustomerLoyaltyTier bean = new CustomerLoyaltyTier();
		bean.setCsLoyaltyRef(csLoyaltyRef.getCsLoyaltyCode());
		bean.setCsLoyaltyTierCode(csLoyaltyTierCode);
		bean.setCustomerCode(customerCode);
		bean.setEndDate(endDate);
		bean.setId(id);
		bean.setProgramTierRef(programTierRef.getTierCode());
		bean.setRevisionControl(revisionControl);
		bean.setStartDate(startDate);
		bean.setStatus(status);
		bean.setTenantId(tenantId);

		return bean;
	}
}
