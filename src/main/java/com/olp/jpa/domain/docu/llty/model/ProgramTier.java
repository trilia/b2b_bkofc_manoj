package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.LifecycleStatus;

@XmlRootElement(name = "programTier", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tierCode", "programCode", "programRef", "tierName", "description", "tierSequence",
		"effectiveFrom", "effectiveUpto", "lifeCycleStatus", "tierPointFrom", "tierPointUpto", "spendConvRate",
		"spendConvFormula", "spendConvProcess", "revisionControl" })
public class ProgramTier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "programId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String tierCode;

	@XmlElement
	private String programCode;

	@XmlElement
	private String programRef;

	@XmlElement
	private String tierName;

	@XmlElement
	private String description;

	@XmlElement
	private int tierSequence;

	@XmlElement
	private Date effectiveFrom;

	@XmlElement
	private Date effectiveUpto;

	@XmlElement
	private int tierPointFrom;

	@XmlElement
	private int tierPointUpto;

	@XmlElement
	private float spendConvRate;

	@XmlElement
	private String spendConvFormula;

	@XmlElement
	private String spendConvProcess;

	@XmlElement
	private LifecycleStatus lifecycleStatus;

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
	public String getProgramRef() {
		return programRef;
	}

	/**
	 * @param programRef
	 *            the programRef to set
	 */
	public void setProgramRef(String programRef) {
		this.programRef = programRef;
	}

	/**
	 * @return the tierName
	 */
	public String getTierName() {
		return tierName;
	}

	/**
	 * @param tieName
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

	public ProgramTierEntity convertTo(int mode) {
		ProgramTierEntity bean = new ProgramTierEntity();
		bean.setDescription(description);
		bean.setEffectiveFrom(effectiveFrom);
		bean.setEffectiveUpto(effectiveUpto);
		bean.setId(id);
		bean.setLifecycleStatus(lifecycleStatus);
		bean.setProgramCode(programCode);
		LoyaltyProgramEntity loyaltyProgramEntity = new LoyaltyProgramEntity();
		loyaltyProgramEntity.setProgramCode(programRef);
		bean.setProgramRef(loyaltyProgramEntity);
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
