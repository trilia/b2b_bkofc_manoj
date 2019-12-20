package com.olp.jpa.domain.docu.be.model;

import java.io.Serializable;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.BeEnum.LogisticPartnerLocType;
import com.olp.jpa.domain.docu.org.model.LocationEntity;

@Entity
@Table(name = "trl_logistic_partner_loc")
@NamedQueries({
		@NamedQuery(name = "LogisticPartnerLocEntity.findByLocationCode", query = "SELECT t FROM LogisticPartnerLocEntity t WHERE t.locationCode = :locationCode and t.partnerCode = :partnerCode") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class LogisticPartnerLocEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_part_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "loc_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String locationCode;

	@Column(name = "partner_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerCode;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "partner_ref")
	@ContainedIn
	private LogisticPartnerEntity partnerRef;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "location_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LogisticPartnerLocType locationType;
	
	@OneToOne(cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private LocationEntity location;

	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStatus lifeCycleStatus;
	
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
	 * @return the partnerRef
	 */
	public LogisticPartnerEntity getPartnerRef() {
		return partnerRef;
	}

	/**
	 * @param partnerRef the partnerRef to set
	 */
	public void setPartnerRef(LogisticPartnerEntity partnerRef) {
		this.partnerRef = partnerRef;
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
	public LocationEntity getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationEntity location) {
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

	public LogisticPartnerLoc convertTo(int mode){
		LogisticPartnerLoc bean = new LogisticPartnerLoc();
		bean.setId(id);
		bean.setLifeCycleStatus(lifeCycleStatus);
		if(location != null){
			bean.setLocation(location.convertTo(0));
		}
		
		bean.setLocationCode(locationCode);
		bean.setLocationType(locationType);
		bean.setPartnerCode(partnerCode);
		bean.setRevisionControl(revisionControl);		
		return bean;
	}
	
}
