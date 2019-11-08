package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
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

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerLineType;

@Entity
@Table(name = "trl_ledger_line"/*, uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "ledger_name" }*/)
@NamedQueries({
		@NamedQuery(name = "LedgerLineEntity.findbyLedgerLine", query = "SELECT t FROM LedgerLineEntity t WHERE t.ledgerName = :ledgerName and t.lineNum = :lineNum ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class LedgerLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ledger_line_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "ledger_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String ledgerName;

	@Column(name = "ledger_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer lineNum;

	@ManyToOne
	@JoinColumn(name = "ledger_ref")
	@ContainedIn
	private LedgerEntity ledgerRef;

	@ManyToOne
	@JoinColumn(name = "account_ref")
	@ContainedIn
	private CoaAccountsEntity accountRef;

	@Column(name = "account_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String accountCode;

	@Column(name = "line_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private LedgerLineType lineType;

	@Column(name = "line_amount", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal lineAmount;

	@Column(name = "line_desc", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	private String lineDesc;
	
	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

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
	 * @return the ledgerName
	 */
	public String getLedgerName() {
		return ledgerName;
	}

	/**
	 * @param ledgerName
	 *            the ledgerName to set
	 */
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	/**
	 * @return the lineNum
	 */
	public Integer getLineNum() {
		return lineNum;
	}

	/**
	 * @param lineNum
	 *            the lineNum to set
	 */
	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * @return the ledgerRef
	 */
	public LedgerEntity getLedgerRef() {
		return ledgerRef;
	}

	/**
	 * @param ledgerRef
	 *            the ledgerRef to set
	 */
	public void setLedgerRef(LedgerEntity ledgerRef) {
		this.ledgerRef = ledgerRef;
	}

	/**
	 * @return the accountRef
	 */
	public CoaAccountsEntity getAccountRef() {
		return accountRef;
	}

	/**
	 * @param accountRef
	 *            the accountRef to set
	 */
	public void setAccountRef(CoaAccountsEntity accountRef) {
		this.accountRef = accountRef;
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
	 * @return the lineType
	 */
	public LedgerLineType getLineType() {
		return lineType;
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(LedgerLineType lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return the lineAmount
	 */
	public BigDecimal getLineAmount() {
		return lineAmount;
	}

	/**
	 * @param lineAmount
	 *            the lineAmount to set
	 */
	public void setLineAmount(BigDecimal lineAmount) {
		this.lineAmount = lineAmount;
	}

	/**
	 * @return the lineDesc
	 */
	public String getLineDesc() {
		return lineDesc;
	}

	/**
	 * @param lineDesc
	 *            the lineDesc to set
	 */
	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}

	public LedgerLine convertTo(int mode) {
		LedgerLine bean = new LedgerLine();
		bean.setId(id);
		bean.setTenantId(tenantId);
		bean.setAccountRef(accountRef.getAccountCode());
		bean.setLedgerName(ledgerName);
		bean.setLineAmount(lineAmount);
		bean.setLineDesc(lineDesc);
		bean.setLineNum(lineNum);
		bean.setLineType(lineType);
		
		return bean;
	}

}
