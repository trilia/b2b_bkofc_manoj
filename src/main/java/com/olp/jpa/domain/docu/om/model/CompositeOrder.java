package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.om.model.OmEnums.CompOrderStatus;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentMethod;
import com.olp.jpa.domain.docu.om.model.OmEnums.PaymentStatus;

@XmlRootElement(name = "compositeorder", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "compOrderNum", "compOrderDate", "compOrderStatus", "orderValue",
		"paymentStatus", "paymentMethod", "orderLines", "revisionControl" })
public class CompositeOrder implements Serializable{
	
	private static final long serialVersionUID = -1L;

	@XmlElement(name = "compOrderId")
	private Long id;

	@XmlElement
	private String compOrderNum;

	@XmlElement
	private Date compOrderDate;

	@XmlElement
	private CompOrderStatus compOrderStatus;

	@XmlElement
	private BigDecimal orderValue;
	
	@XmlElement
	private PaymentStatus paymentStatus;
	
	@XmlElement
	private PaymentMethod paymentMethod;

	@XmlElement
	private List<CompositeOrderLineEntity> orderLines;

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
	 * @return the compOrderNum
	 */
	public String getCompOrderNum() {
		return compOrderNum;
	}

	/**
	 * @param compOrderNum the compOrderNum to set
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
	 * @param compOrderDate the compOrderDate to set
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
	 * @param compOrderStatus the compOrderStatus to set
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
	 * @param orderValue the orderValue to set
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
	 * @param paymentStatus the paymentStatus to set
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
	 * @param paymentMethod the paymentMethod to set
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
	 * @param orderLines the orderLines to set
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
	 * @param revisionControl the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}
	
	public CompositeOrder convertTo(int mode){
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
