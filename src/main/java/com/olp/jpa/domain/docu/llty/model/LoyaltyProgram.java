package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.Scope;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.SpendConversionAlgo;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierMoveType;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierReviewFrequency;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TierValidity;

@XmlRootElement(name = "loyaltyProgram", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "programCode", "programName", "description", "effectiveFrom", "effectiveUpTo",
		"lifeCycleStatus", "tierMoveType", "tierReviewFreq", "tierValidity", "spendConvAlgo", "programTiers",
		"revisionControl" })
public class LoyaltyProgram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "programId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String programCode;

	@XmlElement
	private String programName;

	@XmlElement
	private String description;

	@XmlElement
	private Scope programScope;

	@XmlElement
	private Date effectiveFrom;

	@XmlElement
	private Date effectiveUpto;

	@XmlElement
	private LifecycleStatus lifecycleStatus;

	@XmlElement
	private TierMoveType tierMoveType;

	@XmlElement
	private TierReviewFrequency tierReviewFreq;

	@XmlElement
	private TierValidity tierValidity;

	@XmlElement
	private SpendConversionAlgo spendConvAlgo;

	@XmlElement
	private List<ProgramTier> programTiers;

	private RevisionControlBean revisionControl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @return the programScope
	 */
	public Scope getProgramScope() {
		return programScope;
	}

	/**
	 * @param programScope
	 *            the programScope to set
	 */
	public void setProgramScope(Scope programScope) {
		this.programScope = programScope;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName
	 *            the programName to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the tierMoveType
	 */
	public TierMoveType getTierMoveType() {
		return tierMoveType;
	}

	/**
	 * @param tierMoveType
	 *            the tierMoveType to set
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
	 * @param tierReviewFreq
	 *            the tierReviewFreq to set
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
	 * @param tierValidity
	 *            the tierValidity to set
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
	 * @param spendConvAlgo
	 *            the spendConvAlgo to set
	 */
	public void setSpendConvAlgo(SpendConversionAlgo spendConvAlgo) {
		this.spendConvAlgo = spendConvAlgo;
	}

	/**
	 * @return the programTiers
	 */
	public List<ProgramTier> getProgramTiers() {
		return programTiers;
	}

	/**
	 * @param programTiers
	 *            the programTiers to set
	 */
	public void setProgramTiers(List<ProgramTier> programTiers) {
		this.programTiers = programTiers;
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

	public LoyaltyProgramEntity convertTo(int mode) {
		LoyaltyProgramEntity bean = new LoyaltyProgramEntity();
		bean.setDescription(description);
		bean.setEffectiveFrom(effectiveFrom);
		bean.setEffectiveUpto(effectiveUpto);
		bean.setId(id);
		bean.setLifecycleStatus(lifecycleStatus);
		bean.setProgramCode(programCode);
		bean.setProgramName(programName);
		bean.setProgramScope(programScope);
		List<ProgramTierEntity> listOfProgramTierEntity = new ArrayList<>();
		for (ProgramTier programTier : programTiers) {
			ProgramTierEntity programTierEntity = programTier.convertTo(0);
			listOfProgramTierEntity.add(programTierEntity);
		}
		bean.setProgramTiers(listOfProgramTierEntity);
		bean.setRevisionControl(revisionControl);
		bean.setSpendConvAlgo(spendConvAlgo);
		bean.setTenantId(tenantId);
		bean.setTierMoveType(tierMoveType);
		bean.setTierReviewFreq(tierReviewFreq);
		bean.setTierValidity(tierValidity);
		return bean;
	}
}
