package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderStatus;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentMethod;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentStatus;

@Entity
@Table(name = "trl_comp_order")
@NamedQueries({
		@NamedQuery(name = "CompositeOrderEntity.findByCompOrderNum", query = "SELECT t FROM CompositeOrderEntity t WHERE t.compOrderNum = :compOrderNum") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ALLOW_GLOBAL)
public class CompositeOrderEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ledger_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "comp_order_num", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String compOrderNum;

	@Column(name = "comp_order_date", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date compOrderDate;

	@Column(name = "comp_order_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private CompOrderStatus compOrderStatus;

	@Column(name = "order_value", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private BigDecimal orderValue;

	@Column(name = "payment_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private PaymentStatus paymentStatus;

	@Column(name = "payment_method", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private PaymentMethod paymentMethod;

	@OneToMany(mappedBy = "orderLinesRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<CompositeOrderLineEntity> orderLines = new ArrayList<>();

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
	 * @return the compOrderDate
	 */
	public Date getCompOrderDate() {
		return compOrderDate;
	}

	/**
	 * @param compOrderDate
	 *            the compOrderDate to set
	 */
	public void setCompOrderDate(Date compOrderDate) {
		this.compOrderDate = compOrderDate;
	}

	/**
	 * @return the compOrderStatus
	 */
	public CompOrderStatus getCompOrderStatus() {
		return compOrderStatus;
	}

	/**
	 * @param compOrderStatus
	 *            the compOrderStatus to set
	 */
	public void setCompOrderStatus(CompOrderStatus compOrderStatus) {
		this.compOrderStatus = compOrderStatus;
	}

	/**
	 * @return the orderValue
	 */
	public BigDecimal getOrderValue() {
		return orderValue;
	}

	/**
	 * @param orderValue
	 *            the orderValue to set
	 */
	public void setOrderValue(BigDecimal orderValue) {
		this.orderValue = orderValue;
	}

	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus
	 *            the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the orderLines
	 */
	public List<CompositeOrderLineEntity> getOrderLines() {
		return orderLines;
	}

	/**
	 * @param orderLines
	 *            the orderLines to set
	 */
	public void setOrderLines(List<CompositeOrderLineEntity> orderLines) {
		this.orderLines = orderLines;
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

	public CompositeOrder convertTo(int mode) {
		CompositeOrder bean = new CompositeOrder();
		bean.setId(id);
		bean.setCompOrderDate(compOrderDate);
		bean.setCompOrderNum(compOrderNum);
		bean.setCompOrderStatus(compOrderStatus);
		bean.setOrderLines(orderLines);
		bean.setOrderValue(orderValue);
		bean.setPaymentMethod(paymentMethod);
		bean.setPaymentStatus(paymentStatus);
		bean.setRevisionControl(revisionControl);

		return bean;
	}

}
