package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "coaAccounts", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "accountCode", "accountName", "coaRef", "coaCode", "accountCatgRef",
		"accountCatgCode", "accountSubCatgRef", "accountSubCatCode", "isChild", "parentAccountRef", "parentAccountCode",
		"segment1Value", "segment1LovCode", "segment2Value", "segment2LovCode", "segment3Value", "segment3LovCode",
		"segment4Value", "segment4LovCode", "segment5Value", "segment5LovCode", "isEnabled", "revisionControl" })
public class CoaAccounts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "coaAccId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String accountCode;

	@XmlElement
	private String accountName;

	@XmlElement
	private String coaRef;

	@XmlElement
	private String coaCode;

	@XmlElement
	private String accountCatgRef;

	@XmlElement
	private String accountCatgCode;

	@XmlElement
	private String accountSubCatgRef;

	@XmlElement
	private String accountSubCatgCode;

	@XmlElement
	private boolean isChild;

	@XmlElement
	private String parentAccountRef;

	@XmlElement
	private String parentAccountCode;

	@XmlElement
	private String segment1Value;

	@XmlElement
	private String segment1LovCode;

	@XmlElement
	private String segment2Value;

	@XmlElement
	private String segment2LovCode;

	@XmlElement
	private String segment3Value;

	@XmlElement
	private String segment3LovCode;

	@XmlElement
	private String segment4Value;

	@XmlElement
	private String segment4LovCode;

	@XmlElement
	private String segment5Value;

	@XmlElement
	private String segment5LovCode;

	@XmlElement
	private boolean isEnabled;

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
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *            the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the coaRef
	 */
	public String getCoaRef() {
		return coaRef;
	}

	/**
	 * @param coaRef
	 *            the coaRef to set
	 */
	public void setCoaRef(String coaRef) {
		this.coaRef = coaRef;
	}

	/**
	 * @return the coaCode
	 */
	public String getCoaCode() {
		return coaCode;
	}

	/**
	 * @param coaCode
	 *            the coaCode to set
	 */
	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	/**
	 * @return the accountCatgRef
	 */
	public String getAccountCatgRef() {
		return accountCatgRef;
	}

	/**
	 * @param accountCatgRef
	 *            the accountCatgRef to set
	 */
	public void setAccountCatgRef(String accountCatgRef) {
		this.accountCatgRef = accountCatgRef;
	}

	/**
	 * @return the accountCatgCode
	 */
	public String getAccountCatgCode() {
		return accountCatgCode;
	}

	/**
	 * @param accountCatgCode
	 *            the accountCatgCode to set
	 */
	public void setAccountCatgCode(String accountCatgCode) {
		this.accountCatgCode = accountCatgCode;
	}

	/**
	 * @return the accountSubCatgRef
	 */
	public String getAccountSubCatgRef() {
		return accountSubCatgRef;
	}

	/**
	 * @param accountSubCatgRef
	 *            the accountSubCatgRef to set
	 */
	public void setAccountSubCatgRef(String accountSubCatgRef) {
		this.accountSubCatgRef = accountSubCatgRef;
	}

	/**
	 * @return the accountSubCatgCode
	 */
	public String getAccountSubCatgCode() {
		return accountSubCatgCode;
	}

	/**
	 * @param accountSubCatgCode
	 *            the accountSubCatgCode to set
	 */
	public void setAccountSubCatgCode(String accountSubCatgCode) {
		this.accountSubCatgCode = accountSubCatgCode;
	}

	/**
	 * @return the isChild
	 */
	public boolean isChild() {
		return isChild;
	}

	/**
	 * @param isChild
	 *            the isChild to set
	 */
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	/**
	 * @return the parentAccountRef
	 */
	public String getParentAccountRef() {
		return parentAccountRef;
	}

	/**
	 * @param parentAccountRef
	 *            the parentAccountRef to set
	 */
	public void setParentAccountRef(String parentAccountRef) {
		this.parentAccountRef = parentAccountRef;
	}

	/**
	 * @return the parentAccountCode
	 */
	public String getParentAccountCode() {
		return parentAccountCode;
	}

	/**
	 * @param parentAccountCode
	 *            the parentAccountCode to set
	 */
	public void setParentAccountCode(String parentAccountCode) {
		this.parentAccountCode = parentAccountCode;
	}

	/**
	 * @return the segment1Value
	 */
	public String getSegment1Value() {
		return segment1Value;
	}

	/**
	 * @param segment1Value
	 *            the segment1Value to set
	 */
	public void setSegment1Value(String segment1Value) {
		this.segment1Value = segment1Value;
	}

	/**
	 * @return the segment1LovCode
	 */
	public String getSegment1LovCode() {
		return segment1LovCode;
	}

	/**
	 * @param segment1LovCode
	 *            the segment1LovCode to set
	 */
	public void setSegment1LovCode(String segment1LovCode) {
		this.segment1LovCode = segment1LovCode;
	}

	/**
	 * @return the segment2Value
	 */
	public String getSegment2Value() {
		return segment2Value;
	}

	/**
	 * @param segment2Value
	 *            the segment2Value to set
	 */
	public void setSegment2Value(String segment2Value) {
		this.segment2Value = segment2Value;
	}

	/**
	 * @return the segment2LovCode
	 */
	public String getSegment2LovCode() {
		return segment2LovCode;
	}

	/**
	 * @param segment2LovCode
	 *            the segment2LovCode to set
	 */
	public void setSegment2LovCode(String segment2LovCode) {
		this.segment2LovCode = segment2LovCode;
	}

	/**
	 * @return the segment3Value
	 */
	public String getSegment3Value() {
		return segment3Value;
	}

	/**
	 * @param segment3Value
	 *            the segment3Value to set
	 */
	public void setSegment3Value(String segment3Value) {
		this.segment3Value = segment3Value;
	}

	/**
	 * @return the segment3LovCode
	 */
	public String getSegment3LovCode() {
		return segment3LovCode;
	}

	/**
	 * @param segment3LovCode
	 *            the segment3LovCode to set
	 */
	public void setSegment3LovCode(String segment3LovCode) {
		this.segment3LovCode = segment3LovCode;
	}

	/**
	 * @return the segment4Value
	 */
	public String getSegment4Value() {
		return segment4Value;
	}

	/**
	 * @param segment4Value
	 *            the segment4Value to set
	 */
	public void setSegment4Value(String segment4Value) {
		this.segment4Value = segment4Value;
	}

	/**
	 * @return the segment4LovCode
	 */
	public String getSegment4LovCode() {
		return segment4LovCode;
	}

	/**
	 * @param segment4LovCode
	 *            the segment4LovCode to set
	 */
	public void setSegment4LovCode(String segment4LovCode) {
		this.segment4LovCode = segment4LovCode;
	}

	/**
	 * @return the segment5Value
	 */
	public String getSegment5Value() {
		return segment5Value;
	}

	/**
	 * @param segment5Value
	 *            the segment5Value to set
	 */
	public void setSegment5Value(String segment5Value) {
		this.segment5Value = segment5Value;
	}

	/**
	 * @return the segment5LovCode
	 */
	public String getSegment5LovCode() {
		return segment5LovCode;
	}

	/**
	 * @param segment5LovCode
	 *            the segment5LovCode to set
	 */
	public void setSegment5LovCode(String segment5LovCode) {
		this.segment5LovCode = segment5LovCode;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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

	public CoaAccounts convertTo(int mode) {
		CoaAccounts bean = new CoaAccounts();

		return bean;
	}

}
