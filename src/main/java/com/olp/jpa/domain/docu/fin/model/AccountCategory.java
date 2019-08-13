package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.FinEnums.AccountClass;

@XmlRootElement(name = "fincalendar", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "categoryCode", "categoryName", "accountClass", "lifeCycleStatus",
		"accountSubCategories", "revisionControl" })
public class AccountCategory implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "categoryId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String categoryCode;

	@XmlElement
	private String categoryName;

	@XmlElement
	private AccountClass accountClass;

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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the accountClass
	 */
	public AccountClass getAccountClass() {
		return accountClass;
	}

	/**
	 * @param accountClass
	 *            the accountClass to set
	 */
	public void setAccountClass(AccountClass accountClass) {
		this.accountClass = accountClass;
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
	 * @return the accountSubCategories
	 */
	public List<AccountSubCategory> getAccountSubCategories() {
		return accountSubCategories;
	}

	/**
	 * @param accountSubCategories
	 *            the accountSubCategories to set
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public AccountCategoryEntity convertTo(int mode) {
		AccountCategoryEntity bean = new AccountCategoryEntity();
		bean.setId(id);
		bean.setTenantId(tenantId);
		bean.setAccountClass(accountClass);
		List<AccountSubCategoryEntity> accountSubCategoriesList = new ArrayList<>();
		for (AccountSubCategory accountSubCategory : accountSubCategories) {
			AccountSubCategoryEntity accountSubCategoryEntity = accountSubCategory.convertTo(mode);
			accountSubCategoriesList.add(accountSubCategoryEntity);
		}
		bean.setAccountSubCategories(accountSubCategoriesList);
		bean.setCategoryCode(categoryCode);
		bean.setCategoryName(categoryName);
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
