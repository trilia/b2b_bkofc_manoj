package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
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
import com.olp.jpa.domain.docu.inv.model.ProductSkuEntity;

/**
 * Entity implementation class for Entity: SalesOrderLineEntity
 *
 */

@Entity
@Table(name = "trl_sales_order_lines", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"order_number", "part_number", "line_number" }))
@NamedQueries({
		@NamedQuery(name = "SalesOrderLine.findByOrderLineNumber", query = "SELECT t from SalesOrderLineEntity t WHERE t.orderNumber = :orderNumber and t.partNumber = :partNumber and t.lineNumber = :lineNumber") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@FullTextFilterDef(name = "filter-salesOrderLine", impl = TenantBasedSearchFilterFactory.class)
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
@SortCriteria(attributes = { "lineNumber" })
public class SalesOrderLineEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_line_id", nullable = false)
	private Long id;

	@KeyAttribute
	@Fields({ @Field(index = Index.NO, store = Store.YES, analyze = Analyze.NO) })
	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@KeyAttribute
	@Column(name = "order_number", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String orderNumber;

	@KeyAttribute
	@Column(name = "part_number", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private int partNumber;

	@KeyAttribute
	@Column(name = "line_number", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private int lineNumber;

	@Column(name = "line_type", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.OrderLineType lineType;

	@JoinColumn(name = "order_ref")
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	@ManyToOne
	private SalesOrderEntity orderRef;

	@JoinColumn(name = "product_sku_ref")
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	@ManyToOne
	private ProductSkuEntity productSkuRef;

	@Column(name = "product_sku_code", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String productSkuCode;

	@Column(name = "order_quantity")
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private float orderQuantity;

	@Column(name = "uom")
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private String uom;

	@Column(name = "unit_rate")
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private float unitRate;

	@Column(name = "line_status", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	@Enumerated(EnumType.STRING)
	private OrderEnums.OrderLineStatus lineStatus;

	@Column(name = "return_status", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private boolean returnStatus;

	@Column(name = "return_quantity", nullable = false)
	@Fields({ @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO) })
	private float returnQuantity;

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
	 * @return the lineType
	 */
	public OrderEnums.OrderLineType getLineType() {
		return lineType;
	}

	/**
	 * @param lineType
	 *            the lineType to set
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
	 * @param orderRef
	 *            the orderRef to set
	 */
	public void setOrderRef(SalesOrderEntity orderRef) {
		this.orderRef = orderRef;
	}

	/**
	 * @return the productSkuRef
	 */
	public ProductSkuEntity getProductSkuRef() {
		return productSkuRef;
	}

	/**
	 * @param productSkuRef
	 *            the productSkuRef to set
	 */
	public void setProductSkuRef(ProductSkuEntity productSkuRef) {
		this.productSkuRef = productSkuRef;
	}

	/**
	 * @return the productSkuCode
	 */
	public String getProductSkuCode() {
		return productSkuCode;
	}

	/**
	 * @param productSkuCode
	 *            the productSkuCode to set
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
	 * @param orderQuantity
	 *            the orderQuantity to set
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
	 * @param uom
	 *            the uom to set
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
	 * @param unitRate
	 *            the unitRate to set
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
	 * @param lineStatus
	 *            the lineStatus to set
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
	 * @param returnStatus
	 *            the returnStatus to set
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
	 * @param returnQuantity
	 *            the returnQuantity to set
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public SalesOrderLine convertTo(int mode) {
		SalesOrderLine bean = new SalesOrderLine();

		if (mode < 1) {
			bean.setId(id);
		}
		bean.setTenantId(tenantId);
		bean.setOrderNumber(orderNumber);
		bean.setPartNumber(partNumber);
		bean.setLineNumber(lineNumber);
		bean.setLineType(lineType);
		bean.setOrderRef(orderRef);
		bean.setProductSkuRef(productSkuRef.getSkuCode());
		bean.setProductSkuCode(productSkuCode);
		bean.setOrderQuantity(orderQuantity);
		bean.setUom(uom);
		bean.setUnitRate(unitRate);
		bean.setLineStatus(lineStatus);
		bean.setReturnStatus(returnStatus);
		bean.setReturnQuantity(returnQuantity);

		return bean;
	}

}
