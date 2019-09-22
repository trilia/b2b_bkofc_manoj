package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;

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
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.comm.model.LovDefinitionEntity;

@Entity
@Table(name = "trl_coa_definitions", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "coa_code" }))
@NamedQueries({
		@NamedQuery(name = "ChartOfAccountsEntity.findbyCoaCode", query = "SELECT t FROM ChartOfAccountsEntity t WHERE t.coaCode = :coaCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class ChartOfAccountsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "chart_account_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@Column(name = "coa_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String coaCode;
	
	@Column(name = "coa_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String coaName;
	
	@Column(name = "use_acct_num_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean useAccountNum;
	
	@Column(name = "num_segments", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private int numSegments;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "segment1_lov_ref")
	@ContainedIn
	private LovDefinitionEntity segment1LovRef;

	@Column(name = "segment1_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment1LovCode;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "segment2_lov_ref")
	@ContainedIn
	private LovDefinitionEntity segment2LovRef;
	
	@Column(name = "segment2_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment2LovCode;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "segment3_lov_ref")
	@ContainedIn
	private LovDefinitionEntity segment3LovRef;
	
	@Column(name = "segment3_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment3LovCode;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "segment4_lov_ref")
	@ContainedIn
	private LovDefinitionEntity segment4LovRef;
	
	@Column(name = "segment4_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment4LovCode;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "segment5_lov_ref")
	@ContainedIn
	private LovDefinitionEntity segment5LovRef;
	
	@Column(name = "segment5_lov_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String segment5LovCode;
	
	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStatus lifecycleStatus;

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
	public LovDefinitionEntity getSegment1LovRef() {
		return segment1LovRef;
	}

	/**
	 * @param segment1LovRef the segment1LovRef to set
	 */
	public void setSegment1LovRef(LovDefinitionEntity segment1LovRef) {
		this.segment1LovRef = segment1LovRef;
	}

	/**
	 * @return the segment1LovCode
	 */
	public String getSegment1LovCode() {
		return segment1LovCode;
	}

	/**
	 * @param segment1LovCode the segment1LovCode to set
	 */
	public void setSegment1LovCode(String segment1LovCode) {
		this.segment1LovCode = segment1LovCode;
	}

	/**
	 * @return the segment2LovRef
	 */
	public LovDefinitionEntity getSegment2LovRef() {
		return segment2LovRef;
	}

	/**
	 * @param segment2LovRef the segment2LovRef to set
	 */
	public void setSegment2LovRef(LovDefinitionEntity segment2LovRef) {
		this.segment2LovRef = segment2LovRef;
	}

	/**
	 * @return the segment2LovCode
	 */
	public String getSegment2LovCode() {
		return segment2LovCode;
	}

	/**
	 * @param segment2LovCode the segment2LovCode to set
	 */
	public void setSegment2LovCode(String segment2LovCode) {
		this.segment2LovCode = segment2LovCode;
	}

	/**
	 * @return the segment3LovRef
	 */
	public LovDefinitionEntity getSegment3LovRef() {
		return segment3LovRef;
	}

	/**
	 * @param segment3LovRef the segment3LovRef to set
	 */
	public void setSegment3LovRef(LovDefinitionEntity segment3LovRef) {
		this.segment3LovRef = segment3LovRef;
	}

	/**
	 * @return the segment3LovCode
	 */
	public String getSegment3LovCode() {
		return segment3LovCode;
	}

	/**
	 * @param segment3LovCode the segment3LovCode to set
	 */
	public void setSegment3LovCode(String segment3LovCode) {
		this.segment3LovCode = segment3LovCode;
	}

	/**
	 * @return the segment4LovRef
	 */
	public LovDefinitionEntity getSegment4LovRef() {
		return segment4LovRef;
	}

	/**
	 * @param segment4LovRef the segment4LovRef to set
	 */
	public void setSegment4LovRef(LovDefinitionEntity segment4LovRef) {
		this.segment4LovRef = segment4LovRef;
	}

	/**
	 * @return the segment4LovCode
	 */
	public String getSegment4LovCode() {
		return segment4LovCode;
	}

	/**
	 * @param segment4LovCode the segment4LovCode to set
	 */
	public void setSegment4LovCode(String segment4LovCode) {
		this.segment4LovCode = segment4LovCode;
	}

	/**
	 * @return the segment5LovRef
	 */
	public LovDefinitionEntity getSegment5LovRef() {
		return segment5LovRef;
	}

	/**
	 * @param segment5LovRef the segment5LovRef to set
	 */
	public void setSegment5LovRef(LovDefinitionEntity segment5LovRef) {
		this.segment5LovRef = segment5LovRef;
	}

	/**
	 * @return the segment5LovCode
	 */
	public String getSegment5LovCode() {
		return segment5LovCode;
	}

	/**
	 * @param segment5LovCode the segment5LovCode to set
	 */
	public void setSegment5LovCode(String segment5LovCode) {
		this.segment5LovCode = segment5LovCode;
	}

	/**
	 * @return the lifecycleStatus
	 */
	public LifeCycleStatus getLifecycleStatus() {
		return lifecycleStatus;
	}

	/**
	 * @param lifecycleStatus the lifecycleStatus to set
	 */
	public void setLifecycleStatus(LifeCycleStatus lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
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
	
	public ChartOfAccounts convertTo(int mode){
		ChartOfAccounts bean = new ChartOfAccounts();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
			bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		
		bean.setCoaCode(coaCode);
		bean.setCoaName(coaName);
		bean.setNumSegments(mode);
		bean.setSegment1LovRef(segment1LovRef.getLovCode());
		bean.setSegment2LovRef(segment2LovRef.getLovCode());
		bean.setSegment3LovRef(segment3LovRef.getLovCode());
		bean.setSegment4LovRef(segment4LovRef.getLovCode());
		bean.setSegment5LovRef(segment5LovRef.getLovCode());
		bean.setUseAccountNum(useAccountNum);
		
		return bean;
	}
}
