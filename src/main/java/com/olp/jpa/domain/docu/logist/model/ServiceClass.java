package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;

@XmlRootElement(name = "serviceClass", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "partnerId", "svcClassCode", "svcClassName", "lifecycleStatus",
		"revisionControl" })
public class ServiceClass implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "svcClassId")
	private Long id;
	
	@XmlElement
	private String partnerId;

	@XmlElement
	private String svcClassCode;

	@XmlElement
	private String svcClassName;

	@XmlElement
	private LifeCycleStatus lifeCycleStatus;

	private RevisionControlBean revisionControl;
	
	@XmlElement
	private Set<ServiceCategory> srcSvcCategories;

	@XmlElement
	private Set<ServiceCategory> destSvcCategories;

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
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerCode the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the svcClassCode
	 */
	public String getSvcClassCode() {
		return svcClassCode;
	}

	/**
	 * @param svcClassCode the svcClassCode to set
	 */
	public void setSvcClassCode(String svcClassCode) {
		this.svcClassCode = svcClassCode;
	}

	/**
	 * @return the svcClassName
	 */
	public String getSvcClassName() {
		return svcClassName;
	}

	/**
	 * @param svcClassName the svcClassName to set
	 */
	public void setSvcClassName(String svcClassName) {
		this.svcClassName = svcClassName;
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

	/**
	 * @return the srcSvcCategories
	 */
	public Set<ServiceCategory> getSrcSvcCategories() {
		return srcSvcCategories;
	}

	/**
	 * @param srcSvcCategories the srcSvcCategories to set
	 */
	public void setSrcSvcCategories(Set<ServiceCategory> srcSvcCategories) {
		this.srcSvcCategories = srcSvcCategories;
	}

	/**
	 * @return the destSvcCategories
	 */
	public Set<ServiceCategory> getDestSvcCategories() {
		return destSvcCategories;
	}

	/**
	 * @param destSvcCategories the destSvcCategories to set
	 */
	public void setDestSvcCategories(Set<ServiceCategory> destSvcCategories) {
		this.destSvcCategories = destSvcCategories;
	}

	public ServiceClassEntity convertTo(int mode){
		ServiceClassEntity bean = new ServiceClassEntity();
		bean.setId(id);
		bean.setPartnerId(partnerId);
		bean.setSvcClassCode(svcClassCode);
		bean.setSvcClassName(svcClassName);
		bean.setRevisionControl(revisionControl);
		bean.setLifeCycleStatus(lifeCycleStatus);
		
		Set<ServiceCategoryEntity> destServiceCategorySet = new HashSet<>();
		for(ServiceCategory svcCat : destSvcCategories){
			ServiceCategoryEntity svcCatgEntity =  svcCat.convertTo(0);
			destServiceCategorySet.add(svcCatgEntity);
		
		}
		
		Set<ServiceCategoryEntity> srcServiceCategorySet = new HashSet<>();
		for(ServiceCategory svcCat : srcSvcCategories){
			ServiceCategoryEntity svcCatgEntity =  svcCat.convertTo(0);
			srcServiceCategorySet.add(svcCatgEntity);
		
		}
		
		bean.setDestSvcCategories(destServiceCategorySet);
		bean.setSrcSvcCategories(srcServiceCategorySet);
		
		return bean;
	}

}
