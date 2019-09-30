package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerLineType;

@XmlRootElement(name = "ledgerline", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "ledgerName", "ledgerName", "accountRef","lineType", 
		"lineAmount", "lineDesc"})
public class LedgerLine implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "ledgerLineId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String ledgerName;

	@XmlElement
	private Integer lineNum;

	@XmlElement
	private String accountRef;

	@XmlElement
	private LedgerLineType lineType;

	@XmlElement
	private BigDecimal lineAmount;
	
	@XmlElement
	private String lineDesc;

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
	 * @return the ledgerName
	 */
	public String getLedgerName() {
		return ledgerName;
	}

	/**
	 * @param ledgerName the ledgerName to set
	 */
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	/**
	 * @return the lineNum
	 */
	public Integer getLineNum() {
		return lineNum;
	}

	/**
	 * @param lineNum the lineNum to set
	 */
	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * @return the accountRef
	 */
	public String getAccountRef() {
		return accountRef;
	}

	/**
	 * @param accountRef the accountRef to set
	 */
	public void setAccountRef(String accountRef) {
		this.accountRef = accountRef;
	}

	/**
	 * @return the lineType
	 */
	public LedgerLineType getLineType() {
		return lineType;
	}

	/**
	 * @param lineType the lineType to set
	 */
	public void setLineType(LedgerLineType lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return the lineAmount
	 */
	public BigDecimal getLineAmount() {
		return lineAmount;
	}

	/**
	 * @param lineAmount the lineAmount to set
	 */
	public void setLineAmount(BigDecimal lineAmount) {
		this.lineAmount = lineAmount;
	}

	/**
	 * @return the lineDesc
	 */
	public String getLineDesc() {
		return lineDesc;
	}

	/**
	 * @param lineDesc the lineDesc to set
	 */
	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}
	
	public LedgerLineEntity convertTo(int mode){
		LedgerLineEntity bean = new LedgerLineEntity();
		
		return bean;
	}
}
