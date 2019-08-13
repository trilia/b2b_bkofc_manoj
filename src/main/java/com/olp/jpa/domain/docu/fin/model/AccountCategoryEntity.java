package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.FinEnums.AccountClass;

@Entity
@Table(name = "trl_account_category", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"category_code" }))
@NamedQueries({
		@NamedQuery(name = "AccountCategoryEntity.findbyCategoryCode", query = "SELECT t FROM AccountCategoryEntity t WHERE t.categoryCode = :catgCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class AccountCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "category_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String categoryCode;

	@Column(name = "category_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String categoryName;

	@Column(name = "account_class", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private AccountClass accountClass;

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStatus lifecycleStatus;

	@OneToMany(mappedBy = "categoryRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<AccountSubCategoryEntity> accountSubCategories = new ArrayList<>();

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
	 * @return the lifecycleStatus
	 */
	public LifeCycleStatus getLifecycleStatus() {
		return lifecycleStatus;
	}

	/**
	 * @param lifecycleStatus
	 *            the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifeCycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
	}

	/**
	 * @return the acountSubCatgories
	 */
	public List<AccountSubCategoryEntity> getAccountSubCategories() {
		return accountSubCategories;
	}

	/**
	 * @param acountSubCatgories
	 *            the acountSubCatgories to set
	 */
	public void setAccountSubCategories(List<AccountSubCategoryEntity> accountSubCategories) {
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

	public AccountCategory convertTo(int mode) {
		AccountCategory bean = new AccountCategory();
		bean.setId(id);
		bean.setTenantId(tenantId);
		bean.setAccountClass(accountClass);
		if (accountSubCategories != null && !accountSubCategories.isEmpty()) {
			List<AccountSubCategory> accountSubCategoryList = new ArrayList<>();
			for (AccountSubCategoryEntity accountSubCategory : accountSubCategories) {
				AccountSubCategory accntSubCategory = accountSubCategory.convertTo(mode);
				accountSubCategoryList.add(accntSubCategory);
			}
			bean.setAccountSubCategories(accountSubCategoryList);
		}

		bean.setCategoryCode(categoryCode);
		bean.setCategoryName(categoryName);
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
