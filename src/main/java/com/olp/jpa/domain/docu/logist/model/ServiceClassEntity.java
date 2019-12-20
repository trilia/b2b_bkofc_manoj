package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.common.RevisionControlBean;

@Entity
@Table(name = "trl_service_class")
@NamedQueries({
		@NamedQuery(name = "ServiceClassEntity.findBySvcClassCode", query = "SELECT t FROM ServiceClassEntity t WHERE t.svcClassCode = :svcClassCode ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class ServiceClassEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "service_class_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "partner_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })
	private String partnerId;

	@Column(name = "svc_class_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String svcClassCode;

	@Column(name = "svc_class_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String svcClassName;

	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStatus lifeCycleStatus;
	
	@OneToMany(mappedBy = "srcSvcClassRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<ServiceCategoryEntity> srcSvcCategories = new HashSet<>();

	@OneToMany(mappedBy = "destSvcClassRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<ServiceCategoryEntity> destSvcCategories = new HashSet<>();

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
	 * @return the svcClassCode
	 */
	public String getSvcClassCode() {
		return svcClassCode;
	}

	/**
	 * @param svcClassCode
	 *            the svcClassCode to set
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
	 * @param svcClassName
	 *            the svcClassName to set
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
	 * @param lifeCycleStatus
	 *            the lifeCycleStatus to set
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
	 * @param revisionControl
	 *            the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}

	/**
	 * @return the srcSvcCategories
	 */
	public Set<ServiceCategoryEntity> getSrcSvcCategories() {
		return srcSvcCategories;
	}

	/**
	 * @param srcSvcCategories the srcSvcCategories to set
	 */
	public void setSrcSvcCategories(Set<ServiceCategoryEntity> srcSvcCategories) {
		this.srcSvcCategories = srcSvcCategories;
	}

	/**
	 * @return the destSvcCategories
	 */
	public Set<ServiceCategoryEntity> getDestSvcCategories() {
		return destSvcCategories;
	}

	/**
	 * @param destSvcCategories the destSvcCategories to set
	 */
	public void setDestSvcCategories(Set<ServiceCategoryEntity> destSvcCategories) {
		this.destSvcCategories = destSvcCategories;
	}

	public ServiceClass convertTo(int mode) {
		ServiceClass bean = new ServiceClass();
		bean.setId(id);
		bean.setPartnerId(partnerId);
		bean.setSvcClassCode(svcClassCode);
		bean.setSvcClassName(svcClassName);
		bean.setRevisionControl(revisionControl);
		bean.setLifeCycleStatus(lifeCycleStatus);
		
		Set<ServiceCategory> srcSvcCategoriesSet = new HashSet<>();
		for (ServiceCategoryEntity svcCat : srcSvcCategories) {
			ServiceCategory serviceCategory = svcCat.convertTo(mode);
			srcSvcCategoriesSet.add(serviceCategory);
		}
		
		bean.setSrcSvcCategories(srcSvcCategoriesSet);
		
		Set<ServiceCategory> destSvcCategoriesSet = new HashSet<>();
		for (ServiceCategoryEntity svcCat : destSvcCategories) {
			ServiceCategory serviceCategory = svcCat.convertTo(mode);
			destSvcCategoriesSet.add(serviceCategory);
		}
		
		bean.setDestSvcCategories(destSvcCategoriesSet);
		return bean;
	}
}
