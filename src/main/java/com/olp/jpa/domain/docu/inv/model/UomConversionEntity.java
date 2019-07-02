/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.inv.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.Constants;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;

@Entity
@Table(name = "trl_uom_conversions",uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id", "entry_sequence","src_uom_code","dest_uom_code"}))
@NamedQueries({
		@NamedQuery(name = "UomConversionEntity.srcUomCode", query = "SELECT t FROM UomConversionEntity t JOIN FETCH t.srcUomRef WHERE t.srcUomCode = :srcUomCode and t.tenantId = :tenant "),
		@NamedQuery(name = "UomConversionEntity.findByDestUom", query = "SELECT t FROM UomConversionEntity t JOIN FETCH t.destUomRef WHERE t.destUomCode = :destUomCode and t.tenantId = :tenant "),
		@NamedQuery(name = "UomConversionEntity.findBySrcTarget", query = "SELECT t FROM UomConversionEntity t JOIN FETCH t.srcUomRef JOIN FETCH t.destUomRef WHERE t.srcUomCode = :srcUomCode and t.destUomCode = :destUomCode and t.tenantId = :tenant ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class UomConversionEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uom_conv_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;

	@Column(name = "entry_sequence", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private int entrySequence;

	@ManyToOne
	@JoinColumn(name = "unit_of_measure_ref")
	@ContainedIn
	private UnitOfMeasureEntity srcUomRef;

	@Column(name = "src_uom_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String srcUomCode;

	@ManyToOne
	@JoinColumn(name = "unit_of_measure_ref")
	@ContainedIn
	private UnitOfMeasureEntity destUomRef;

	@Column(name = "dest_uom_code", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String destUomCode;

	@Column(name = "conv_factor", nullable = true)
	private BigDecimal convFactor;

	@Column(name = "conv_function", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String convFunction;

	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	@Enumerated(EnumType.STRING)
	private LifeCycleStatus lifecycleStatus;

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
	 * @return the entrySequence
	 */
	public int getEntrySequence() {
		return entrySequence;
	}

	/**
	 * @param entrySequence
	 *            the entrySequence to set
	 */
	public void setEntrySequence(int entrySequence) {
		this.entrySequence = entrySequence;
	}

	/**
	 * @return the srcUomRef
	 */
	public UnitOfMeasureEntity getSrcUomRef() {
		return srcUomRef;
	}

	/**
	 * @param srcUomRef
	 *            the srcUomRef to set
	 */
	public void setSrcUomRef(UnitOfMeasureEntity srcUomRef) {
		this.srcUomRef = srcUomRef;
	}

	/**
	 * @return the srcUomCode
	 */
	public String getSrcUomCode() {
		return srcUomCode;
	}

	/**
	 * @param srcUomCode
	 *            the srcUomCode to set
	 */
	public void setSrcUomCode(String srcUomCode) {
		this.srcUomCode = srcUomCode;
	}

	/**
	 * @return the destUomRef
	 */
	public UnitOfMeasureEntity getDestUomRef() {
		return destUomRef;
	}

	/**
	 * @param destUomRef
	 *            the destUomRef to set
	 */
	public void setDestUomRef(UnitOfMeasureEntity destUomRef) {
		this.destUomRef = destUomRef;
	}

	/**
	 * @return the destUomCode
	 */
	public String getDestUomCode() {
		return destUomCode;
	}

	/**
	 * @param destUomCode
	 *            the destUomCode to set
	 */
	public void setDestUomCode(String destUomCode) {
		this.destUomCode = destUomCode;
	}

	/**
	 * @return the convFactor
	 */
	public BigDecimal getConvFactor() {
		return convFactor;
	}

	/**
	 * @param convFactor
	 *            the convFactor to set
	 */
	public void setConvFactor(BigDecimal convFactor) {
		this.convFactor = convFactor;
	}

	/**
	 * @return the convFunction
	 */
	public String getConvFunction() {
		return convFunction;
	}

	/**
	 * @param convFunction
	 *            the convFunction to set
	 */
	public void setConvFunction(String convFunction) {
		this.convFunction = convFunction;
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

	public UomConversion convertTo(int mode) {
		UomConversion bean = new UomConversion();
		if (mode <= Constants.CONV_COMPLETE_DEFINITION)// todo - check referred
			bean.setId(this.id);
		bean.setId(this.id);
		bean.setTenantId(this.tenantId);
		bean.setEntrySequence(entrySequence);
		bean.setSrcUomCode(srcUomCode);
		bean.setDestUomCode(destUomCode);
		bean.setConvFactor(convFactor);
		bean.setConvFunction(convFunction);

		return bean;
	}
}
