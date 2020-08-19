package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.llty.model.LoyaltyEnums.ParticipationStatus;

@XmlRootElement(name = "customerLoyalty", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "csLoyaltyCode", "customerRef", "programeRef", "startDate", "endDate", "status",
		"totalCredit", "redeemedCredit", "expiredCredit", "activeCredit", "csLoyaltyTiers", "csLoyaltyTxns",
		"revisionControl" })
public class CustomerLoyalty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "csLoyaltyId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String csLoyaltyCode;

	@XmlElement
	private String customerRef;

	@XmlElement
	private String programRef;

	@XmlElement
	private Date startDate;

	@XmlElement
	private Date endDate;

	@XmlElement
	private ParticipationStatus status;

	@XmlElement
	private BigDecimal totalCredit;

	@XmlElement
	private BigDecimal redeemedCredit;

	@XmlElement
	private BigDecimal expiredCredit;

	@XmlElement
	private BigDecimal activeCredit;

	@XmlElement
	private List<CustomerLoyaltyTier> csLoyaltyTiers;

	@XmlElement
	private Set<CustomerLoyaltyTxn> csLoyaltyTxns;

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
	 * @return the csLoyaltyCode
	 */
	public String getCsLoyaltyCode() {
		return csLoyaltyCode;
	}

	/**
	 * @param csLoyaltyCode
	 *            the csLoyaltyCode to set
	 */
	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}

	/**
	 * @return the customerRef
	 */
	public String getCustomerRef() {
		return customerRef;
	}

	/**
	 * @param customerRef
	 *            the customerRef to set
	 */
	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}

	/**
	 * @return the programRef
	 */
	public String getProgramRef() {
		return programRef;
	}

	/**
	 * @param programRef
	 *            the programRef to set
	 */
	public void setProgramRef(String programRef) {
		this.programRef = programRef;
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
	 * @return the totalCredit
	 */
	public BigDecimal getTotalCredit() {
		return totalCredit;
	}

	/**
	 * @param totalCredit
	 *            the totalCredit to set
	 */
	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	/**
	 * @return the redeemedCredit
	 */
	public BigDecimal getRedeemedCredit() {
		return redeemedCredit;
	}

	/**
	 * @param redeemedCredit
	 *            the redeemedCredit to set
	 */
	public void setRedeemedCredit(BigDecimal redeemedCredit) {
		this.redeemedCredit = redeemedCredit;
	}

	/**
	 * @return the expiredCredit
	 */
	public BigDecimal getExpiredCredit() {
		return expiredCredit;
	}

	/**
	 * @param expiredCredit
	 *            the expiredCredit to set
	 */
	public void setExpiredCredit(BigDecimal expiredCredit) {
		this.expiredCredit = expiredCredit;
	}

	/**
	 * @return the activeCredit
	 */
	public BigDecimal getActiveCredit() {
		return activeCredit;
	}

	/**
	 * @param activeCredit
	 *            the activeCredit to set
	 */
	public void setActiveCredit(BigDecimal activeCredit) {
		this.activeCredit = activeCredit;
	}

	/**
	 * @return the csLoyaltyTiers
	 */
	public List<CustomerLoyaltyTier> getCsLoyaltyTiers() {
		return csLoyaltyTiers;
	}

	/**
	 * @param csLoyaltyTiers
	 *            the csLoyaltyTiers to set
	 */
	public void setCsLoyaltyTiers(List<CustomerLoyaltyTier> csLoyaltyTiers) {
		this.csLoyaltyTiers = csLoyaltyTiers;
	}

	/**
	 * @return the csLoyaltyTxns
	 */
	public Set<CustomerLoyaltyTxn> getCsLoyaltyTxns() {
		return csLoyaltyTxns;
	}

	/**
	 * @param csLoyaltyTxns
	 *            the csLoyaltyTxns to set
	 */
	public void setCsLoyaltyTxns(Set<CustomerLoyaltyTxn> csLoyaltyTxns) {
		this.csLoyaltyTxns = csLoyaltyTxns;
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

	public CustomerLoyaltyEntity convertTo(int mode) {
		CustomerLoyaltyEntity bean = new CustomerLoyaltyEntity();
		bean.setActiveCredit(activeCredit);
		bean.setCsLoyaltyCode(csLoyaltyCode);

		List<CustomerLoyaltyTierEntity> customerLoyaltyTiers = new ArrayList<>();
		for (CustomerLoyaltyTier customerLoyaltyTier : csLoyaltyTiers) {
			customerLoyaltyTiers.add(customerLoyaltyTier.convertTo(0));
		}
		bean.setCsLoyaltyTiers(customerLoyaltyTiers);

		Set<CustomerLoyaltyTxnEntity> customerLoyaltyTxns = new HashSet<>();
		for (CustomerLoyaltyTxn customerLoyaltyTxn : csLoyaltyTxns) {
			customerLoyaltyTxns.add(customerLoyaltyTxn.convertTo(0));
		}
		bean.setCsLoyaltyTxns(customerLoyaltyTxns);
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerCode(customerRef);
		bean.setCustomerRef(customerEntity);
		bean.setEndDate(endDate);
		bean.setExpiredCredit(expiredCredit);
		bean.setId(id);
		LoyaltyProgramEntity loyaltyProgramEntity = new LoyaltyProgramEntity();
		loyaltyProgramEntity.setProgramCode(programRef);
		bean.setProgramRef(loyaltyProgramEntity);
		bean.setRedeemedCredit(redeemedCredit);
		bean.setRevisionControl(revisionControl);
		bean.setStartDate(startDate);
		bean.setStatus(status);
		bean.setTenantId(tenantId);

		return bean;
	}
}
