package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;

@XmlRootElement(name = "serviceCategory", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "partnerId", "srcSvcClassRef", "destSvcClassRef", "etaMinHours", "etaMaxHours",
		"bothWaysFlag", "costEstimate", "lifeCycleStatus","revisionControl" })
public class ServiceCategory implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "svcCatgId")
	private Long id;

	@XmlElement
	private String partnerId;

	@XmlElement
	private String srcSvcClassRef;

	@XmlElement
	private String destSvcClassRef;

	@XmlElement
	private Float etaMinHours;

	@XmlElement
	private Float etaMaxHours;

	@XmlElement
	private boolean bothWaysFlag;

	@XmlElement
	private List<LogisticsCostEntity> costEstimate;
	
	@XmlElement
	private LifeCycleStatus lifeCycleStatus;

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
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId
	 *            the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the srcSvcClassRef
	 */
	public String getSrcSvcClassRef() {
		return srcSvcClassRef;
	}

	/**
	 * @param srcSvcClassRef
	 *            the srcSvcClassRef to set
	 */
	public void setSrcSvcClassRef(String srcSvcClassRef) {
		this.srcSvcClassRef = srcSvcClassRef;
	}

	/**
	 * @return the destSvcClassRef
	 */
	public String getDestSvcClassRef() {
		return destSvcClassRef;
	}

	/**
	 * @param destSvcClassRef
	 *            the destSvcClassRef to set
	 */
	public void setDestSvcClassRef(String destSvcClassRef) {
		this.destSvcClassRef = destSvcClassRef;
	}

	/**
	 * @return the etaMinHours
	 */
	public Float getEtaMinHours() {
		return etaMinHours;
	}

	/**
	 * @param etaMinHours
	 *            the etaMinHours to set
	 */
	public void setEtaMinHours(Float etaMinHours) {
		this.etaMinHours = etaMinHours;
	}

	/**
	 * @return the etaMaxHours
	 */
	public Float getEtaMaxHours() {
		return etaMaxHours;
	}

	/**
	 * @param etaMaxHours
	 *            the etaMaxHours to set
	 */
	public void setEtaMaxHours(Float etaMaxHours) {
		this.etaMaxHours = etaMaxHours;
	}

	/**
	 * @return the bothWaysFlag
	 */
	public boolean isBothWaysFlag() {
		return bothWaysFlag;
	}

	/**
	 * @param bothWaysFlag
	 *            the bothWaysFlag to set
	 */
	public void setBothWaysFlag(boolean bothWaysFlag) {
		this.bothWaysFlag = bothWaysFlag;
	}

	/**
	 * @return the costEstimate
	 */
	public List<LogisticsCostEntity> getCostEstimate() {
		return costEstimate;
	}

	/**
	 * @param costEstimate
	 *            the costEstimate to set
	 */
	public void setCostEstimate(List<LogisticsCostEntity> costEstimate) {
		this.costEstimate = costEstimate;
	}

	/**
	 * @return the revisionControl
	 */
	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}
	
	/**
	 * @return the lifeCycleStatus
	 */
	public LifeCycleStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	/**
	 * @param lifeCycleStatus the lifeCycleStatus to set
	 */
	public void setLifeCycleStatus(LifeCycleStatus lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
	}

	/**
	 * @param revisionControl the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	public ServiceCategoryEntity convertTo(int mode) {
		ServiceCategoryEntity bean = new ServiceCategoryEntity();
		bean.setId(id);
		bean.setPartnerId(partnerId);

		bean.setBothWaysFlag(bothWaysFlag);
		bean.setCostEstimate(costEstimate);
		bean.setDestSvcClassCode(destSvcClassRef);

		ServiceClassEntity destSvcClassEntity = new ServiceClassEntity();
		destSvcClassEntity.setSvcClassCode(destSvcClassRef);
		bean.setDestSvcClassRef(destSvcClassEntity);

		bean.setEtaMaxHours(etaMaxHours);
		bean.setEtaMinHours(etaMinHours);
		bean.setSrcSvcClassCode(srcSvcClassRef);

		ServiceClassEntity srcSvcClassEntity = new ServiceClassEntity();
		srcSvcClassEntity.setSvcClassCode(srcSvcClassRef);
		bean.setSrcSvcClassRef(srcSvcClassEntity);
		bean.setRevisionControl(revisionControl);
		bean.setLifeCycleStatus(lifeCycleStatus);
		
		return bean;
	}
}
