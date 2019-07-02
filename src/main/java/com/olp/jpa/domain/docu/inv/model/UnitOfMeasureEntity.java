/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomClass;
import com.olp.jpa.domain.docu.inv.model.InvEnums.UomType;

@Entity
@Table(name = "trl_uoms", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id", "uom_code" }))
@NamedQueries({
		@NamedQuery(name = "UnitOfMeasureEntity.findByUomCode", query = "SELECT t FROM UnitOfMeasureEntity t JOIN FETCH t.srcConversions WHERE t.uomCode = :code and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class UnitOfMeasureEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uom_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "uom_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String uomCode;

	@Column(name = "uom_desc", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String uomDesc;

	@Enumerated(EnumType.STRING)
	@Column(name = "uom_type", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private UomType uomType;

	@Enumerated(EnumType.STRING)
	@Column(name = "uom_class", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private UomClass uomClass;

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStatus lifecycleStatus;

	@Column(name = "strict_conv_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean isStrictConv;

	@OneToMany(mappedBy = "srcUomRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<UomConversionEntity> srcConversions = new HashSet<>();

	@OneToMany(mappedBy = "destUomRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<UomConversionEntity> destConversions = new HashSet<>();

	// @Embedded
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
	 * @return the uomCode
	 */
	public String getUomCode() {
		return uomCode;
	}

	/**
	 * @param uomCode
	 *            the uomCode to set
	 */
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}

	/**
	 * @return the uomDesc
	 */
	public String getUomDesc() {
		return uomDesc;
	}

	/**
	 * @param uomDesc
	 *            the uomDesc to set
	 */
	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	/**
	 * @return the uomType
	 */
	public UomType getUomType() {
		return uomType;
	}

	/**
	 * @param uomType
	 *            the uomType to set
	 */
	public void setUomType(UomType uomType) {
		this.uomType = uomType;
	}

	/**
	 * @return the uomClass
	 */
	public UomClass getUomClass() {
		return uomClass;
	}

	/**
	 * @param uomClass
	 *            the uomClass to set
	 */
	public void setUomClass(UomClass uomClass) {
		this.uomClass = uomClass;
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
	 * @return the isStrictConv
	 */
	public boolean isStrictConv() {
		return isStrictConv;
	}

	/**
	 * @param isStrictConv
	 *            the isStrictConv to set
	 */
	public void setStrictConv(boolean isStrictConv) {
		this.isStrictConv = isStrictConv;
	}

	/**
	 * @return the srcConversions
	 */
	public Set<UomConversionEntity> getSrcConversions() {
		return srcConversions;
	}

	/**
	 * @param srcConversions
	 *            the srcConversions to set
	 */
	public void setSrcConversions(Set<UomConversionEntity> srcConversions) {
		this.srcConversions = srcConversions;
	}

	/**
	 * @return the destConversions
	 */
	public Set<UomConversionEntity> getDestConversions() {
		return destConversions;
	}

	/**
	 * @param destConversions
	 *            the destConversions to set
	 */
	public void setDestConversions(Set<UomConversionEntity> destConversions) {
		this.destConversions = destConversions;
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

	public UnitOfMeasure convertTo(int mode) {
		UnitOfMeasure bean = new UnitOfMeasure();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)// todo - check referred
														// existing
			bean.setId(this.id);

		bean.setTenantId(this.tenantId);
		bean.setUomClass(uomClass);
		bean.setUomCode(uomCode);
		bean.setUomDesc(uomDesc);
		bean.setUomType(uomType);
		bean.setStrictConv(isStrictConv);

		Set<UomConversion> srcConversionsSet = new HashSet<>();
		for (UomConversionEntity uom : srcConversions) {
			UomConversion uomConversion = uom.convertTo(mode);
			srcConversionsSet.add(uomConversion);
		}

		Set<UomConversion> destConversionsSet = new HashSet<>();
		for (UomConversionEntity uom : destConversions) {
			UomConversion uomConversion = uom.convertTo(mode);
			destConversionsSet.add(uomConversion);
		}
		bean.setSrcConversions(srcConversionsSet);
		bean.setDestConversions(destConversionsSet);
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
