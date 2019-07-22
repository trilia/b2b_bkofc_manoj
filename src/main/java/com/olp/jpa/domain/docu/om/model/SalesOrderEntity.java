package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.annotations.SortCriteria;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
import com.olp.jpa.domain.docu.cs.model.CustomerEntity;
import com.olp.jpa.domain.docu.po.model.PurchaseOrderLineEntity;

/**
 * @author Jayesh
 *
 */
@Entity
@Table(name = "trl_sales_orders", uniqueConstraints = {
		@javax.persistence.UniqueConstraint(columnNames = { "tenant_id", "order_number" }) })
@Indexed(index = "SetupDataIndex")
@FullTextFilterDef(name = "filter-salesorder", impl = TenantBasedSearchFilterFactory.class)
@NamedQueries({
		@javax.persistence.NamedQuery(name = "SalesOrder.findByOrderNumber", query = "SELECT t from SalesOrderEntity t WHERE t.orderNumber = :orderNumber and t.orderPart = :partNumber and t.tenantId = :tenant") })
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
@SortCriteria(attributes = { "orderNumber" })
public class SalesOrderEntity implements Serializable {

	private static final long serialVersionUID = -2195457661876745924L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", nullable = false)
	private Long id;

	@KeyAttribute
	@Fields({ @Field(index = Index.NO, store = Store.YES, analyze = Analyze.NO) })
	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@KeyAttribute
	@Column(name = "order_number", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String orderNumber;

	@Column(name = "order_part", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private int orderPart;

	@Column(name = "order_date", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(name = "order_source", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.OrderSource orderSource;

	@Column(name = "order_type", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.OrderType orderType;

	@Column(name = "delivery_type", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.DeliveryType deliveryType;

	@Column(name = "deliver-by-date", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Temporal(TemporalType.DATE)
	private Date deliverByDate;

	@Column(name = "order_status", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name = "parent_order_ref")
	@ContainedIn
	private SalesOrderEntity parentOrderRef;

	@Column(name = "parent_order_num")
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String parentOrderNum;

	@ManyToOne
	@JoinColumn(name = "customer_ref")
	@ContainedIn
	private CustomerEntity customerRef;

	@Column(name = "customer_code", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String customerCode;

	@Column(name = "shipping_Address", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String shippingAddress;

	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

	@OneToMany(mappedBy = "orderRef", cascade = CascadeType.ALL)
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<SalesOrderLineEntity> orderLines;

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
	 * @return the orderPart
	 */
	public int getOrderPart() {
		return orderPart;
	}

	/**
	 * @param orderPart
	 *            the orderPart to set
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
	 * @param orderDate
	 *            the orderDate to set
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
	 * @param orderSource
	 *            the orderSource to set
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
	 * @param orderType
	 *            the orderType to set
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
	 * @param deliveryType
	 *            the deliveryType to set
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
	 * @param deliverByDate
	 *            the deliverByDate to set
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
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(OrderEnums.OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the parentOrderRef
	 */
	public SalesOrderEntity getParentOrderRef() {
		return parentOrderRef;
	}

	/**
	 * @param parentOrderRef
	 *            the parentOrderRef to set
	 */
	public void setParentOrderRef(SalesOrderEntity parentOrderRef) {
		this.parentOrderRef = parentOrderRef;
	}

	/**
	 * @return the parentOrderNum
	 */
	public String getParentOrderNum() {
		return parentOrderNum;
	}

	/**
	 * @param parentOrderNum
	 *            the parentOrderNum to set
	 */
	public void setParentOrderNum(String parentOrderNum) {
		this.parentOrderNum = parentOrderNum;
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

	/**
	 * @return the orderLines
	 */
	public List<SalesOrderLineEntity> getOrderLines() {
		return orderLines;
	}

	/**
	 * @param orderLines
	 *            the orderLines to set
	 */
	public void setOrderLines(List<SalesOrderLineEntity> orderLines) {
		this.orderLines = orderLines;
	}

	public SalesOrder convertTo(int mode) {
		SalesOrder bean = new SalesOrder();

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
		bean.setParentOrderRef(parentOrderRef.getOrderNumber());
		bean.setCustomerRef(customerRef.getCustomerCode());
		bean.setShippingAddress(shippingAddress);

		if (mode <= 0) {
			bean.setRevisionControl(revisionControl);
		}
		return bean;
	}

}
