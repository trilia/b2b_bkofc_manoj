package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.Country;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.StateOrCounty;

@Entity
@Table(name = "trl_service_area")
@NamedQueries({
		@NamedQuery(name = "ServiceAreaEntity.findBySvcClassCode", query = "SELECT t FROM ServiceAreaEntity t WHERE t.svcClassCode = :svcClassCode ") ,
		@NamedQuery(name = "ServiceAreaEntity.findByCountry", query = "SELECT t FROM ServiceAreaEntity t WHERE t.country = :country ") ,
		@NamedQuery(name = "ServiceAreaEntity.findByPartnerId", query = "SELECT t FROM ServiceAreaEntity t WHERE t.partnerId = :partnerId ") })
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.NO_MT)
public class ServiceAreaEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "service_area_id", nullable = false)
	@DocumentId
	private Long id;
	
	@Column(name = "partner_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })
	private String partnerId;
	
	//@Column(name = "svc_class_ref", nullable = false)
	//@Fields({ @Field(analyze = Analyze.NO, store = Store.NO) })
	@ManyToOne(optional=true)
	@JoinColumn(name = "svc_class_ref")
	@ContainedIn
	private ServiceClassEntity svcClassRef;

	@Column(name = "svc_class_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String svcClassCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "country", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Country country;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "state_or_county", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private StateOrCounty stateOrCounty;

	@Column(name = "postal_code_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String postalCodeFrom;
	
	@Column(name = "postal_code_to", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String postalCodeTo;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "trl_svc_area_exceptions", joinColumns = { @JoinColumn(name = "svc_area_excp_id") })
	private Set<String> postalCodeExcept;
	
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
	 * @return the svcClassRef
	 */
	public ServiceClassEntity getSvcClassRef() {
		return svcClassRef;
	}

	/**
	 * @param svcClassRef the svcClassRef to set
	 */
	public void setSvcClassRef(ServiceClassEntity svcClassRef) {
		this.svcClassRef = svcClassRef;
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
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the stateOrCounty
	 */
	public StateOrCounty getStateOrCounty() {
		return stateOrCounty;
	}

	/**
	 * @param stateOrCounty the stateOrCounty to set
	 */
	public void setStateOrCounty(StateOrCounty stateOrCounty) {
		this.stateOrCounty = stateOrCounty;
	}

	/**
	 * @return the postalCodeFrom
	 */
	public String getPostalCodeFrom() {
		return postalCodeFrom;
	}

	/**
	 * @param postalCodeFrom the postalCodeFrom to set
	 */
	public void setPostalCodeFrom(String postalCodeFrom) {
		this.postalCodeFrom = postalCodeFrom;
	}

	/**
	 * @return the postalCodeTo
	 */
	public String getPostalCodeTo() {
		return postalCodeTo;
	}

	/**
	 * @param postalCodeTo the postalCodeTo to set
	 */
	public void setPostalCodeTo(String postalCodeTo) {
		this.postalCodeTo = postalCodeTo;
	}

	/**
	 * @return the postalCodeExcept
	 */
	public Set<String> getPostalCodeExcept() {
		return postalCodeExcept;
	}

	/**
	 * @param postalCodeExcept the postalCodeExcept to set
	 */
	public void setPostalCodeExcept(Set<String> postalCodeExcept) {
		this.postalCodeExcept = postalCodeExcept;
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
	
	public ServiceArea convertTo(int mode){
		ServiceArea bean = new ServiceArea();
		bean.setId(id);
		bean.setCountry(country);
		bean.setLifeCycleStatus(lifeCycleStatus);
		bean.setPartnerId(partnerId);
		bean.setPostalCodeExcept(postalCodeExcept);
		bean.setPostalCodeFrom(postalCodeFrom);
		bean.setPostalCodeTo(postalCodeTo);
		bean.setSvcClassCode(svcClassCode);
		bean.setSvcClassRef(svcClassRef.getSvcClassCode());//need to check
		
		return bean;
	}
}
