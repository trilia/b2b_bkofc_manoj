package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ScheduleStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ShipmentStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.TrackingStatus;

@XmlRootElement(name = "logisticstracking", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "trackingCode", "merchTenantId", "orderLineRef", "partNumber", "lineNumber", "customerRef",
		"customerCode", "shippingAddress", "postalCode", "primaryPhone", "secondrayPhone", "primaryEmail",
		"secondaryEmail", "shipmentStatus", "trackingStatus", "scheduleStatus", "baseShipCost", "offsetTax",
		"nonOffsetTax", "logistRoutings", "revisionControl" })
public class LogisticsTracking implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "logistTrackingId")
	private Long id;

	@XmlElement
	private String trackingCode;

	@XmlElement
	private String merchTenantId;

	@XmlTransient
	private String orderLineRef;

	@XmlElement
	private int partNumber;

	@XmlElement
	private int lineNumber;

	@XmlElement
	private CustomerEntity customerRef;

	@XmlElement
	private String customerCode;

	@XmlElement
	private String shippingAddress;

	@XmlElement
	private String postalCode;

	@XmlElement
	private String primaryPhone;

	@XmlElement
	private String secondaryPhone;

	@XmlElement
	private String primaryEmail;

	@XmlElement
	private String secondaryEmail;

	@XmlElement
	private ShipmentStatus shipmentStatus;

	@XmlElement
	private TrackingStatus trackingStatus;

	@XmlElement
	private ScheduleStatus scheduleStatus;

	@XmlElement
	private BigDecimal baseShipCost;

	@XmlElement
	private BigDecimal offsetTax;

	@XmlElement
	private BigDecimal nonOffsetTax;

	@XmlElement
	private List<LogisticsRoutingEntity> logistRoutings;

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
	 * @return the trackingCode
	 */
	public String getTrackingCode() {
		return trackingCode;
	}

	/**
	 * @param trackingCode
	 *            the trackingCode to set
	 */
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
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
	 * @return the orderLineRef
	 */
	public String getOrderLineRef() {
		return orderLineRef;
	}

	/**
	 * @param orderLineRef
	 *            the orderLineRef to set
	 */
	public void setOrderLineRef(String orderLineRef) {
		this.orderLineRef = orderLineRef;
	}

	/**
	 * @return the partNumber
	 */
	public int getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber
	 *            the partNumber to set
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
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the customerRef
	 */
	public CustomerEntity getCustomerRef() {
		return customerRef;
	}

	/**
	 * @param customerRef
	 *            the customerRef to set
	 */
	public void setCustomerRef(CustomerEntity customerRef) {
		this.customerRef = customerRef;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the primaryPhone
	 */
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	 * @param primaryPhone
	 *            the primaryPhone to set
	 */
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	/**
	 * @return the secondaryPhone
	 */
	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	/**
	 * @param secondaryPhone
	 *            the secondaryPhone to set
	 */
	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail() {
		return primaryEmail;
	}

	/**
	 * @param primaryEmail
	 *            the primaryEmail to set
	 */
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	/**
	 * @return the secondaryEmail
	 */
	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	/**
	 * @param secondaryEmail
	 *            the secondaryEmail to set
	 */
	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	/**
	 * @return the shipmentStatus
	 */
	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}

	/**
	 * @param shipmentStatus
	 *            the shipmentStatus to set
	 */
	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	/**
	 * @return the trackingStatus
	 */
	public TrackingStatus getTrackingStatus() {
		return trackingStatus;
	}

	/**
	 * @param trackingStatus
	 *            the trackingStatus to set
	 */
	public void setTrackingStatus(TrackingStatus trackingStatus) {
		this.trackingStatus = trackingStatus;
	}

	/**
	 * @return the scheduleStatus
	 */
	public ScheduleStatus getScheduleStatus() {
		return scheduleStatus;
	}

	/**
	 * @param scheduleStatus
	 *            the scheduleStatus to set
	 */
	public void setScheduleStatus(ScheduleStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	/**
	 * @return the baseShipCost
	 */
	public BigDecimal getBaseShipCost() {
		return baseShipCost;
	}

	/**
	 * @param baseShipCost
	 *            the baseShipCost to set
	 */
	public void setBaseShipCost(BigDecimal baseShipCost) {
		this.baseShipCost = baseShipCost;
	}

	/**
	 * @return the offsetTax
	 */
	public BigDecimal getOffsetTax() {
		return offsetTax;
	}

	/**
	 * @param offsetTax
	 *            the offsetTax to set
	 */
	public void setOffsetTax(BigDecimal offsetTax) {
		this.offsetTax = offsetTax;
	}

	/**
	 * @return the nonOffsetTax
	 */
	public BigDecimal getNonOffsetTax() {
		return nonOffsetTax;
	}

	/**
	 * @param nonOffsetTax
	 *            the nonOffsetTax to set
	 */
	public void setNonOffsetTax(BigDecimal nonOffsetTax) {
		this.nonOffsetTax = nonOffsetTax;
	}

	/**
	 * @return the logistRoutings
	 */
	public List<LogisticsRoutingEntity> getLogistRoutings() {
		return logistRoutings;
	}

	/**
	 * @param logistRoutings
	 *            the logistRoutings to set
	 */
	public void setLogistRoutings(List<LogisticsRoutingEntity> logistRoutings) {
		this.logistRoutings = logistRoutings;
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

	public LogisticsTrackingEntity convertTo(int mode) {
		LogisticsTrackingEntity bean = new LogisticsTrackingEntity();

		bean.setBaseShipCost(baseShipCost);
		bean.setCustomerCode(customerCode);
		bean.setCustomerRef(customerRef);
		bean.setId(id);
		bean.setLineNumber(lineNumber);
		bean.setLogistRoutings(logistRoutings);
		bean.setMerchTenantId(merchTenantId);
		bean.setNonOffsetTax(nonOffsetTax);
		bean.setOffsetTax(nonOffsetTax);
		SalesOrderLineEntity orderLineEntity = new SalesOrderLineEntity();
		orderLineEntity.setOrderNumber(orderLineRef);
		bean.setOrderLineRef(orderLineEntity);
		bean.setPartNumber(partNumber);
		bean.setPostalCode(postalCode);
		bean.setPrimaryEmail(primaryEmail);
		bean.setPrimaryPhone(primaryPhone);
		bean.setRevisionControl(revisionControl);
		bean.setScheduleStatus(scheduleStatus);
		bean.setSecondaryEmail(secondaryEmail);
		bean.setSecondaryPhone(secondaryPhone);
		bean.setShipmentStatus(shipmentStatus);
		bean.setTrackingCode(trackingCode);
		bean.setTrackingStatus(trackingStatus);
		bean.setShippingAddress(shippingAddress);

		return bean;
	}
}
