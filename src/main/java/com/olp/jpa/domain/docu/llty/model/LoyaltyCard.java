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

@XmlRootElement(name = "loyaltyCard", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "csLoyaltyRef", "customerCode", "effectiveFrom", "effectiveUpto", "cardNumber",
		"cardNumber2", "cardSource2", "cardNumber3", "cardSource3", "cardNumber4", "cardSource4", "cardNumber5",
		"cardSource5", "revisionControl" })
public class LoyaltyCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "loyaltyCardId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String csLoyaltyRef;

	@XmlElement
	private String customerCode;

	@XmlElement
	private Date effectiveFrom;

	@XmlElement
	private Date effectiveUpto;

	@XmlElement
	private String cardNumber;

	@XmlElement
	private String cardNumber2;

	@XmlElement
	private String cardSource2;

	@XmlElement
	private String cardNumber3;

	@XmlElement
	private String cardSource3;

	@XmlElement
	private String cardNumber4;

	@XmlElement
	private String cardSource4;

	@XmlElement
	private String cardNumber5;

	@XmlElement
	private String cardSource5;

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

	public String getCsLoyaltyRef() {
		return csLoyaltyRef;
	}

	public void setCsLoyaltyRef(String csLoyaltyRef) {
		this.csLoyaltyRef = csLoyaltyRef;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveUpto() {
		return effectiveUpto;
	}

	public void setEffectiveUpto(Date effectiveUpto) {
		this.effectiveUpto = effectiveUpto;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardNumber2() {
		return cardNumber2;
	}

	public void setCardNumber2(String cardNumber2) {
		this.cardNumber2 = cardNumber2;
	}

	public String getCardSource2() {
		return cardSource2;
	}

	public void setCardSource2(String cardSource2) {
		this.cardSource2 = cardSource2;
	}

	public String getCardNumber3() {
		return cardNumber3;
	}

	public void setCardNumber3(String cardNumber3) {
		this.cardNumber3 = cardNumber3;
	}

	public String getCardSource3() {
		return cardSource3;
	}

	public void setCardSource3(String cardSource3) {
		this.cardSource3 = cardSource3;
	}

	public String getCardNumber4() {
		return cardNumber4;
	}

	public void setCardNumber4(String cardNumber4) {
		this.cardNumber4 = cardNumber4;
	}

	public String getCardSource4() {
		return cardSource4;
	}

	public void setCardSource4(String cardSource4) {
		this.cardSource4 = cardSource4;
	}

	public String getCardNumber5() {
		return cardNumber5;
	}

	public void setCardNumber5(String cardNumber5) {
		this.cardNumber5 = cardNumber5;
	}

	public String getCardSource5() {
		return cardSource5;
	}

	public void setCardSource5(String cardSource5) {
		this.cardSource5 = cardSource5;
	}

	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public LoyaltyCardEntity covertTo(int mode) {
		LoyaltyCardEntity loyaltyCardEntity = new LoyaltyCardEntity();

		loyaltyCardEntity.setCardNumber(cardNumber);
		loyaltyCardEntity.setCardNumber2(cardNumber2);
		loyaltyCardEntity.setCardNumber3(cardNumber3);
		loyaltyCardEntity.setCardNumber4(cardNumber4);
		loyaltyCardEntity.setCardNumber5(cardNumber5);
		loyaltyCardEntity.setCardSource2(cardSource2);
		loyaltyCardEntity.setCardSource3(cardSource3);
		loyaltyCardEntity.setCardSource4(cardSource4);
		loyaltyCardEntity.setCardSource5(cardSource5);
		CustomerLoyaltyEntity customerLoyaltyEntity = new CustomerLoyaltyEntity();
		customerLoyaltyEntity.setCsLoyaltyCode(csLoyaltyRef);
		loyaltyCardEntity.setCsLoyaltyRef(customerLoyaltyEntity);
		loyaltyCardEntity.setCustomerCode(customerCode);
		loyaltyCardEntity.setEffectiveFrom(effectiveFrom);
		loyaltyCardEntity.setEffectiveUpto(effectiveUpto);
		loyaltyCardEntity.setId(id);
		loyaltyCardEntity.setRevisionControl(revisionControl);
		loyaltyCardEntity.setTenantId(tenantId);
		return loyaltyCardEntity;
	}
}
