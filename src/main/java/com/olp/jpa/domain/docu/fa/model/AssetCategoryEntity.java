package com.olp.jpa.domain.docu.fa.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
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

@Entity
@Table(name = "trl_asset_category")
@NamedQueries({
		@NamedQuery(name = "AssetCategoryEntity.findByCategoryCode", query = "SELECT t FROM AssetCategoryEntity t WHERE t.categoryCode = :categoryCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class AssetCategoryEntity implements Serializable {

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

	@ManyToOne(optional = true)
	@JoinColumn(name = "default_depr_sch_ref")
	@ContainedIn
	private DeprScheduleEntity defaultDeprScheduleRef;

	@Column(name = "default_depr_sch_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String defaultDeprScheduleCode;

	@Column(name = "default_prefix", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String defaultPrefix;

	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_stage", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStage lifeCycleStage;

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
	 * @return the defaultDeprScheduleRef
	 */
	public DeprScheduleEntity getDefaultDeprScheduleRef() {
		return defaultDeprScheduleRef;
	}

	/**
	 * @param defaultDeprScheduleRef
	 *            the defaultDeprScheduleRef to set
	 */
	public void setDefaultDeprScheduleRef(DeprScheduleEntity defaultDeprScheduleRef) {
		this.defaultDeprScheduleRef = defaultDeprScheduleRef;
	}

	/**
	 * @return the defaultDeprScheduleCode
	 */
	public String getDefaultDeprScheduleCode() {
		return defaultDeprScheduleCode;
	}

	/**
	 * @param defaultDeprScheduleCode
	 *            the defaultDeprScheduleCode to set
	 */
	public void setDefaultDeprScheduleCode(String defaultDeprScheduleCode) {
		this.defaultDeprScheduleCode = defaultDeprScheduleCode;
	}

	/**
	 * @return the defaultPrefix
	 */
	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	/**
	 * @param defaultPrefix
	 *            the defaultPrefix to set
	 */
	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
	}

	/**
	 * @return the lifeCycleStage
	 */
	public LifeCycleStage getLifeCycleStage() {
		return lifeCycleStage;
	}

	/**
	 * @param lifeCycleStage
	 *            the lifeCycleStage to set
	 */
	public void setLifeCycleStage(LifeCycleStage lifeCycleStage) {
		this.lifeCycleStage = lifeCycleStage;
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

	public AssetCategory convertTo(int mode) {
		AssetCategory bean = new AssetCategory();

		bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setCategoryCode(categoryCode);
		bean.setDefaultDeprScheduleCode(defaultDeprScheduleCode);
		DeprSchedule deprSchedule = new DeprSchedule();
		deprSchedule.setDeprScheduleCode(defaultDeprScheduleRef.getDeprScheduleCode());

		bean.setDefaultDeprScheduleRef(deprSchedule);
		bean.setDefaultPrefix(defaultPrefix);
		bean.setLifeCycleStage(lifeCycleStage);
		bean.setRevisionControl(revisionControl);
		return bean;
	}
}
