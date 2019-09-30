package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "fincalendar", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "subCategoryCode", "subCategoryName", "categoryRef","categoryRef", "lifeCycleStatus",
		"accountSubCategories", "revisionControl" })
public class AccountSubCategory implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "subCategoryId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String subCategoryCode;

	@XmlElement
	private String subCategoryName;

	@XmlElement
	private String categoryRef;

	@XmlElement
	private String categoryCode;

	@XmlElement
	private LifeCycleStatus lifeCycleStatus;

	@XmlElement
	private List<AccountSubCategory> accountSubCategories;

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
	 * @return the subCategoryCode
	 */
	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	/**
	 * @param subCategoryCode
	 *            the subCategoryCode to set
	 */
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}

	/**
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param subCategoryName
	 *            the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	/**
	 * @return the categoryRef
	 */
	public String getCategoryRef() {
		return categoryRef;
	}

	/**
	 * @param categoryRef
	 *            the categoryRef to set
	 */
	public void setCategoryRef(String categoryRef) {
		this.categoryRef = categoryRef;
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
	 * @return the accountCategories
	 */
	public List<AccountSubCategory> getAccountSubCategories() {
		return accountSubCategories;
	}

	/**
	 * @param accountCategories
	 *            the accountCategories to set
	 */
	public void setAccountSubCategories(List<AccountSubCategory> accountSubCategories) {
		this.accountSubCategories = accountSubCategories;
	}

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode
	 *            the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public AccountSubCategoryEntity convertTo(int mode) {
		AccountSubCategoryEntity bean = new AccountSubCategoryEntity();

		bean.setId(id);
		bean.setTenantId(tenantId);	
		bean.setCategoryCode(categoryCode);
		
		bean.setSubCategoryCode(subCategoryCode);
		bean.setSubCategoryName(subCategoryName);
		bean.setRevisionControl(revisionControl);

		return bean;
	}

}
