package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ScheduleStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ShipmentStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.TrackingStatus;

@Entity
@Table(name = "trl_logistics_tracking")
@NamedQueries({
		@NamedQuery(name = "LogisticsTrackingEntity.findByTrackingCode", query = "SELECT t FROM LogisticsTrackingEntity t WHERE t.trackingCode = :trackingCode"),
		@NamedQuery(name = "LogisticsTrackingEntity.findByOrderLine", query = "SELECT t FROM LogisticsTrackingEntity t WHERE t.merchTenantId = :merchTenantId and t.orderNumber = :orderNumber and t.partNumber =:partNumber") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class LogisticsTrackingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "logist_tracking_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tracking_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String trackingCode;

	@Column(name = "merch_tenant_id", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String merchTenantId;

	@ManyToOne
	@JoinColumn(name = "order_line_ref")
	@ContainedIn
	private SalesOrderLineEntity orderLineRef;

	@Column(name = "order_number", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String orderNumber;

	@Column(name = "part_number", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private int partNumber;

	@Column(name = "line_number", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private int lineNumber;

	@ManyToOne
	@JoinColumn(name = "customer_ref")
	@ContainedIn
	private CustomerEntity customerRef;

	@Column(name = "customer_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String customerCode;

	@Column(name = "shipping_address", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String shippingAddress;

	@Column(name = "postal_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String postalCode;

	@Column(name = "primary_phone", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String primaryPhone;

	@Column(name = "secondary_phone", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String secondaryPhone;

	@Column(name = "primary_email", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String primaryEmail;

	@Column(name = "secondary_email", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String secondaryEmail;

	@Column(name = "shipment_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private ShipmentStatus shipmentStatus;

	@Column(name = "tracking_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private TrackingStatus trackingStatus;

	@Column(name = "schedule_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private ScheduleStatus scheduleStatus;

	@Column(name = "base_ship_cost", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal baseShipCost;

	@Column(name = "offset_tax", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal offsetTax;

	@Column(name = "non_offset_tax", nullable = true)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private BigDecimal nonOffsetTax;

	@OneToMany(mappedBy = "logistTrackingRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<LogisticsRoutingEntity> logistRoutings;

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
	public SalesOrderLineEntity getOrderLineRef() {
		return orderLineRef;
	}

	/**
	 * @param orderLineRef
	 *            the orderLineRef to set
	 */
	public void setOrderLineRef(SalesOrderLineEntity orderLineRef) {
		this.orderLineRef = orderLineRef;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
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

	public LogisticsTracking convertTo(int mode) {
		LogisticsTracking bean = new LogisticsTracking();

		bean.setBaseShipCost(baseShipCost);
		bean.setCustomerCode(customerCode);
		bean.setCustomerRef(customerRef);
		bean.setId(id);
		bean.setLineNumber(lineNumber);
		bean.setLogistRoutings(logistRoutings);
		bean.setMerchTenantId(merchTenantId);
		bean.setNonOffsetTax(nonOffsetTax);
		bean.setOffsetTax(nonOffsetTax);
		bean.setOrderLineRef(orderLineRef.getOrderNumber());
		bean.setPartNumber(partNumber);
		bean.setPostalCode(postalCode);
		bean.setPrimaryEmail(primaryEmail);
		bean.setPrimaryPhone(primaryPhone);
		bean.setRevisionControl(revisionControl);
		bean.setScheduleStatus(scheduleStatus);
		bean.setSecondaryEmail(secondaryEmail);
		bean.setSecondaryPhone(secondaryPhone);
		bean.setShipmentStatus(shipmentStatus);
		bean.setShippingAddress(shippingAddress);
		bean.setTrackingCode(trackingCode);
		bean.setTrackingStatus(trackingStatus);

		return bean;
	}
}
