package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;

@Entity
@Table(name = "trl_customer_loyalty", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"csLoyaltyCode" }))
@NamedQueries({
		@NamedQuery(name = "CustomerLoyaltyEntity.findByCustProgCode", query = "SELECT t FROM CustomerLoyaltyEntity t WHERE t.customerCode = :customerCode and t.programCode = :programCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class CustomerLoyaltyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cs_loyalty_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@Column(name = "cs_loyalty_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String csLoyaltyCode;
	
	@ManyToOne
	@JoinColumn(name = "customer_ref")
	@ContainedIn
	private CustomerEntity customerRef;
	
	@Column(name = "customer_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String customerCode;
	
	@ManyToOne
	@JoinColumn(name = "program_ref")
	@ContainedIn
	private LoyaltyProgramEntity programRef;
	
	@Column(name = "program_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String programCode;

	@Column(name = "start_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date startDate;
	
	@Column(name = "end_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date endDate;
	
	@Column(name = "status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private ParticipationStatus status;
	
	@Column(name = "total_credit", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal totalCredit;
	
	@Column(name = "redeemed_credit", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal redeemedCredit;
	
	@Column(name = "expired_credit", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal expiredCredit;
	
	@Column(name = "active_credit", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal activeCredit;
	
	@OneToMany(mappedBy = "csLoyaltyRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<CustomerLoyaltyTierEntity> csLoyaltyTiers;
	
	@OneToMany(mappedBy = "csLoyaltyRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<CustomerLoyaltyTxnEntity> csLoyaltyTxns;
	
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
	 * @return the csLoyaltyCode
	 */
	public String getCsLoyaltyCode() {
		return csLoyaltyCode;
	}

	/**
	 * @param csLoyaltyCode the csLoyaltyCode to set
	 */
	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}

	/**
	 * @return the customerRef
	 */
	public CustomerEntity getCustomerRef() {
		return customerRef;
	}

	/**
	 * @param customerRef the customerRef to set
	 */
	public void setCustomerRef(CustomerEntity customerRef) {
		this.customerRef = customerRef;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the programRef
	 */
	public LoyaltyProgramEntity getProgramRef() {
		return programRef;
	}

	/**
	 * @param programRef the programRef to set
	 */
	public void setProgramRef(LoyaltyProgramEntity programRef) {
		this.programRef = programRef;
	}

	/**
	 * @return the programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * @param programCode the programCode to set
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
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
	 * @param status the status to set
	 */
	public void setStatus(ParticipationStatus status) {
		this.status = status;
	}

	/**
	 * @return the totalCredit
	 */
	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	/**
	 * @param totalCredit the totalCredit to set
	 */
	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	/**
	 * @return the redeemedCredit
	 */
	public BigDecimal getRedeemedCredit() {
		return redeemedCredit;
	}

	/**
	 * @param redeemedCredit the redeemedCredit to set
	 */
	public void setRedeemedCredit(BigDecimal redeemedCredit) {
		this.redeemedCredit = redeemedCredit;
	}

	/**
	 * @return the expiredCredit
	 */
	public BigDecimal getExpiredCredit() {
		return expiredCredit;
	}

	/**
	 * @param expiredCredit the expiredCredit to set
	 */
	public void setExpiredCredit(BigDecimal expiredCredit) {
		this.expiredCredit = expiredCredit;
	}

	/**
	 * @return the activeCredit
	 */
	public BigDecimal getActiveCredit() {
		return activeCredit;
	}

	/**
	 * @param activeCredit the activeCredit to set
	 */
	public void setActiveCredit(BigDecimal activeCredit) {
		this.activeCredit = activeCredit;
	}

	/**
	 * @return the csLoyaltyTiers
	 */
	public List<CustomerLoyaltyTierEntity> getCsLoyaltyTiers() {
		return csLoyaltyTiers;
	}

	/**
	 * @param csLoyaltyTiers the csLoyaltyTiers to set
	 */
	public void setCsLoyaltyTiers(List<CustomerLoyaltyTierEntity> csLoyaltyTiers) {
		this.csLoyaltyTiers = csLoyaltyTiers;
	}

	/**
	 * @return the csLoyaltyTxns
	 */
	public Set<CustomerLoyaltyTxnEntity> getCsLoyaltyTxns() {
		return csLoyaltyTxns;
	}

	/**
	 * @param csLoyaltyTxns the csLoyaltyTxns to set
	 */
	public void setCsLoyaltyTxns(Set<CustomerLoyaltyTxnEntity> csLoyaltyTxns) {
		this.csLoyaltyTxns = csLoyaltyTxns;
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

	public CustomerLoyalty convertTo(int mode){
		CustomerLoyalty bean = new CustomerLoyalty();
		bean.setActiveCredit(activeCredit);
		bean.setCsLoyaltyCode(csLoyaltyCode);
		
		List<CustomerLoyaltyTier> customerLoyaltyTiers = new ArrayList<>();
		for(CustomerLoyaltyTierEntity customerLoyaltyTierEntity: csLoyaltyTiers ){
			//customerLoyaltyTiers.add(customerLoyaltyTierEntity.convertTo(0));
		}
		bean.setCsLoyaltyTiers(customerLoyaltyTiers);
		Set<CustomerLoyaltyTxn> customerLoyaltyTxns = new HashSet<>();
		for(CustomerLoyaltyTxn customerLoyaltyTxn : customerLoyaltyTxns){
			//customerLoyaltyTxns.add(customerLoyaltyTxn.convertTo(0));
		}
		bean.setCsLoyaltyTxns(customerLoyaltyTxns);
		bean.setCustomerRef(customerRef.getCustomerCode());
		bean.setEndDate(endDate);
		bean.setExpiredCredit(expiredCredit);
		bean.setId(id);
		bean.setProgrameRef(programRef.getProgramCode());
		bean.setRedeemedCredit(redeemedCredit);
		bean.setRevisionControl(revisionControl);
		bean.setStartDate(startDate);
		bean.setStatus(status);
		bean.setTenantId(tenantId);
		bean.setTotalCredit(totalCredit);
		
		return bean;
	}
}
