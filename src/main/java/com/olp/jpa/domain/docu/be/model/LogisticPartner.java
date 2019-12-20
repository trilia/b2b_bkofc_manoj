package com.olp.jpa.domain.docu.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.ContactEntity;

@XmlRootElement(name = "logisticPartner", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "partnerCode", "partnerName", "primaryContactRef", "legalInfo", "lifecycleStatus",
		"revisionControl", "partnerLocations", "wfStatus" })
public class LogisticPartner implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "logPartnerId")
	private Long id;

	@XmlElement
	private String partnerCode;

	@XmlElement
	private String partnerName;

	@XmlElement
	private ContactEntity primaryContactRef;

	@XmlElement
	private List<LegalInfoBean> legalInfo;

	@XmlElement
	private LifeCycleStatus lifeCycleStatus;

	private RevisionControlBean revisionControl;

	@XmlElement
	private List<LogisticPartnerLoc> partnerLocations;

	@XmlElement
	private Set<LgstPtnrWfStatusEntity> wfStatus;

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
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode
	 *            the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName
	 *            the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the primaryContactRef
	 */
	public ContactEntity getPrimaryContactRef() {
		return primaryContactRef;
	}

	/**
	 * @param primaryContactRef
	 *            the primaryContactRef to set
	 */
	public void setPrimaryContactRef(ContactEntity primaryContactRef) {
		this.primaryContactRef = primaryContactRef;
	}

	/**
	 * @return the legalInfo
	 */
	public List<LegalInfoBean> getLegalInfo() {
		return legalInfo;
	}

	/**
	 * @param legalInfo
	 *            the legalInfo to set
	 */
	public void setLegalInfo(List<LegalInfoBean> legalInfo) {
		this.legalInfo = legalInfo;
	}

	/**
	 * @return the lifeCycleStatus
	 */
	public LifeCycleStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	/**
	 * @param lifeCycleStatus
	 *            the lifeCycleStatus to set
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	/**
	 * @return the partnerLocations
	 */
	public List<LogisticPartnerLoc> getPartnerLocations() {
		return partnerLocations;
	}

	/**
	 * @param partnerLocations
	 *            the partnerLocations to set
	 */
	public void setPartnerLocations(List<LogisticPartnerLoc> partnerLocations) {
		this.partnerLocations = partnerLocations;
	}

	/**
	 * @return the wfStatus
	 */
	public Set<LgstPtnrWfStatusEntity> getWfStatus() {
		return wfStatus;
	}

	/**
	 * @param wfStatus
	 *            the wfStatus to set
	 */
	public void setWfStatus(Set<LgstPtnrWfStatusEntity> wfStatus) {
		this.wfStatus = wfStatus;
	}

	public LogisticPartnerEntity convertTo(int mode) {
		LogisticPartnerEntity bean = new LogisticPartnerEntity();
		bean.setId(id);
		bean.setLegalInfo(legalInfo);
		bean.setLifeCycleStatus(lifeCycleStatus);
		bean.setPartnerCode(partnerCode);
		List<LogisticPartnerLocEntity> partnerLocationsList = new ArrayList<>();
		for(LogisticPartnerLoc logisticPartnerLoc:partnerLocations ){
			LogisticPartnerLocEntity partnerLoc = logisticPartnerLoc.convertTo(0);
			partnerLoc.setPartnerCode(partnerCode);
			partnerLocationsList.add(partnerLoc);
		}

		bean.setPartnerLocations(partnerLocationsList);
		
		bean.setPartnerName(partnerName);
		bean.setPrimaryContactRef(primaryContactRef);
		bean.setRevisionControl(this.revisionControl);
		bean.setWfStatus(wfStatus);
		return bean;
	}
}
