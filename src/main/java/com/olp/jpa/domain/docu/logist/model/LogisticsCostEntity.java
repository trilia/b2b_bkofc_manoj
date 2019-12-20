package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.TaxGroupEntity;

@Entity
@Table(name = "trl_logistics_cost")
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class LogisticsCostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "svc_cost_id", nullable = false)
	@DocumentId
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "svc_catg_ref")
	@ContainedIn
	private ServiceCategoryEntity svcCatgRef;
	
	/*@Column(name = "svc_catg_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String serviceCategoryCode;*/

	@Column(name = "vol_weight_min", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal volWeightMin;
	
	@Column(name = "vol_weight_max", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal volWeightMax;
	
	@Column(name = "basic_cost", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal basicCost;
	
	@ManyToOne
	@JoinColumn(name = "tax_group_ref")
	@ContainedIn
	private TaxGroupEntity taxGroupRef;
	
	@Column(name = "tax_group_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String taxGroupCode;
	
	@Column(name = "nonoffset_tax", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal nonOffsetTax;
		
	@Column(name = "nonoffset_tax_pct", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal nonOffsetTaxPct;	
	
	@Column(name = "offset_tax", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal offsetTax;
	
	@Column(name = "offset_tax_pct", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal offsetTaxPct;
	
	@Column(name = "total_cost", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal totalCost;
	
	@Column(name = "effective_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveDate;

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
	 * @return the svcCatgRef
	 */
	public ServiceCategoryEntity getSvcCatgRef() {
		return svcCatgRef;
	}

	/**
	 * @param svcCatgRef the svcCatgRef to set
	 */
	public void setSvcCatgRef(ServiceCategoryEntity svcCatgRef) {
		this.svcCatgRef = svcCatgRef;
	}

	/**
	 * @return the volWeightMin
	 */
	public BigDecimal getVolWeightMin() {
		return volWeightMin;
	}

	/**
	 * @param volWeightMin the volWeightMin to set
	 */
	public void setVolWeightMin(BigDecimal volWeightMin) {
		this.volWeightMin = volWeightMin;
	}

	/**
	 * @return the volWeightMax
	 */
	public BigDecimal getVolWeightMax() {
		return volWeightMax;
	}

	/**
	 * @param volWeightMax the volWeightMax to set
	 */
	public void setVolWeightMax(BigDecimal volWeightMax) {
		this.volWeightMax = volWeightMax;
	}

	/**
	 * @return the basicCost
	 */
	public BigDecimal getBasicCost() {
		return basicCost;
	}

	/**
	 * @param basicCost the basicCost to set
	 */
	public void setBasicCost(BigDecimal basicCost) {
		this.basicCost = basicCost;
	}

	/**
	 * @return the taxGroupRef
	 */
	public TaxGroupEntity getTaxGroupRef() {
		return taxGroupRef;
	}

	/**
	 * @param taxGroupRef the taxGroupRef to set
	 */
	public void setTaxGroupRef(TaxGroupEntity taxGroupRef) {
		this.taxGroupRef = taxGroupRef;
	}

	/**
	 * @return the taxGroupCode
	 */
	public String getTaxGroupCode() {
		return taxGroupCode;
	}

	/**
	 * @param taxGroupCode the taxGroupCode to set
	 */
	public void setTaxGroupCode(String taxGroupCode) {
		this.taxGroupCode = taxGroupCode;
	}

	/**
	 * @return the nonOffsetTax
	 */
	public BigDecimal getNonOffsetTax() {
		return nonOffsetTax;
	}

	/**
	 * @param nonOffsetTax the nonOffsetTax to set
	 */
	public void setNonOffsetTax(BigDecimal nonOffsetTax) {
		this.nonOffsetTax = nonOffsetTax;
	}

	/**
	 * @return the nonOffsetTaxPct
	 */
	public BigDecimal getNonOffsetTaxPct() {
		return nonOffsetTaxPct;
	}

	/**
	 * @param nonOffsetTaxPct the nonOffsetTaxPct to set
	 */
	public void setNonOffsetTaxPct(BigDecimal nonOffsetTaxPct) {
		this.nonOffsetTaxPct = nonOffsetTaxPct;
	}

	/**
	 * @return the offsetTax
	 */
	public BigDecimal getOffsetTax() {
		return offsetTax;
	}

	/**
	 * @param offsetTax the offsetTax to set
	 */
	public void setOffsetTax(BigDecimal offsetTax) {
		this.offsetTax = offsetTax;
	}

	/**
	 * @return the offsetTaxPct
	 */
	public BigDecimal getOffsetTaxPct() {
		return offsetTaxPct;
	}

	/**
	 * @param offsetTaxPct the offsetTaxPct to set
	 */
	public void setOffsetTaxPct(BigDecimal offsetTaxPct) {
		this.offsetTaxPct = offsetTaxPct;
	}

	/**
	 * @return the totalCost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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
	
	public LogisticsCost convertTo(int mode){
		LogisticsCost bean = new LogisticsCost();
		bean.setBasicCost(basicCost);
		bean.setEffectiveDate(effectiveDate);
		bean.setId(id);
		bean.setNonOffsetTax(nonOffsetTax);
		bean.setNonOffsetTaxPct(nonOffsetTaxPct);
		bean.setOffsetTax(nonOffsetTax);
		bean.setOffsetTaxPct(nonOffsetTaxPct);
		bean.setRevisionControl(revisionControl);
		bean.setSvcCatgRef(svcCatgRef.getSrcSvcClassRef().getSvcClassCode());
		bean.setTaxGroupRef(taxGroupRef.getGroupCode());
		bean.setTotalCost(totalCost);
		bean.setTotalCost(totalCost);
		bean.setVolWeightMax(volWeightMax);
		bean.setVolWeightMin(volWeightMin);
		
		return bean;		
	}

}

