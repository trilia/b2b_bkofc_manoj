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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fa.model.FaEnums.DepreciationType;

@Entity
@Table(name = "trl_depr_schedule")
@NamedQueries({
		@NamedQuery(name = "DeprScheduleEntity.findByScheduleCode", query = "SELECT t FROM DeprScheduleEntity t WHERE t.deprScheduleCode = :deprScheduleCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class DeprScheduleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "depr_schedule_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "depr_schedule_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String deprScheduleCode;

	@Column(name = "depr_schedule_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String deprScheduleName;

	@Enumerated(EnumType.STRING)
	@Column(name = "depr_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private DepreciationType deprType;

	@Column(name = "depr_type_impl", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String deprTypeImpl;

	@Column(name = "depr_pct", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Float deprPct;

	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_stage", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStage lifecycleStage;

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
	 * @return the deprScheduleCode
	 */
	public String getDeprScheduleCode() {
		return deprScheduleCode;
	}

	/**
	 * @param deprScheduleCode
	 *            the deprScheduleCode to set
	 */
	public void setDeprScheduleCode(String deprScheduleCode) {
		this.deprScheduleCode = deprScheduleCode;
	}

	/**
	 * @return the deprScheduleName
	 */
	public String getDeprScheduleName() {
		return deprScheduleName;
	}

	/**
	 * @param deprScheduleName
	 *            the deprScheduleName to set
	 */
	public void setDeprScheduleName(String deprScheduleName) {
		this.deprScheduleName = deprScheduleName;
	}

	/**
	 * @return the deprType
	 */
	public DepreciationType getDeprType() {
		return deprType;
	}

	/**
	 * @param deprType
	 *            the deprType to set
	 */
	public void setDeprType(DepreciationType deprType) {
		this.deprType = deprType;
	}

	/**
	 * @return the deprTypeImpl
	 */
	public String getDeprTypeImpl() {
		return deprTypeImpl;
	}

	/**
	 * @param deprTypeImpl
	 *            the deprTypeImpl to set
	 */
	public void setDeprTypeImpl(String deprTypeImpl) {
		this.deprTypeImpl = deprTypeImpl;
	}

	/**
	 * @return the deprPct
	 */
	public Float getDeprPct() {
		return deprPct;
	}

	/**
	 * @param deprPct
	 *            the deprPct to set
	 */
	public void setDeprPct(Float deprPct) {
		this.deprPct = deprPct;
	}

	/**
	 * @return the lifecycleStage
	 */
	public LifeCycleStage getLifecycleStage() {
		return lifecycleStage;
	}

	/**
	 * @param lifecycleStage
	 *            the lifecycleStage to set
	 */
	public void setLifecycleStage(LifeCycleStage lifecycleStage) {
		this.lifecycleStage = lifecycleStage;
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

	public DeprSchedule convertTo(int mode) {
		DeprSchedule bean = new DeprSchedule();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)
			// existing
			bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setDeprScheduleCode(deprScheduleCode);
		bean.setDeprScheduleName(deprScheduleName);
		bean.setDeprType(deprType);
		bean.setDeprTypeImpl(deprTypeImpl);
		bean.setLifecycleStage(lifecycleStage);
		bean.setRevisionControl(revisionControl);

		return bean;
	}

}
