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
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.MerchantEntity;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderLineStatus;

@Entity
@Table(name = "trl_comp_order_lines", uniqueConstraints = @UniqueConstraint(columnNames = { "comp_order_num",
		"line_num" }))
@NamedQueries({
		@NamedQuery(name = "CompositeOrderLineEntity.findByCompOrderLine", query = "SELECT t FROM CompositeOrderLineEntity t WHERE t.compOrderNum = :compOrderNum and t.lineNum = :lineNum ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ALLOW_GLOBAL)
public class CompositeOrderLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comp_order_line_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "line_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Integer lineNum;

	@ManyToOne
	@JoinColumn(name = "comp_order_ref")
	@ContainedIn
	private CompositeOrderEntity compOrderRef;

	@Column(name = "comp_order_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String compOrderNum;

	@ManyToOne
	@JoinColumn(name = "merchant_ref")
	@ContainedIn
	private MerchantEntity merchantRef;

	@Column(name = "merch_tenant_id", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String merchTenantId;

	@ManyToOne
	@JoinColumn(name = "sales_order_ref")
	@ContainedIn
	private SalesOrderEntity salesOrderRef;

	@Column(name = "sales_order_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String salesOrderNum;

	@Column(name = "order_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private CompOrderLineStatus orderStatus;

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
	public MerchantEntity getMerchantRef() {
		return merchantRef;
	}

	/**
	 * @param merchantRef
	 *            the merchantRef to set
	 */
	public void setMerchantRef(MerchantEntity merchantRef) {
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
	public SalesOrderEntity getSalesOrderRef() {
		return salesOrderRef;
	}

	/**
	 * @param salesOrderRef
	 *            the salesOrderRef to set
	 */
	public void setSalesOrderRef(SalesOrderEntity salesOrderRef) {
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

	public CompositeOrderLine convertTo(int mode) {
		CompositeOrderLine bean = new CompositeOrderLine();
		bean.setId(id);
		bean.setCompOrderNum(compOrderNum);
		bean.setCompOrderRef(compOrderRef);
		bean.setLineNum(lineNum);
		bean.setMerchantRef(merchantRef.getTenantId());
		bean.setMerchTenantId(merchantRef.getTenantId());// need to check
		bean.setOrderStatus(orderStatus);
		bean.setSalesOrderNum(salesOrderNum);
		bean.setSalesOrderRef(salesOrderRef.getOrderNumber());
		bean.setRevisionControl(revisionControl);

		return bean;
	}
}
