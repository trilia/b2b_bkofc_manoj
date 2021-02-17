package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.LocationEntity;

@XmlRootElement(name = "loyaltyPartner", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "partnerCode", "partnerName", "locationRef", "locationCode", 
		"partnerValidities","revisionControl" })
public class LoyaltyPartner implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "partnerId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String partnerCode;

	@XmlElement
	private String partnerName;
	
	@XmlElement
	private String locationRef;
	
	@XmlElement
	private String locationCode;
	
	@XmlElement
	private List<PartnerValidity> partnerValidities;
	
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

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getLocationRef() {
		return locationRef;
	}

	public void setLocationRef(String locationRef) {
		this.locationRef = locationRef;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public List<PartnerValidity> getPartnerValidities() {
		return partnerValidities;
	}

	public void setPartnerValidities(List<PartnerValidity> partnerValidities) {
		this.partnerValidities = partnerValidities;
	}

	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public LoyaltyPartnerEntity convertTo(int mode){
		LoyaltyPartnerEntity loyaltyPartnerEntity = new LoyaltyPartnerEntity();
		loyaltyPartnerEntity.setId(id);
		loyaltyPartnerEntity.setLocationCode(locationCode);
		LocationEntity locationEntity = new LocationEntity();
				locationEntity.setZipCode(locationCode);//check 	
		loyaltyPartnerEntity.setLocationRef(locationEntity);
		loyaltyPartnerEntity.setPartnerCode(partnerCode);
		List<PartnerValidityEntity> listOfPartnetValidityEntity = new ArrayList<PartnerValidityEntity>();
		for(PartnerValidity partnerValidity:partnerValidities ){
			listOfPartnetValidityEntity.add(partnerValidity.convertTo(0));
		}
		loyaltyPartnerEntity.setPartnerValidities(listOfPartnetValidityEntity);
		loyaltyPartnerEntity.setRevisionControl(revisionControl);
		loyaltyPartnerEntity.setTenantId(tenantId);
		
		return loyaltyPartnerEntity;
	}
}
