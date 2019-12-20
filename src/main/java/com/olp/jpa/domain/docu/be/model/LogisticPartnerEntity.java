package com.olp.jpa.domain.docu.be.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import com.olp.jpa.domain.docu.org.model.ContactEntity;

@Entity
@Table(name = "trl_logistic_partner")
@NamedQueries({
		@NamedQuery(name = "LogisticPartnerEntity.findByPartnerCode", query = "SELECT t FROM LogisticPartnerEntity t WHERE t.partnerCode = :partnerCode ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class LogisticPartnerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_part_id", nullable = false)
	@DocumentId
	private Long id;

	@Column(name = "partner_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerCode;

	@Column(name = "partner_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerName;

	@OneToOne(cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private ContactEntity primaryContactRef;

	@ElementCollection(fetch = FetchType.EAGER)
	@IndexedEmbedded
	@CollectionTable(name = "legal_info", joinColumns = { @JoinColumn(name = "log_part_id") })
	private List<LegalInfoBean> legalInfo;

	@Enumerated(EnumType.STRING)
	@Column(name = "lifecycle_status", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private LifeCycleStatus lifeCycleStatus;

	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

	@OneToMany(mappedBy = "partnerRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private Set<LgstPtnrWfStatusEntity> wfStatus;

	@OneToMany(mappedBy = "partnerRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<LogisticPartnerLocEntity> partnerLocations;

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
	 * @return the partnerCode
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode
	 *            the partnerCode to set
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName
	 *            the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	/**
	 * @return the primaryContactRef
	 */
	public ContactEntity getPrimaryContactRef() {
		return primaryContactRef;
	}

	/**
	 * @param primaryContactRef
	 *            the primaryContactRef to set
	 */
	public void setPrimaryContactRef(ContactEntity primaryContactRef) {
		this.primaryContactRef = primaryContactRef;
	}

	/**
	 * @return the legalInfo
	 */
	public List<LegalInfoBean> getLegalInfo() {
		return legalInfo;
	}

	/**
	 * @param legalInfo
	 *            the legalInfo to set
	 */
	public void setLegalInfo(List<LegalInfoBean> legalInfo) {
		this.legalInfo = legalInfo;
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
	 * @return the wfStatus
	 */
	public Set<LgstPtnrWfStatusEntity> getWfStatus() {
		return wfStatus;
	}

	/**
	 * @param wfStatus
	 *            the wfStatus to set
	 */
	public void setWfStatus(Set<LgstPtnrWfStatusEntity> wfStatus) {
		this.wfStatus = wfStatus;
	}

	/**
	 * @return the partnerLocations
	 */
	public List<LogisticPartnerLocEntity> getPartnerLocations() {
		return partnerLocations;
	}

	/**
	 * @param partnerLocations
	 *            the partnerLocations to set
	 */
	public void setPartnerLocations(List<LogisticPartnerLocEntity> partnerLocations) {
		this.partnerLocations = partnerLocations;
	}

	public LogisticPartner convertTo(int mode) {
		LogisticPartner bean = new LogisticPartner();
		bean.setId(id);
		bean.setLegalInfo(legalInfo);
		bean.setLifeCycleStatus(lifeCycleStatus);
		bean.setPartnerCode(partnerCode);
		List<LogisticPartnerLoc> listOfLogisticsPartnerLocs = new ArrayList<>();
		
		for(LogisticPartnerLocEntity logisticPartnerLoc : partnerLocations){
			listOfLogisticsPartnerLocs.add(logisticPartnerLoc.convertTo(0));
		}
		bean.setPartnerLocations(listOfLogisticsPartnerLocs);
		
		bean.setPartnerName(partnerName);
		bean.setPrimaryContactRef(primaryContactRef);
		bean.setRevisionControl(revisionControl);
		bean.setWfStatus(wfStatus);
		return bean;
	}
}
