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
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;

@XmlRootElement(name = "customerLoyaltyTie", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "csLoyaltyTierCode", "csLoyaltyRef", "customerCode", "programTierRef", "startDate",
		"endDate", "status", "revisionControl" })
public class CustomerLoyaltyTier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "csLoyaltyTierId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String csLoyaltyTierCode;

	@XmlElement
	private String csLoyaltyRef;

	@XmlElement
	private String customerCode;

	@XmlElement
	private String programTierRef;

	@XmlElement
	private Date startDate;

	@XmlElement
	private Date endDate;

	@XmlElement
	private ParticipationStatus status;

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
	 * @return the csLoyaltyTierCode
	 */
	public String getCsLoyaltyTierCode() {
		return csLoyaltyTierCode;
	}

	/**
	 * @param csLoyaltyTierCode
	 *            the csLoyaltyTierCode to set
	 */
	public void setCsLoyaltyTierCode(String csLoyaltyTierCode) {
		this.csLoyaltyTierCode = csLoyaltyTierCode;
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
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the programTierRef
	 */
	public String getProgramTierRef() {
		return programTierRef;
	}

	/**
	 * @param programTierRef
	 *            the programTierRef to set
	 */
	public void setProgramTierRef(String programTierRef) {
		this.programTierRef = programTierRef;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public ParticipationStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ParticipationStatus status) {
		this.status = status;
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

	public CustomerLoyaltyTierEntity convertTo(int mode) {
		CustomerLoyaltyTierEntity bean = new CustomerLoyaltyTierEntity();

		CustomerLoyaltyEntity customerLoyaltyEntity = new CustomerLoyaltyEntity();
		customerLoyaltyEntity.setCsLoyaltyCode(csLoyaltyRef);
		bean.setCsLoyaltyRef(customerLoyaltyEntity);
		bean.setCsLoyaltyTierCode(csLoyaltyTierCode);
		bean.setCustomerCode(customerCode);
		bean.setEndDate(endDate);
		bean.setId(id);
		ProgramTierEntity programTierEntity = new ProgramTierEntity();
		programTierEntity.setProgramCode(programTierRef);
		bean.setProgramTierRef(programTierEntity);
		bean.setRevisionControl(revisionControl);
		bean.setStartDate(startDate);
		bean.setStatus(status);
		bean.setTenantId(tenantId);

		return bean;
	}
}
