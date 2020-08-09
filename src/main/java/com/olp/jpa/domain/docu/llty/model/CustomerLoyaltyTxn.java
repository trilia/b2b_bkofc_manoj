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
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.TxnType;

@XmlRootElement(name = "customerLoyaltyTxn", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "txnCode", "csLoyaltyRef", "txnType", "description", "txnDate", "valueDate", "txnValue",
		"totalTxnValue", "creditPoints", "totalCreditPoints", "revisionControl" })
public class CustomerLoyaltyTxn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "csLoyaltyTierId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String txnCode;

	@XmlElement
	private String csLoyaltyRef;
	
	@XmlElement
	private String customerRef;

	@XmlElement
	private TxnType txnType;

	@XmlElement
	private String description;

	@XmlElement
	private Date txnDate;

	@XmlElement
	private Date valueDate;

	@XmlElement
	private BigDecimal txnValue;

	@XmlElement
	private BigDecimal totalTxnValue;

	@XmlElement
	private BigDecimal creditPoints;

	@XmlElement
	private BigDecimal totalCreditPoints;

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
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}

	/**
	 * @param txnCode
	 *            the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	/**
	 * @return the csLoyaltyRef
	 */
	public String getCsLoyaltyRef() {
		return csLoyaltyRef;
	}

	/**
	 * @param csLoyaltyRef
	 *            the csLoyaltyRef to set
	 */
	public void setCsLoyaltyRef(String csLoyaltyRef) {
		this.csLoyaltyRef = csLoyaltyRef;
	}

	/**
	 * @return the txnType
	 */
	public TxnType getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
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
	 * @param description
	 *            the description to set
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
	 * @param txnDate
	 *            the txnDate to set
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
	 * @param valueDate
	 *            the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * @return the txnValue
	 */
	public BigDecimal getTxnValue() {
		return txnValue;
	}

	/**
	 * @param txnValue
	 *            the txnValue to set
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
	 * @param totalTxnValue
	 *            the totalTxnValue to set
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
	 * @param creditPoints
	 *            the creditPoints to set
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
	 * @param totalCreditPoints
	 *            the totalCreditPoints to set
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	/**
	 * @return the customerRef
	 */
	public String getCustomerRef() {
		return customerRef;
	}

	/**
	 * @param customerRef the customerRef to set
	 */
	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}

	public CustomerLoyaltyTxnEntity convertTo(int mode) {
		CustomerLoyaltyTxnEntity bean = new CustomerLoyaltyTxnEntity();
		bean.setCreditPoints(creditPoints);
		CustomerLoyaltyEntity customerLoyaltyEntity = new CustomerLoyaltyEntity();
		customerLoyaltyEntity.setCsLoyaltyCode(csLoyaltyRef);
		bean.setCsLoyaltyRef(customerLoyaltyEntity);
		bean.setDescription(description);	
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerCode(customerRef);
		bean.setCsLoyaltyRef(customerLoyaltyEntity);
		bean.setCustomerCode(customerRef);
		bean.setId(id);
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
