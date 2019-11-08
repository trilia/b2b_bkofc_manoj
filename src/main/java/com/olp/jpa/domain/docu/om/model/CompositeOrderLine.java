package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.MerchantEntity;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderLineStatus;

@XmlRootElement(name = "compositeorderline", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "lineNum", "compOrderRef", "compOrderNum", "merchantRef", "merchTenantId", "salesOrderRef",
		"salesOrderNum", "orderStatus", "revisionControl" })
public class CompositeOrderLine implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "compOrderLineId")
	private Long id;

	@XmlElement
	private Integer lineNum;

	@XmlElement
	private CompositeOrderEntity compOrderRef;

	@XmlElement
	private String compOrderNum;

	@XmlElement
	private String merchantRef;

	@XmlElement
	private String merchTenantId;

	@XmlElement
	private String salesOrderRef;

	@XmlElement
	private String salesOrderNum;

	@XmlElement
	private CompOrderLineStatus orderStatus;

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
	 * @return the compOrderRef
	 */
	public CompositeOrderEntity getCompOrderRef() {
		return compOrderRef;
	}

	/**
	 * @param compOrderRef
	 *            the compOrderRef to set
	 */
	public void setCompOrderRef(CompositeOrderEntity compOrderRef) {
		this.compOrderRef = compOrderRef;
	}

	/**
	 * @return the compOrderNum
	 */
	public String getCompOrderNum() {
		return compOrderNum;
	}

	/**
	 * @param compOrderNum
	 *            the compOrderNum to set
	 */
	public void setCompOrderNum(String compOrderNum) {
		this.compOrderNum = compOrderNum;
	}

	/**
	 * @return the merchantRef
	 */
	public String getMerchantRef() {
		return merchantRef;
	}

	/**
	 * @param merchantRef
	 *            the merchantRef to set
	 */
	public void setMerchantRef(String merchantRef) {
		this.merchantRef = merchantRef;
	}

	/**
	 * @return the merchTenantId
	 */
	public String getMerchTenantId() {
		return merchTenantId;
	}

	/**
	 * @param merchTenantId
	 *            the merchTenantId to set
	 */
	public void setMerchTenantId(String merchTenantId) {
		this.merchTenantId = merchTenantId;
	}

	/**
	 * @return the salesOrderRef
	 */
	public String getSalesOrderRef() {
		return salesOrderRef;
	}

	/**
	 * @param salesOrderRef
	 *            the salesOrderRef to set
	 */
	public void setSalesOrderRef(String salesOrderRef) {
		this.salesOrderRef = salesOrderRef;
	}

	/**
	 * @return the salesOrderNum
	 */
	public String getSalesOrderNum() {
		return salesOrderNum;
	}

	/**
	 * @param salesOrderNum
	 *            the salesOrderNum to set
	 */
	public void setSalesOrderNum(String salesOrderNum) {
		this.salesOrderNum = salesOrderNum;
	}

	/**
	 * @return the orderStatus
	 */
	public CompOrderLineStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(CompOrderLineStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	public CompositeOrderLineEntity convertTo(int mode) {
		CompositeOrderLineEntity bean = new CompositeOrderLineEntity();
		bean.setId(id);
		bean.setCompOrderNum(compOrderNum);
		bean.setCompOrderRef(compOrderRef);
		bean.setLineNum(lineNum);
		bean.setMerchTenantId(merchTenantId);
		bean.setOrderStatus(orderStatus);
		bean.setSalesOrderNum(salesOrderNum);
		bean.setRevisionControl(revisionControl);

		return bean;
	}

}
