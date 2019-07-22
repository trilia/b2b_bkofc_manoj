package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.inv.model.ProductSkuEntity;

/**
 * Entity implementation class for Entity: SalesOrderLineEntity
 *
 */

public class SalesOrderLine implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="order-line-id")
	private Long id;
	
	@XmlTransient
	private String tenantId;

	@XmlElement(name="order-number")
	private String orderNumber;

	@XmlElement(name="part-number")
	private int partNumber;

	@XmlElement(name="line-number")
	private int lineNumber;

	@XmlElement(name="line-type")
	private OrderEnums.OrderLineType lineType;

	@XmlElement(name="order-ref")
	private SalesOrderEntity orderRef;

	@XmlElement(name="product-sku-ref")
	private String productSkuRef;
	
	@XmlElement(name="product-sku-code")
	private String productSkuCode;

	@XmlElement(name="order-quantity")
	private float orderQuantity;

	@XmlElement(name="uom")
	private String uom;

	@XmlElement(name="unit-rate")
	private float unitRate;

	@XmlElement(name="line-status")
	private OrderEnums.OrderLineStatus lineStatus;

	@XmlElement(name="return-status")
	private boolean returnStatus;

	@XmlElement(name="return-quantity")
	private float returnQuantity;

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
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the partNumber
	 */
	public int getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the lineType
	 */
	public OrderEnums.OrderLineType getLineType() {
		return lineType;
	}

	/**
	 * @param lineType the lineType to set
	 */
	public void setLineType(OrderEnums.OrderLineType lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return the orderRef
	 */
	public SalesOrderEntity getOrderRef() {
		return orderRef;
	}

	/**
	 * @param orderRef the orderRef to set
	 */
	public void setOrderRef(SalesOrderEntity orderRef) {
		this.orderRef = orderRef;
	}

	/**
	 * @return the productSkuRef
	 */
	public String getProductSkuRef() {
		return productSkuRef;
	}

	/**
	 * @param productSkuRef the productSkuRef to set
	 */
	public void setProductSkuRef(String productSkuRef) {
		this.productSkuRef = productSkuRef;
	}

	/**
	 * @return the productSkuCode
	 */
	public String getProductSkuCode() {
		return productSkuCode;
	}

	/**
	 * @param productSkuCode the productSkuCode to set
	 */
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	/**
	 * @return the orderQuantity
	 */
	public float getOrderQuantity() {
		return orderQuantity;
	}

	/**
	 * @param orderQuantity the orderQuantity to set
	 */
	public void setOrderQuantity(float orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * @return the unitRate
	 */
	public float getUnitRate() {
		return unitRate;
	}

	/**
	 * @param unitRate the unitRate to set
	 */
	public void setUnitRate(float unitRate) {
		this.unitRate = unitRate;
	}

	/**
	 * @return the lineStatus
	 */
	public OrderEnums.OrderLineStatus getLineStatus() {
		return lineStatus;
	}

	/**
	 * @param lineStatus the lineStatus to set
	 */
	public void setLineStatus(OrderEnums.OrderLineStatus lineStatus) {
		this.lineStatus = lineStatus;
	}

	/**
	 * @return the returnStatus
	 */
	public boolean isReturnStatus() {
		return returnStatus;
	}

	/**
	 * @param returnStatus the returnStatus to set
	 */
	public void setReturnStatus(boolean returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * @return the returnQuantity
	 */
	public float getReturnQuantity() {
		return returnQuantity;
	}

	/**
	 * @param returnQuantity the returnQuantity to set
	 */
	public void setReturnQuantity(float returnQuantity) {
		this.returnQuantity = returnQuantity;
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

	public SalesOrderLineEntity convertTo(int mode) {
		SalesOrderLineEntity bean = new SalesOrderLineEntity();

		if (mode < 1) {
			bean.setId(id);
		}
		bean.setTenantId(tenantId);
		bean.setOrderNumber(orderNumber);
		bean.setPartNumber(partNumber);
		bean.setLineNumber(lineNumber);
		bean.setLineType(lineType);
		bean.setOrderRef(orderRef);
		bean.setOrderNumber(orderNumber);
		bean.setProductSkuCode(productSkuCode);
		bean.setOrderQuantity(orderQuantity);
		bean.setUom(uom);
		bean.setUnitRate(unitRate);
		bean.setLineStatus(lineStatus);
		bean.setReturnStatus(returnStatus);
		bean.setReturnQuantity(returnQuantity);
		
		
		if (!(this.productSkuRef == null || "".equals(this.productSkuRef))) {
			ProductSkuEntity productSkuEntity = new ProductSkuEntity();
			productSkuEntity.setSkuCode(productSkuRef);
			bean.setProductSkuRef(productSkuEntity);
        }
		
		if (mode <= 0) {
			bean.setRevisionControl(revisionControl);
		}
		return bean;
	}
   
}
