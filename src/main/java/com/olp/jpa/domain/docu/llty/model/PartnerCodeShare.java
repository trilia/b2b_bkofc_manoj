package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "partnerCodeShare", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "partnerValidityRef", "partnerCode", "effectiveFrom", "txnMinValue", "txnMaxValue",
		"txnMinNum", "txnMaxNum", "conversionRateIn", "conversionRateOut", "creditPointsIn", "creditPointsOut", "revisionControl" })
public class PartnerCodeShare implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "programId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String partnerValidityRef;

	@XmlElement
	private Date effectiveFrom;

	@XmlElement
	private BigDecimal txnMinValue;
	
	@XmlElement
	private BigDecimal txnMaxValue;
	
	@XmlElement
	private Integer txnMinNum;
	
	@XmlElement
	private Integer txnMaxNum;
	
	@XmlElement
	private BigDecimal conversionRateIn;
	
	@XmlElement
	private BigDecimal conversionRateOut;
	
	@XmlElement
	private BigDecimal creditPointsIn;
	
	@XmlElement
	private BigDecimal creditPointsOut;
	
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

	public String getPartnerValidityRef() {
		return partnerValidityRef;
	}

	public void setPartnerValidityRef(String partnerValidityRef) {
		this.partnerValidityRef = partnerValidityRef;
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
	
	public PartnerCodeShareEntity convertTo(int mode){
		PartnerCodeShareEntity partnerCodeShareEntity = new PartnerCodeShareEntity();
		partnerCodeShareEntity.setConversionRateIn(conversionRateIn);
		partnerCodeShareEntity.setConversionRateOut(conversionRateOut);
		partnerCodeShareEntity.setCreditPointsIn(creditPointsIn);
		partnerCodeShareEntity.setCreditPointsOut(creditPointsOut);
		partnerCodeShareEntity.setEffectiveFrom(effectiveFrom);
		partnerCodeShareEntity.setId(id);
		partnerCodeShareEntity.setPartnerCode(partnerValidityRef);
		PartnerValidityEntity partnerValidityEntity = new PartnerValidityEntity();
		partnerValidityEntity.setPartnerCode(partnerValidityRef);
		partnerCodeShareEntity.setPartnerValidityRef(partnerValidityEntity);
		partnerCodeShareEntity.setRevisionControl(revisionControl);
		partnerCodeShareEntity.setTenantId(tenantId);
		partnerCodeShareEntity.setTxnMaxNum(txnMaxNum);
		partnerCodeShareEntity.setTxnMaxValue(txnMaxValue);
		partnerCodeShareEntity.setTxnMinNum(txnMinNum);
		partnerCodeShareEntity.setTxnMinValue(txnMinValue);
		
		return partnerCodeShareEntity;
	}
}
