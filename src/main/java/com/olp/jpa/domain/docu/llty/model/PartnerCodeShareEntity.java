package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

@Entity
@Table(name = "trl_loyalty_code_share", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"partner_code" }))
@NamedQueries({
		@NamedQuery(name = "PartnerCodeShareEntity.findByEffectiveDate", query = "SELECT t FROM PartnerCodeShareEntity t WHERE t.partnerCode = :partnerCode and t.effectiveFrom > :date and t.tenantId = :tenant "),
		@NamedQuery(name = "PartnerCodeShareEntity.findByPartnerCode", query = "SELECT t FROM PartnerCodeShareEntity t WHERE t.partnerCode = :partnerCode and t.tenantId = :tenant")		
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class PartnerCodeShareEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "program_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name = "partner_validity_ref")
	@ContainedIn
	private PartnerValidityEntity partnerValidityRef;
	
	@Column(name = "cs_loyalty_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String csLoyaltyCode;
	
	@Column(name = "partner_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerCode;
	
	@Column(name = "effective_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveFrom;
	
	@Column(name = "txn_min_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal txnMinValue;
	
	@Column(name = "txn_max_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal txnMaxValue;
	
	@Column(name = "txn_min_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer txnMinNum;
	
	@Column(name = "txn_max_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer txnMaxNum;
	
	@Column(name = "conversion_rate_in", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal conversionRateIn;
	
	@Column(name = "conversion_rate_out", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal conversionRateOut;
	
	@Column(name = "credit_oints_in", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal creditPointsIn;
	
	@Column(name = "credit_points_out", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal creditPointsOut;
	
	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public PartnerValidityEntity getPartnerValidityRef() {
		return partnerValidityRef;
	}

	public void setPartnerValidityRef(PartnerValidityEntity partnerValidityRef) {
		this.partnerValidityRef = partnerValidityRef;
	}

	public String getCsLoyaltyCode() {
		return csLoyaltyCode;
	}

	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public BigDecimal getTxnMinValue() {
		return txnMinValue;
	}

	public void setTxnMinValue(BigDecimal txnMinValue) {
		this.txnMinValue = txnMinValue;
	}

	public BigDecimal getTxnMaxValue() {
		return txnMaxValue;
	}

	public void setTxnMaxValue(BigDecimal txnMaxValue) {
		this.txnMaxValue = txnMaxValue;
	}

	public Integer getTxnMinNum() {
		return txnMinNum;
	}

	public void setTxnMinNum(Integer txnMinNum) {
		this.txnMinNum = txnMinNum;
	}

	public Integer getTxnMaxNum() {
		return txnMaxNum;
	}

	public void setTxnMaxNum(Integer txnMaxNum) {
		this.txnMaxNum = txnMaxNum;
	}

	public BigDecimal getConversionRateIn() {
		return conversionRateIn;
	}

	public void setConversionRateIn(BigDecimal conversionRateIn) {
		this.conversionRateIn = conversionRateIn;
	}

	public BigDecimal getConversionRateOut() {
		return conversionRateOut;
	}

	public void setConversionRateOut(BigDecimal conversionRateOut) {
		this.conversionRateOut = conversionRateOut;
	}

	public BigDecimal getCreditPointsIn() {
		return creditPointsIn;
	}

	public void setCreditPointsIn(BigDecimal creditPointsIn) {
		this.creditPointsIn = creditPointsIn;
	}

	public BigDecimal getCreditPointsOut() {
		return creditPointsOut;
	}

	public void setCreditPointsOut(BigDecimal creditPointsOut) {
		this.creditPointsOut = creditPointsOut;
	}

	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public PartnerCodeShare convertTo(int mode){
		PartnerCodeShare partnerCodeShare = new PartnerCodeShare();
		partnerCodeShare.setConversionRateIn(conversionRateIn);
		partnerCodeShare.setConversionRateOut(conversionRateOut);
		partnerCodeShare.setCreditPointsIn(creditPointsIn);
		partnerCodeShare.setCreditPointsOut(creditPointsOut);
		partnerCodeShare.setEffectiveFrom(effectiveFrom);
		partnerCodeShare.setId(id);
		partnerCodeShare.setPartnerValidityRef(partnerValidityRef.getPartnerCode());
		partnerCodeShare.setRevisionControl(revisionControl);
		partnerCodeShare.setTenantId(tenantId);
		partnerCodeShare.setTxnMaxNum(txnMaxNum);
		partnerCodeShare.setTxnMaxValue(txnMaxValue);
		partnerCodeShare.setTxnMinNum(txnMinNum);
		partnerCodeShare.setTxnMinValue(txnMinValue);
		
		return partnerCodeShare;
	}
}
