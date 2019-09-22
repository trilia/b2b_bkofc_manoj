package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "chartOfAccounts", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "coaCode", "coaName", "useAccountNum",
		"numSegments", "segment1LovRef", "segment2LovRef", "segment3LovRef", 
		"segment4LovRef", "segment5LovRef", "lifeCycleStatus", "revisionControl" })
public class ChartOfAccounts implements Serializable{

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "chartOfAccId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String coaCode;
	
	@XmlElement
	private String coaName;
	
	@XmlElement
	private boolean useAccountNum;
	
	@XmlElement
	private int numSegments;
	
	@XmlElement
	private String segment1LovRef;
	
	@XmlElement
	private String segment2LovRef;
	
	@XmlElement
	private String segment3LovRef;
	
	@XmlElement
	private String segment4LovRef;
	
	@XmlElement
	private String segment5LovRef;
		
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
	 * @return the coaCode
	 */
	public String getCoaCode() {
		return coaCode;
	}

	/**
	 * @param coaCode the coaCode to set
	 */
	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	/**
	 * @return the coaName
	 */
	public String getCoaName() {
		return coaName;
	}

	/**
	 * @param coaName the coaName to set
	 */
	public void setCoaName(String coaName) {
		this.coaName = coaName;
	}

	/**
	 * @return the useAccountNum
	 */
	public boolean isUseAccountNum() {
		return useAccountNum;
	}

	/**
	 * @param useAccountNum the useAccountNum to set
	 */
	public void setUseAccountNum(boolean useAccountNum) {
		this.useAccountNum = useAccountNum;
	}

	/**
	 * @return the numSegments
	 */
	public int getNumSegments() {
		return numSegments;
	}

	/**
	 * @param numSegments the numSegments to set
	 */
	public void setNumSegments(int numSegments) {
		this.numSegments = numSegments;
	}

	/**
	 * @return the segment1LovRef
	 */
	public String getSegment1LovRef() {
		return segment1LovRef;
	}

	/**
	 * @param segment1LovRef the segment1LovRef to set
	 */
	public void setSegment1LovRef(String segment1LovRef) {
		this.segment1LovRef = segment1LovRef;
	}

	/**
	 * @return the segment2LovRef
	 */
	public String getSegment2LovRef() {
		return segment2LovRef;
	}

	/**
	 * @param segment2LovRef the segment2LovRef to set
	 */
	public void setSegment2LovRef(String segment2LovRef) {
		this.segment2LovRef = segment2LovRef;
	}

	/**
	 * @return the segment3LovRef
	 */
	public String getSegment3LovRef() {
		return segment3LovRef;
	}

	/**
	 * @param segment3LovRef the segment3LovRef to set
	 */
	public void setSegment3LovRef(String segment3LovRef) {
		this.segment3LovRef = segment3LovRef;
	}

	/**
	 * @return the segment4LovRef
	 */
	public String getSegment4LovRef() {
		return segment4LovRef;
	}

	/**
	 * @param segment4LovRef the segment4LovRef to set
	 */
	public void setSegment4LovRef(String segment4LovRef) {
		this.segment4LovRef = segment4LovRef;
	}

	/**
	 * @return the segment5LovRef
	 */
	public String getSegment5LovRef() {
		return segment5LovRef;
	}

	/**
	 * @param segment5LovRef the segment5LovRef to set
	 */
	public void setSegment5LovRef(String segment5LovRef) {
		this.segment5LovRef = segment5LovRef;
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
	
	public ChartOfAccountsEntity convertTo(int mode){
		ChartOfAccountsEntity bean = new ChartOfAccountsEntity();
		
		return bean;
	}
	
	
}
