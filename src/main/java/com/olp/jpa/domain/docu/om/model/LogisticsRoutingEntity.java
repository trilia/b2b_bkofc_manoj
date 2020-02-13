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
import com.olp.jpa.domain.docu.be.model.LogisticPartnerEntity;
import com.olp.jpa.domain.docu.om.model.LogistEnums.RoutingStatus;
import com.olp.jpa.domain.docu.om.model.LogistEnums.ScheduleStatus;

@Entity
@Table(name = "trl_logistics_routing")
@NamedQueries({
		@NamedQuery(name = "LogisticRoutingEntity.findByTrackingCode", query = "SELECT t FROM LogisticsRoutingEntity t WHERE t.trackingCode = :trackingCode and t.routingSequence = :routingSequence") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class LogisticsRoutingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "logist_routing_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tracking_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String trackingCode;

	@KeyAttribute
	@Column(name = "routing_sequence", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private int routingSequence;

	@ManyToOne
	@JoinColumn(name = "logist_tracking_ref")
	@ContainedIn
	private LogisticsTrackingEntity logistTrackingRef;

	@Column(name = "logist_tracking_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String logistTrackingCode;

	@ManyToOne
	@JoinColumn(name = "logist_partner_ref")
	@ContainedIn
	private LogisticPartnerEntity logistPartnerRef;

	@Column(name = "logist_partner_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String logistPartnerCode;

	@Column(name = "is_pickup_func", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private boolean isPickupFunc;

	@Column(name = "is_intermediate_func", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private boolean isIntermediateFnc;

	@Column(name = "is_lastmile_func", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private boolean isLastMileFunc;

	@Column(name = "source_address", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String sourceAddress;

	@Column(name = "source_postal_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String sourcePostalCode;

	@Column(name = "dest_address", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String destAddress;

	@Column(name = "dest_postal_code", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String destPostalCode;

	@Column(name = "schedule_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private ScheduleStatus scheduleStatus;

	@Column(name = "routing_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES) })
	@Enumerated(EnumType.STRING)
	private RoutingStatus routingStatus;

	@Column(name = "offset_tax", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String offsetTax;

	@Column(name = "non_offset_tax", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
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

	public LogisticsRouting convertTo(int mode) {
		LogisticsRouting bean = new LogisticsRouting();

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
		bean.setNonOffsetTax(nonOffsetTax);
		bean.setOffsetTax(nonOffsetTax);
		bean.setPickupFunc(isPickupFunc);
		bean.setRevisionControl(revisionControl);
		bean.setRoutingSequence(routingSequence);
		bean.setRoutingStatus(routingStatus);
		bean.setScheduleStatus(scheduleStatus);
		bean.setSourceAddress(sourceAddress);
		bean.setSourcePostalCode(sourcePostalCode);
		bean.setTrackingCode(logistTrackingCode);

		return bean;
	}
}
