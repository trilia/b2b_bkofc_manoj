package com.olp.jpa.domain.docu.be.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.BeEnum.LogisticPartnerLocType;
import com.olp.jpa.domain.docu.org.model.Location;
import com.olp.jpa.domain.docu.org.model.LocationEntity;

@XmlRootElement(name = "logisticPartner", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "locationCode", "partnerCode", "locationType", "location", "lifecycleStatus",
		"revisionControl"})
public class LogisticPartnerLoc implements Serializable{

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "logPartnerLocId")
	private Long id;

	@XmlElement
	private String locationCode;

	@XmlElement
	private String partnerCode;

	@XmlElement
	private LogisticPartnerLocType locationType;
	
	@XmlElement
	private Location location;

	@XmlElement
	private LifeCycleStatus lifeCycleStatus;

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
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the locationType
	 */
	public LogisticPartnerLocType getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(LogisticPartnerLocType locationType) {
		this.locationType = locationType;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the lifeCycleStatus
	 */
	public LifeCycleStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	/**
	 * @param lifeCycleStatus the lifeCycleStatus to set
	 */
	public void setLifeCycleStatus(LifeCycleStatus lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
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

	public LogisticPartnerLocEntity convertTo(int mode){
		LogisticPartnerLocEntity bean = new LogisticPartnerLocEntity();
		bean.setLifeCycleStatus(lifeCycleStatus);
		bean.setId(id);
		if(location != null){
			LocationEntity locationEntity = location.convertTo(0);
			bean.setLocation(locationEntity);
		}

		bean.setLocationCode(locationCode);
		bean.setLocationType(locationType);		
		bean.setRevisionControl(revisionControl);
		
		return bean;
	}

}
