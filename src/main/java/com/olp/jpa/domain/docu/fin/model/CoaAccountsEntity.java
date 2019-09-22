package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;

@Entity
@Table(name = "trl_coa_accounts", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "account_code" }))
@NamedQueries({
		@NamedQuery(name = "CoaAccountsEntity.findbyAccountCatg", query = "SELECT t FROM CoaAccountsEntity t WHERE t.accountCatgCode = :accountCatgCode and t.accountSubCatgCode = :accountSubCatgCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class CoaAccountsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "coa_account_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "account_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String accountCode;

	@Column(name = "account_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String accountName;

	@ManyToOne(optional = true)
	@JoinColumn(name = "coa_ref")
	@ContainedIn
	private ChartOfAccountsEntity coaRef;

	@Column(name = "coa_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String coaCode;

	@ManyToOne(optional = true)
	@JoinColumn(name = "account_catg_ref")
	@ContainedIn
	private AccountCategoryEntity accountCatgRef;

	@Column(name = "account_catg_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String accountCatgCode;

	@ManyToOne(optional = true)
	@JoinColumn(name = "account_sub_catg_ref")
	@ContainedIn
	private AccountSubCategoryEntity accountSubCatgRef;

	@Column(name = "account_sub_catg_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String accountSubCatgCode;

	@Column(name = "child_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isChild;

	@ManyToOne(optional = true)
	@JoinColumn(name = "parent_account_ref")
	@ContainedIn
	private CoaAccountsEntity parentAccountRef;

	@Column(name = "parent_account_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String parentAccountCode;

	@Column(name = "segment1_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment1Value;

	@Column(name = "segment1_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment1LovCode;

	@Column(name = "segment2_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment2Value;

	@Column(name = "segment2_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment2LovCode;

	@Column(name = "segment3_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment3Value;

	@Column(name = "segment3_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment3LovCode;

	@Column(name = "segment4_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment4Value;

	@Column(name = "segment4_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment4LovCode;

	@Column(name = "segment5_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment5Value;

	@Column(name = "segment5_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment5LovCode;

	@Column(name = "enabled_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isEnabled;

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
	public ChartOfAccountsEntity getCoaRef() {
		return coaRef;
	}

	/**
	 * @param coaRef
	 *            the coaRef to set
	 */
	public void setCoaRef(ChartOfAccountsEntity coaRef) {
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
	public AccountCategoryEntity getAccountCatgRef() {
		return accountCatgRef;
	}

	/**
	 * @param accountCatgRef
	 *            the accountCatgRef to set
	 */
	public void setAccountCatgRef(AccountCategoryEntity accountCatgRef) {
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
	public AccountSubCategoryEntity getAccountSubCatgRef() {
		return accountSubCatgRef;
	}

	/**
	 * @param accountSubCatgRef
	 *            the accountSubCatgRef to set
	 */
	public void setAccountSubCatgRef(AccountSubCategoryEntity accountSubCatgRef) {
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
	public CoaAccountsEntity getParentAccountRef() {
		return parentAccountRef;
	}

	/**
	 * @param parentAccountRef
	 *            the parentAccountRef to set
	 */
	public void setParentAccountRef(CoaAccountsEntity parentAccountRef) {
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
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
			bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setAccountCatgCode(accountCatgCode);
		bean.setAccountCatgRef(accountCatgRef.getCategoryCode());
		bean.setAccountCode(accountCode);
		bean.setAccountName(accountName);
		bean.setAccountSubCatgCode(accountSubCatgCode);
		bean.setAccountSubCatgRef(accountSubCatgRef.getSubCategoryCode());
		bean.setChild(isChild);
		bean.setCoaCode(coaCode);
		bean.setCoaRef(coaRef.getCoaCode());
		bean.setEnabled(isEnabled);
		bean.setParentAccountCode(parentAccountCode);
		bean.setParentAccountRef(parentAccountRef.getAccountCode());
		bean.setRevisionControl(revisionControl);
		bean.setSegment1LovCode(segment1LovCode);
		bean.setSegment1Value(segment1Value);
		bean.setSegment2LovCode(segment2LovCode);
		bean.setSegment2Value(segment2Value);
		bean.setSegment3LovCode(segment3LovCode);
		bean.setSegment3Value(segment3Value);
		bean.setSegment4LovCode(segment4LovCode);
		bean.setSegment4Value(segment4Value);
		bean.setSegment5LovCode(segment5LovCode);
		bean.setSegment5Value(segment5Value);

		return bean;
	}
}
