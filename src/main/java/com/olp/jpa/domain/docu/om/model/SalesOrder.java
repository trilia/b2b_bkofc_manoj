/**
 * 
 */
package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;

/**
 * @author Jayesh
 *
 */
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = -8054125191916739990L;

	@XmlElement(name="order-id")
	private Long id;
	
	@XmlTransient
	private String tenantId;
	
	@XmlElement(name="order-number")
	private String orderNumber;
	
	@XmlElement(name="order-part")
	private int orderPart;

	@XmlElement(name="order-date")
	private Date orderDate;

	@XmlElement(name="order-source")
	private OrderEnums.OrderSource orderSource;

	@XmlElement(name="order-type")
	private OrderEnums.OrderType orderType;

	@XmlElement(name="delivery-type")
	private OrderEnums.DeliveryType deliveryType;

	@XmlElement(name="deliver-by-date")
	private Date deliverByDate;

	@XmlElement(name="order-status")
	private OrderEnums.OrderStatus orderStatus;

	@XmlElement(name="parent-order-ref")
	private String parentOrderRef;

	@XmlElement(name="customer-ref")
	private String customerRef;

	@XmlElement(name="shipping-address")
	private String shippingAddress;
	
	@XmlElement(name="order-lines")
	private List<String> orderLines;
	
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
	 * @return the orderPart
	 */
	public int getOrderPart() {
		return orderPart;
	}

	/**
	 * @param orderPart the orderPart to set
	 */
	public void setOrderPart(int orderPart) {
		this.orderPart = orderPart;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the orderSource
	 */
	public OrderEnums.OrderSource getOrderSource() {
		return orderSource;
	}

	/**
	 * @param orderSource the orderSource to set
	 */
	public void setOrderSource(OrderEnums.OrderSource orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * @return the orderType
	 */
	public OrderEnums.OrderType getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(OrderEnums.OrderType orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the deliveryType
	 */
	public OrderEnums.DeliveryType getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(OrderEnums.DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the deliverByDate
	 */
	public Date getDeliverByDate() {
		return deliverByDate;
	}

	/**
	 * @param deliverByDate the deliverByDate to set
	 */
	public void setDeliverByDate(Date deliverByDate) {
		this.deliverByDate = deliverByDate;
	}

	/**
	 * @return the orderStatus
	 */
	public OrderEnums.OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(OrderEnums.OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the parentOrderRef
	 */
	public String getParentOrderRef() {
		return parentOrderRef;
	}

	/**
	 * @param parentOrderRef the parentOrderRef to set
	 */
	public void setParentOrderRef(String parentOrderRef) {
		this.parentOrderRef = parentOrderRef;
	}

	/**
	 * @return the customerRef
	 */
	public String getCustomerRef() {
		return customerRef;
	}

	/**
	 * @param customerRef the customerRef to set
	 */
	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}

	/**
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the orderLines
	 */
	public List<String> getOrderLines() {
		return orderLines;
	}

	/**
	 * @param orderLines the orderLines to set
	 */
	public void setOrderLines(List<String> orderLines) {
		this.orderLines = orderLines;
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
	
	public SalesOrderEntity convertTo(int mode) {
		SalesOrderEntity bean = new SalesOrderEntity();

		if (mode < 1) {
			bean.setId(id);
		}
		bean.setTenantId(tenantId);
		bean.setOrderNumber(orderNumber);
		bean.setOrderPart(orderPart);
		bean.setOrderDate(orderDate);
		bean.setOrderSource(orderSource);
		bean.setOrderType(orderType);
		bean.setDeliveryType(deliveryType);
		bean.setDeliverByDate(deliverByDate);
		bean.setOrderStatus(orderStatus);
		//bean.setParentOrderNum(parentOrderNum);
		//bean.setCustomerCode(customerCode);
		bean.setShippingAddress(shippingAddress);
		
		if (!(this.parentOrderRef == null || "".equals(this.parentOrderRef))) {
			SalesOrderEntity salesOrderBean = new SalesOrderEntity();
			salesOrderBean.setOrderNumber(parentOrderRef);
			bean.setParentOrderRef(salesOrderBean);
			bean.setParentOrderNum(salesOrderBean.getOrderNumber());
        }
		
		if (!(this.customerRef == null || "".equals(this.customerRef))) {
			CustomerEntity customerBean = new CustomerEntity();
			customerBean.setCustomerCode(customerRef);
			bean.setCustomerRef(customerBean);
			bean.setCustomerCode(customerBean.getCustomerCode());
        }
		
		if (this.orderLines != null) {
            ArrayList<SalesOrderLineEntity> soLines = new ArrayList<>();
            for (String lineNumber : this.orderLines) {
            	SalesOrderLineEntity soLineEntity = new SalesOrderLineEntity();
            	soLineEntity.setOrderNumber(orderNumber);
            	soLines.add(soLineEntity);
            }
            bean.setOrderLines(soLines);
        }
		
		if (mode <= 0) {
			bean.setRevisionControl(revisionControl);
		}
		return bean;
	}
}
