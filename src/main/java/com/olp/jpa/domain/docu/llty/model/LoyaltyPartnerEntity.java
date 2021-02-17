package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.org.model.LocationEntity;

@Entity
@Table(name = "trl_loyalty_partner")
@NamedQueries({
		@NamedQuery(name = "LoyaltyPartnerEntity.findByPartnerCode", query = "SELECT t FROM LoyaltyPartnerEntity t WHERE t.partnerCode = :partnerCode and t.tenantId = :tenant ")
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
public class LoyaltyPartnerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "partner_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@Column(name = "partner_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerCode;
	
	@Column(name = "partner_name", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerName;
	
	@OneToOne
	@JoinColumn(name = "location_ref")
	@ContainedIn
	private LocationEntity locationRef;
	
	@Column(name = "location_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String locationCode;
	
	@OneToMany(mappedBy = "partnerRef", cascade = { CascadeType.ALL })
	@IndexedEmbedded(includeEmbeddedObjectId = true, depth = 1)
	private List<PartnerValidityEntity> partnerValidities;
	
	@Embedded
	@IndexedEmbedded
	private RevisionControlBean revisionControl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public LocationEntity getLocationRef() {
		return locationRef;
	}

	public void setLocationRef(LocationEntity locationRef) {
		this.locationRef = locationRef;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public List<PartnerValidityEntity> getPartnerValidities() {
		return partnerValidities;
	}

	public void setPartnerValidities(List<PartnerValidityEntity> partnerValidities) {
		this.partnerValidities = partnerValidities;
	}

	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}
	
	public LoyaltyPartner convertTo(int mode){
		LoyaltyPartner loyaltyPartner = new LoyaltyPartner();
		loyaltyPartner.setId(id);
		loyaltyPartner.setLocationCode(locationCode);		
		loyaltyPartner.setLocationRef(locationCode);
		loyaltyPartner.setPartnerCode(partnerCode);
		loyaltyPartner.setPartnerName(partnerName);
		List<PartnerValidity> partnerValiodityList= new ArrayList<PartnerValidity>();
		for(PartnerValidityEntity partnerValidityEntity:partnerValidities){
			partnerValiodityList.add(partnerValidityEntity.convertTo(0));
		}
		
		loyaltyPartner.setPartnerValidities(partnerValiodityList);
		loyaltyPartner.setRevisionControl(revisionControl);
		loyaltyPartner.setTenantId(tenantId);
		
		
		return loyaltyPartner;
	}
	
}
