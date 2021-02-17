package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "loyaltyCard", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "partnerRef", "effectiveFrom", "effectiveUpto" })
public class PartnerValidity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "programId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String partnerRef;

	@XmlElement
	private Date effectiveFrom;

	@XmlElement
	private Date effectiveUpto;

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

	public String getPartnerRef() {
		return partnerRef;
	}

	public void setPartnerRef(String partnerRef) {
		this.partnerRef = partnerRef;
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
	
	public PartnerValidityEntity convertTo(int mode){
		PartnerValidityEntity partnerValidityEntity = new PartnerValidityEntity();
		partnerValidityEntity.setEffectiveFrom(effectiveFrom);
		partnerValidityEntity.setEffectiveUpto(effectiveUpto);
		partnerValidityEntity.setId(id);
		partnerValidityEntity.setPartnerCode(partnerRef);
		LoyaltyPartnerEntity loyaltyPartnerEntity = new LoyaltyPartnerEntity();
		loyaltyPartnerEntity.setPartnerCode(partnerRef);
		partnerValidityEntity.setPartnerRef(loyaltyPartnerEntity);
		partnerValidityEntity.setTenantId(tenantId);
		
		return partnerValidityEntity;
	}


}
