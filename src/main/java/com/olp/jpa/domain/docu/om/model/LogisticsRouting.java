package com.olp.jpa.domain.docu.om.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.search.annotations.IndexedEmbedded;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.RoutingStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ScheduleStatus;

@XmlRootElement(name = "logisticsrouting", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "trackingCode", "routingSequence", "logistTrackingRef", "logistPartnerRef", "isPickupFunc",
		"isIntermediateFnc", "isLastMileFunc", "sourceAddress", "sourcePostalCode", "destAddress", "destPostalCode",
		"scheduleStatus", "routingStatus", "routingCost", "offsetTax", "nonOffsetTax", "revisionControl" })
public class LogisticsRouting implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "logistRoutingId")
	private Long id;

	@XmlElement
	private String trackingCode;

	@XmlElement
	private int routingSequence;

	@XmlElement
	private LogisticsTrackingEntity logistTrackingRef;

	@XmlElement
	private String logistTrackingCode;

	@XmlElement
	private LogisticPartnerEntity logistPartnerRef;

	@XmlElement
	private String logistPartnerCode;

	@XmlElement
	private boolean isPickupFunc;

	@XmlElement
	private boolean isIntermediateFnc;

	@XmlElement
	private boolean isLastMileFunc;

	@XmlElement
	private String sourceAddress;

	@XmlElement
	private String sourcePostalCode;

	@XmlElement
	private String destAddress;

	@XmlElement
	private String destPostalCode;

	@XmlElement
	private ScheduleStatus scheduleStatus;

	@XmlElement
	private RoutingStatus routingStatus;

	@XmlElement
	private String offsetTax;

	@XmlElement
	private String nonOffsetTax;

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
	 * @return the routingSequence
	 */
	public int getRoutingSequence() {
		return routingSequence;
	}

	/**
	 * @param routingSequence
	 *            the routingSequence to set
	 */
	public void setRoutingSequence(int routingSequence) {
		this.routingSequence = routingSequence;
	}

	/**
	 * @return the logistTrackingRef
	 */
	public LogisticsTrackingEntity getLogistTrackingRef() {
		return logistTrackingRef;
	}

	/**
	 * @param logistTrackingRef
	 *            the logistTrackingRef to set
	 */
	public void setLogistTrackingRef(LogisticsTrackingEntity logistTrackingRef) {
		this.logistTrackingRef = logistTrackingRef;
	}

	/**
	 * @return the logistTrackingCode
	 */
	public String getLogistTrackingCode() {
		return logistTrackingCode;
	}

	/**
	 * @param logistTrackingCode
	 *            the logistTrackingCode to set
	 */
	public void setLogistTrackingCode(String logistTrackingCode) {
		this.logistTrackingCode = logistTrackingCode;
	}

	/**
	 * @return the logistPartnerRef
	 */
	public LogisticPartnerEntity getLogistPartnerRef() {
		return logistPartnerRef;
	}

	/**
	 * @param logistPartnerRef
	 *            the logistPartnerRef to set
	 */
	public void setLogistPartnerRef(LogisticPartnerEntity logistPartnerRef) {
		this.logistPartnerRef = logistPartnerRef;
	}

	/**
	 * @return the logistPartnerCode
	 */
	public String getLogistPartnerCode() {
		return logistPartnerCode;
	}

	/**
	 * @param logistPartnerCode
	 *            the logistPartnerCode to set
	 */
	public void setLogistPartnerCode(String logistPartnerCode) {
		this.logistPartnerCode = logistPartnerCode;
	}

	/**
	 * @return the isPickupFunc
	 */
	public boolean isPickupFunc() {
		return isPickupFunc;
	}

	/**
	 * @param isPickupFunc
	 *            the isPickupFunc to set
	 */
	public void setPickupFunc(boolean isPickupFunc) {
		this.isPickupFunc = isPickupFunc;
	}

	/**
	 * @return the isIntermediateFnc
	 */
	public boolean isIntermediateFnc() {
		return isIntermediateFnc;
	}

	/**
	 * @param isIntermediateFnc
	 *            the isIntermediateFnc to set
	 */
	public void setIntermediateFnc(boolean isIntermediateFnc) {
		this.isIntermediateFnc = isIntermediateFnc;
	}

	/**
	 * @return the isLastMileFunc
	 */
	public boolean isLastMileFunc() {
		return isLastMileFunc;
	}

	/**
	 * @param isLastMileFunc
	 *            the isLastMileFunc to set
	 */
	public void setLastMileFunc(boolean isLastMileFunc) {
		this.isLastMileFunc = isLastMileFunc;
	}

	/**
	 * @return the sourceAddress
	 */
	public String getSourceAddress() {
		return sourceAddress;
	}

	/**
	 * @param sourceAddress
	 *            the sourceAddress to set
	 */
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	/**
	 * @return the sourcePostalCode
	 */
	public String getSourcePostalCode() {
		return sourcePostalCode;
	}

	/**
	 * @param sourcePostalCode
	 *            the sourcePostalCode to set
	 */
	public void setSourcePostalCode(String sourcePostalCode) {
		this.sourcePostalCode = sourcePostalCode;
	}

	/**
	 * @return the destAddress
	 */
	public String getDestAddress() {
		return destAddress;
	}

	/**
	 * @param destAddress
	 *            the destAddress to set
	 */
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	/**
	 * @return the destPostalCode
	 */
	public String getDestPostalCode() {
		return destPostalCode;
	}

	/**
	 * @param destPostalCode
	 *            the destPostalCode to set
	 */
	public void setDestPostalCode(String destPostalCode) {
		this.destPostalCode = destPostalCode;
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
	 * @return the routingStatus
	 */
	public RoutingStatus getRoutingStatus() {
		return routingStatus;
	}

	/**
	 * @param routingStatus
	 *            the routingStatus to set
	 */
	public void setRoutingStatus(RoutingStatus routingStatus) {
		this.routingStatus = routingStatus;
	}

	/**
	 * @return the offsetTax
	 */
	public String getOffsetTax() {
		return offsetTax;
	}

	/**
	 * @param offsetTax
	 *            the offsetTax to set
	 */
	public void setOffsetTax(String offsetTax) {
		this.offsetTax = offsetTax;
	}

	/**
	 * @return the nonOffsetTax
	 */
	public String getNonOffsetTax() {
		return nonOffsetTax;
	}

	/**
	 * @param nonOffsetTax
	 *            the nonOffsetTax to set
	 */
	public void setNonOffsetTax(String nonOffsetTax) {
		this.nonOffsetTax = nonOffsetTax;
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

	public LogisticsRoutingEntity convertTo(int mode) {
		LogisticsRoutingEntity bean = new LogisticsRoutingEntity();
		bean.setDestAddress(destAddress);
		bean.setDestPostalCode(destPostalCode);
		bean.setId(id);
		bean.setIntermediateFnc(isIntermediateFnc);
		bean.setLastMileFunc(isLastMileFunc);
		bean.setLogistPartnerCode(logistPartnerCode);
		bean.setLogistPartnerRef(logistPartnerRef);
		bean.setLogistTrackingCode(logistTrackingCode);
		bean.setLogistTrackingRef(logistTrackingRef);
		bean.setNonOffsetTax(nonOffsetTax);
		bean.setOffsetTax(offsetTax);
		bean.setPickupFunc(isPickupFunc);
		bean.setRevisionControl(revisionControl);
		bean.setRoutingSequence(routingSequence);
		bean.setRoutingStatus(routingStatus);
		bean.setScheduleStatus(scheduleStatus);
		bean.setSourceAddress(sourceAddress);
		bean.setSourcePostalCode(sourcePostalCode);

		return bean;
	}
}
