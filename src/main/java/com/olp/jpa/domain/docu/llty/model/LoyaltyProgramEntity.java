package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.Scope;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.SpendConversionAlgo;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierMoveType;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierReviewFrequency;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierValidity;

@Entity
@Table(name = "trl_loyalty_program", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"program_code" }))
@NamedQueries({
		@NamedQuery(name = "LoyaltyProgramEntity.findbyCategoryCode", query = "SELECT t FROM LoyaltyProgramEntity t WHERE t.programCode = :programCode and t.tenantId = :tenantId ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class LoyaltyProgramEntity implements Serializable {
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
	
	@Column(name = "program_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String programCode;
	
	@Column(name = "program_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String programName;
	
	@Column(name = "description", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String description;
	
	@Column(name = "program_scope", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private Scope programScope;
	
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
	
	@Column(name = "tier_move_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private TierMoveType tierMoveType;
	
	@Column(name = "tier_review_freq", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private TierReviewFrequency tierReviewFreq;
	
	@Column(name = "tier_validity", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private TierValidity tierValidity;
	
	@Column(name = "spend_conv_algo", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private SpendConversionAlgo spendConvAlgo;
	
	@OneToMany(mappedBy = "programRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<ProgramTierEntity> programTiers;
	
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
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
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
	 * @return the programScope
	 */
	public Scope getProgramScope() {
		return programScope;
	}

	/**
	 * @param programScope the programScope to set
	 */
	public void setProgramScope(Scope programScope) {
		this.programScope = programScope;
	}

	/**
	 * @return the effectiveFrom
	 */
	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	/**
	 * @param effectiveFrom the effectiveFrom to set
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
	 * @param effectiveUpto the effectiveUpto to set
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
	 * @param lifecycleStatus the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifecycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
	}

	/**
	 * @return the tierMoveType
	 */
	public TierMoveType getTierMoveType() {
		return tierMoveType;
	}

	/**
	 * @param tierMoveType the tierMoveType to set
	 */
	public void setTierMoveType(TierMoveType tierMoveType) {
		this.tierMoveType = tierMoveType;
	}

	/**
	 * @return the tierReviewFreq
	 */
	public TierReviewFrequency getTierReviewFreq() {
		return tierReviewFreq;
	}

	/**
	 * @param tierReviewFreq the tierReviewFreq to set
	 */
	public void setTierReviewFreq(TierReviewFrequency tierReviewFreq) {
		this.tierReviewFreq = tierReviewFreq;
	}

	/**
	 * @return the tierValidity
	 */
	public TierValidity getTierValidity() {
		return tierValidity;
	}

	/**
	 * @param tierValidity the tierValidity to set
	 */
	public void setTierValidity(TierValidity tierValidity) {
		this.tierValidity = tierValidity;
	}

	/**
	 * @return the spendConvAlgo
	 */
	public SpendConversionAlgo getSpendConvAlgo() {
		return spendConvAlgo;
	}

	/**
	 * @param spendConvAlgo the spendConvAlgo to set
	 */
	public void setSpendConvAlgo(SpendConversionAlgo spendConvAlgo) {
		this.spendConvAlgo = spendConvAlgo;
	}

	/**
	 * @return the programTiers
	 */
	public List<ProgramTierEntity> getProgramTiers() {
		return programTiers;
	}

	/**
	 * @param programTiers the programTiers to set
	 */
	public void setProgramTiers(List<ProgramTierEntity> programTiers) {
		this.programTiers = programTiers;
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
	
	public LoyaltyProgram convertTo(int mode){
		LoyaltyProgram bean = new LoyaltyProgram();
		bean.setId(this.getId());
		bean.setTenantId(tenantId);
		bean.setDescription(description);
		bean.setEffectiveFrom(effectiveFrom);
		bean.setEffectiveUpto(effectiveUpto);
		bean.setLifecycleStatus(lifecycleStatus);
		bean.setTierMoveType(tierMoveType);
		bean.setProgramCode(programCode);
		bean.setProgramName(programName);
		bean.setProgramScope(programScope);
		bean.setRevisionControl(revisionControl);
		bean.setSpendConvAlgo(spendConvAlgo);
		bean.setTierReviewFreq(tierReviewFreq);
		bean.setTierValidity(tierValidity);
		List<ProgramTier> listOfProgramTiers = new ArrayList<>();
		for(ProgramTierEntity programTierEntity : programTiers){
			listOfProgramTiers.add(programTierEntity.convertTo(0));
		}
		bean.setProgramTiers(listOfProgramTiers);
		
		return bean;
	}
	
	
	
}
