package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TxnType;

@Entity
@Table(name = "trl_cs_loyalty_txn", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "txn_code" }))
@NamedQueries({
		@NamedQuery(name = "CustomerLoyaltyTxnEntity.findByCustProgCode", query = "SELECT t FROM CustomerLoyaltyTxnEntity t WHERE t.customerCode = :customerCode and t.txnDate between :toDate and :fromDate and t.tenantId = :tenant "),
		@NamedQuery(name = "CustomerLoyaltyTxnEntity.findByCustomerCode", query = "SELECT t FROM CustomerLoyaltyTxnEntity t WHERE t.customerCode = :customerCode and t.tenantId = :tenant order by t.id desc")
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class CustomerLoyaltyTxnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "txn_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String txnCode;

	@ManyToOne
	@JoinColumn(name = "cs_loyalty_ref")
	@ContainedIn
	private CustomerLoyaltyEntity csLoyaltyRef;

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

	@Column(name = "txn_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private TxnType txnType;

	@Column(name = "description", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String description;

	@Column(name = "txn_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date txnDate;

	@Column(name = "value_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date valueDate;

	@Column(name = "tier_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String tierName;

	@Column(name = "txn_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal txnValue;

	@Column(name = "total_txn_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal totalTxnValue;

	@Column(name = "credit_points", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal creditPoints;

	@Column(name = "total_credit_points", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal totalCreditPoints;

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
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}


	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}


	/**
	 * @return the csLoyaltyRef
	 */
	public CustomerLoyaltyEntity getCsLoyaltyRef() {
		return csLoyaltyRef;
	}


	/**
	 * @param csLoyaltyRef the csLoyaltyRef to set
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
	 * @param csLoyaltyCode the csLoyaltyCode to set
	 */
	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}


	/**
	 * @return the txnType
	 */
	public TxnType getTxnType() {
		return txnType;
	}


	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(TxnType txnType) {
		this.txnType = txnType;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the txnDate
	 */
	public Date getTxnDate() {
		return txnDate;
	}


	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}


	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}


	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}


	/**
	 * @return the tierName
	 */
	public String getTierName() {
		return tierName;
	}


	/**
	 * @param tierName the tierName to set
	 */
	public void setTierName(String tierName) {
		this.tierName = tierName;
	}


	/**
	 * @return the txnValue
	 */
	public BigDecimal getTxnValue() {
		return txnValue;
	}


	/**
	 * @param txnValue the txnValue to set
	 */
	public void setTxnValue(BigDecimal txnValue) {
		this.txnValue = txnValue;
	}


	/**
	 * @return the totalTxnValue
	 */
	public BigDecimal getTotalTxnValue() {
		return totalTxnValue;
	}


	/**
	 * @param totalTxnValue the totalTxnValue to set
	 */
	public void setTotalTxnValue(BigDecimal totalTxnValue) {
		this.totalTxnValue = totalTxnValue;
	}


	/**
	 * @return the creditPoints
	 */
	public BigDecimal getCreditPoints() {
		return creditPoints;
	}


	/**
	 * @param creditPoints the creditPoints to set
	 */
	public void setCreditPoints(BigDecimal creditPoints) {
		this.creditPoints = creditPoints;
	}


	/**
	 * @return the totalCreditPoints
	 */
	public BigDecimal getTotalCreditPoints() {
		return totalCreditPoints;
	}


	/**
	 * @param totalCreditPoints the totalCreditPoints to set
	 */
	public void setTotalCreditPoints(BigDecimal totalCreditPoints) {
		this.totalCreditPoints = totalCreditPoints;
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

	public CustomerLoyaltyTxn convertTo(int mode) {
		CustomerLoyaltyTxn bean = new CustomerLoyaltyTxn();
		bean.setCreditPoints(creditPoints);
		bean.setCsLoyaltyRef(csLoyaltyRef.getCsLoyaltyCode());
		bean.setDescription(description);
		bean.setId(id);
		bean.setCsLoyaltyRef(csLoyaltyRef.getCustomerCode());
		bean.setRevisionControl(revisionControl);
		bean.setTenantId(tenantId);
		bean.setTotalCreditPoints(totalCreditPoints);
		bean.setTotalTxnValue(totalTxnValue);
		bean.setTxnCode(txnCode);
		bean.setTxnDate(txnDate);
		bean.setTxnType(txnType);
		bean.setTxnValue(totalTxnValue);
		bean.setValueDate(valueDate);

		return bean;
	}
}
