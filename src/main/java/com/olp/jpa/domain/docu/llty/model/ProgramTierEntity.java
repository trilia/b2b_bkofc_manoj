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
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;

@Entity
@Table(name = "trl_program_tier"/*, uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"tier_code" })*/)
@NamedQueries({
		@NamedQuery(name = "ProgramTierEntity.findByTierCode", query = "SELECT t FROM ProgramTierEntity t WHERE t.tierCode = :tierCode and t.programCode = :programCode and t.tenantId = :tenantId "),
		@NamedQuery(name = "ProgramTierEntity.findByTierSequence", query = "SELECT t FROM ProgramTierEntity t WHERE t.programCode = :programCode and t.tierSequence = :sequence and t.tenantId = :tenantId "),
		@NamedQuery(name = "ProgramTierEntity.findAllSequencesByProgramCode", query = "SELECT t FROM ProgramTierEntity t WHERE t.programCode = :programCode and t.tenantId = :tenantId order by t.tierPointFrom desc")		
})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class ProgramTierEntity implements Serializable {
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

	@Column(name = "tier_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String tierCode;

	@Column(name = "program_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String programCode;

	@ManyToOne
	@JoinColumn(name = "program_ref")
	@ContainedIn
	private LoyaltyProgramEntity programRef;

	@Column(name = "tier_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String tierName;

	@Column(name = "description", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String description;

	@Column(name = "tier_sequence", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private int tierSequence;

	@Column(name = "effective_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveFrom;

	@Column(name = "effective_upto", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveUpto;

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private LifecycleStatus lifecycleStatus;

	@Column(name = "tier_point_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private int tierPointFrom;

	@Column(name = "tier_point_upto", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private int tierPointUpto;

	@Column(name = "spend_conv_rate", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private float spendConvRate;

	@Column(name = "spend_conv_formula", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private String spendConvFormula;

	@Column(name = "spend_conv_process", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private String spendConvProcess;

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
	 * @return the tierCode
	 */
	public String getTierCode() {
		return tierCode;
	}

	/**
	 * @param tierCode
	 *            the tierCode to set
	 */
	public void setTierCode(String tierCode) {
		this.tierCode = tierCode;
	}

	/**
	 * @return the programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * @param programCode
	 *            the programCode to set
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * @return the programRef
	 */
	public LoyaltyProgramEntity getProgramRef() {
		return programRef;
	}

	/**
	 * @param programRef
	 *            the programRef to set
	 */
	public void setProgramRef(LoyaltyProgramEntity programRef) {
		this.programRef = programRef;
	}

	/**
	 * @return the tierName
	 */
	public String getTierName() {
		return tierName;
	}

	/**
	 * @param tierName
	 *            the tierName to set
	 */
	public void setTierName(String tierName) {
		this.tierName = tierName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tierSequence
	 */
	public int getTierSequence() {
		return tierSequence;
	}

	/**
	 * @param tierSequence
	 *            the tierSequence to set
	 */
	public void setTierSequence(int tierSequence) {
		this.tierSequence = tierSequence;
	}

	/**
	 * @return the effectiveFrom
	 */
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	/**
	 * @param effectiveFrom
	 *            the effectiveFrom to set
	 */
	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	/**
	 * @return the effectiveUpto
	 */
	public Date getEffectiveUpto() {
		return effectiveUpto;
	}

	/**
	 * @param effectiveUpto
	 *            the effectiveUpto to set
	 */
	public void setEffectiveUpto(Date effectiveUpto) {
		this.effectiveUpto = effectiveUpto;
	}

	/**
	 * @return the lifecycleStatus
	 */
	public LifecycleStatus getLifecycleStatus() {
		return lifecycleStatus;
	}

	/**
	 * @param lifecycleStatus
	 *            the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifecycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
	}

	/**
	 * @return the tierPointFrom
	 */
	public int getTierPointFrom() {
		return tierPointFrom;
	}

	/**
	 * @param tierPointFrom
	 *            the tierPointFrom to set
	 */
	public void setTierPointFrom(int tierPointFrom) {
		this.tierPointFrom = tierPointFrom;
	}

	/**
	 * @return the tierPointUpto
	 */
	public int getTierPointUpto() {
		return tierPointUpto;
	}

	/**
	 * @param tierPointUpto
	 *            the tierPointUpto to set
	 */
	public void setTierPointUpto(int tierPointUpto) {
		this.tierPointUpto = tierPointUpto;
	}

	/**
	 * @return the spendConvRate
	 */
	public float getSpendConvRate() {
		return spendConvRate;
	}

	/**
	 * @param spendConvRate
	 *            the spendConvRate to set
	 */
	public void setSpendConvRate(float spendConvRate) {
		this.spendConvRate = spendConvRate;
	}

	/**
	 * @return the spendConvFormula
	 */
	public String getSpendConvFormula() {
		return spendConvFormula;
	}

	/**
	 * @param spendConvFormula
	 *            the spendConvFormula to set
	 */
	public void setSpendConvFormula(String spendConvFormula) {
		this.spendConvFormula = spendConvFormula;
	}

	/**
	 * @return the spendConvProcess
	 */
	public String getSpendConvProcess() {
		return spendConvProcess;
	}

	/**
	 * @param spendConvProcess
	 *            the spendConvProcess to set
	 */
	public void setSpendConvProcess(String spendConvProcess) {
		this.spendConvProcess = spendConvProcess;
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

	public ProgramTier convertTo(int mode) {
		ProgramTier bean = new ProgramTier();
		bean.setDescription(description);
		bean.setEffectiveFrom(effectiveFrom);
		bean.setEffectiveUpto(effectiveUpto);
		bean.setId(id);
		bean.setLifecycleStatus(lifecycleStatus);
		bean.setProgramCode(programCode);
		bean.setProgramRef(programRef.getProgramCode());
		bean.setRevisionControl(revisionControl);
		bean.setSpendConvFormula(spendConvFormula);
		bean.setSpendConvProcess(spendConvProcess);
		bean.setSpendConvRate(spendConvRate);
		bean.setTenantId(tenantId);
		bean.setTierCode(tierCode);
		bean.setTierName(tierName);
		bean.setTierPointFrom(tierPointFrom);
		bean.setTierPointUpto(tierPointUpto);
		bean.setTierSequence(tierSequence);
		return bean;
	}
}
