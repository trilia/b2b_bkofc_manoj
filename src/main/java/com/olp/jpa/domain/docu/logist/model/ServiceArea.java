package com.olp.jpa.domain.docu.logist.model;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.olp.jpa.common.CommonEnums.LifeCycleStatus;
import com.olp.jpa.domain.docu.logist.model.LogisticsEnum.Country;

@XmlRootElement(name = "serviceArea", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "partnerId", "svcClassRef", "svcClassCode", "country", "postalCodeFrom", "postalCodeTo",
		"postalCodeExcept", "lifecycleStatus" })
public class ServiceArea implements Serializable {

	private static final long serialVersionUID = -1L;

	@XmlElement(name = "svcAreaId")
	private Long id;

	@XmlElement
	private String partnerId;

	@XmlElement
	private String svcClassRef;

	@XmlElement
	private String svcClassCode;

	@XmlElement
	private Country country;

	@XmlElement
	private String postalCodeFrom;

	@XmlElement
	private String postalCodeTo;

	@XmlElement
	private Set<String> postalCodeExcept;

	@XmlElement
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
	public String getSvcClassRef() {
		return svcClassRef;
	}

	/**
	 * @param svcClassRef the svcClassRef to set
	 */
	public void setSvcClassRef(String svcClassRef) {
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
	
	public ServiceAreaEntity convertTo(int mode){
		ServiceAreaEntity bean = new ServiceAreaEntity();
		
		bean.setId(id);
		bean.setCountry(country);
		bean.setLifeCycleStatus(lifeCycleStatus);
		bean.setPartnerId(partnerId);
		bean.setPostalCodeExcept(postalCodeExcept);
		bean.setPostalCodeFrom(postalCodeFrom);
		bean.setPostalCodeTo(postalCodeTo);
		bean.setSvcClassCode(svcClassCode);
		//bean.setSvcClassRef(svcClassRef.getSvcClassCode());
		
		return bean;
	}

}
