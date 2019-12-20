package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;

@Entity
@Table(name = "trl_service_category")
@NamedQueries({
		@NamedQuery(name = "ServiceCategoryEntity.findBySrcSvcClassCode", query = "SELECT t FROM ServiceCategoryEntity t WHERE t.srcSvcClassCode = :svcClassCode"),
		@NamedQuery(name = "ServiceCategoryEntity.findByDestSvcClassCode", query = "SELECT t FROM ServiceCategoryEntity t WHERE t.destSvcClassCode = :svcClassCode") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class ServiceCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "svc_catg_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "partner_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })
	private String partnerId;

	/*@Column(name = "src_svc_class_ref", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })*/
	@ManyToOne(optional=true)
	@JoinColumn(name = "src_svc_class_ref")
	@ContainedIn
	private ServiceClassEntity srcSvcClassRef;

	@Column(name = "src_svc_class_code", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String srcSvcClassCode;

	/*@Column(name = "dest_svc_class_ref", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })*/
	@ManyToOne(optional=true)
	@JoinColumn(name = "dest_svc_class_ref")
	@ContainedIn
	private ServiceClassEntity destSvcClassRef;

	@Column(name = "dest_svc_class_code", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String destSvcClassCode;

	@Column(name = "eta_min_hours", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Float etaMinHours;

	@Column(name = "eta_max_hours", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Float etaMaxHours;

	@Column(name = "both_ways_flag", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private boolean bothWaysFlag;

	@OneToMany(mappedBy = "svcCatgRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<LogisticsCostEntity> costEstimate = new ArrayList<>();
	
	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStatus lifeCycleStatus;

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
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the srcSvcClassRef
	 */
	public ServiceClassEntity getSrcSvcClassRef() {
		return srcSvcClassRef;
	}

	/**
	 * @param srcSvcClassRef the srcSvcClassRef to set
	 */
	public void setSrcSvcClassRef(ServiceClassEntity srcSvcClassRef) {
		this.srcSvcClassRef = srcSvcClassRef;
	}

	/**
	 * @return the srcSvcClassCode
	 */
	public String getSrcSvcClassCode() {
		return srcSvcClassCode;
	}

	/**
	 * @param srcSvcClassCode the srcSvcClassCode to set
	 */
	public void setSrcSvcClassCode(String srcSvcClassCode) {
		this.srcSvcClassCode = srcSvcClassCode;
	}

	/**
	 * @return the destSvcClassRef
	 */
	public ServiceClassEntity getDestSvcClassRef() {
		return destSvcClassRef;
	}

	/**
	 * @param destSvcClassRef the destSvcClassRef to set
	 */
	public void setDestSvcClassRef(ServiceClassEntity destSvcClassRef) {
		this.destSvcClassRef = destSvcClassRef;
	}

	/**
	 * @return the destSvcClassCode
	 */
	public String getDestSvcClassCode() {
		return destSvcClassCode;
	}

	/**
	 * @param destSvcClassCode the destSvcClassCode to set
	 */
	public void setDestSvcClassCode(String destSvcClassCode) {
		this.destSvcClassCode = destSvcClassCode;
	}

	/**
	 * @return the etaMinHours
	 */
	public Float getEtaMinHours() {
		return etaMinHours;
	}

	/**
	 * @param etaMinHours the etaMinHours to set
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
	 * @param etaMaxHours the etaMaxHours to set
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
	 * @param bothWaysFlag the bothWaysFlag to set
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
	 * @param costEstimate the costEstimate to set
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
	 * @param revisionControl the revisionControl to set
	 */
	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
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

	public ServiceCategory convertTo(int mode){
		ServiceCategory bean = new ServiceCategory();
		bean.setId(id);
		bean.setBothWaysFlag(bothWaysFlag);
		bean.setCostEstimate(costEstimate);
		bean.setDestSvcClassRef(destSvcClassCode);
		bean.setEtaMaxHours(etaMaxHours);
		bean.setEtaMinHours(etaMinHours);
		bean.setPartnerId(partnerId);
		bean.setSrcSvcClassRef(srcSvcClassCode);
		bean.setRevisionControl(revisionControl);
		bean.setLifeCycleStatus(lifeCycleStatus);
		
		return bean;
	}
}
