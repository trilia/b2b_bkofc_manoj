package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "logisticsCost", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "svcCatgRef", "volWeightMin", "volWeightMax", "basicCost", "taxGroupRef",
		"nonOffsetTax", "nonOffsetTaxPct", "offsetTax", "offsetTaxPct",
		"totalCost", "effectiveDate", "lifeCycleStatus", "revisionControl"})
public class LogisticsCost implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "svcCostId")
	private Long id;
	
	@XmlElement
	private String svcCatgRef;

	@XmlElement
	private BigDecimal volWeightMin;
	
	@XmlElement
	private BigDecimal volWeightMax;
	
	@XmlElement
	private BigDecimal basicCost;

	@XmlElement
	private String taxGroupRef;
	
	@XmlElement
	private BigDecimal nonOffsetTax;
	
	@XmlElement
	private BigDecimal nonOffsetTaxPct;
	
	@XmlElement
	private BigDecimal offsetTax;
	
	@XmlElement
	private BigDecimal offsetTaxPct;
	
	@XmlElement
	private BigDecimal totalCost;
	
	@XmlElement
	private Date effectiveDate;
	
	@XmlElement
	private LifeCycleStatus lifeCycleStatus;
	
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
	public String getSvcCatgRef() {
		return svcCatgRef;
	}

	/**
	 * @param svcCatgRef the svcCatgRef to set
	 */
	public void setSvcCatgRef(String svcCatgRef) {
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
	public String getTaxGroupRef() {
		return taxGroupRef;
	}

	/**
	 * @param taxGroupRef the taxGroupRef to set
	 */
	public void setTaxGroupRef(String taxGroupRef) {
		this.taxGroupRef = taxGroupRef;
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
	 * @return the lifeCycleStatus
	 */
	public LifeCycleStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	/**
	 * @param lifeCycleStatus the lifeCycleStatus to set
	 */
	public void setLifeCycleStatus(LifeCycleStatus lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
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
	
	public LogisticsCostEntity convertTo(int mode){
		LogisticsCostEntity bean = new LogisticsCostEntity();
		
		return bean;
	}

	
}
