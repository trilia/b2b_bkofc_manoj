package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;

@Entity
@Table(name = "trl_loyalty_partner_validity", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"partnerCode" }))
@NamedQueries({
		@NamedQuery(name = "PartnerValidityEntity.findByEffectiveDate", query = "SELECT t FROM PartnerValidityEntity t WHERE t.partnerCode = :partnerCode and t.effectiveFrom >= :date and t.effectiveUpto < :date and  t.tenantId = :tenant "),
		@NamedQuery(name = "PartnerValidityEntity.findByPartnerCode", query = "SELECT t FROM PartnerValidityEntity t WHERE t.partnerCode = :partnerCode and t.tenantId = :tenant ")		
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class PartnerValidityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "program_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name = "partner_ref")
	@ContainedIn
	private LoyaltyPartnerEntity partnerRef;
	
	@Column(name = "partner_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String partnerCode;
	
	@Column(name = "effective_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveFrom;
	
	@Column(name = "effective_upto", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveUpto;

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

	public LoyaltyPartnerEntity getPartnerRef() {
		return partnerRef;
	}

	public void setPartnerRef(LoyaltyPartnerEntity partnerRef) {
		this.partnerRef = partnerRef;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Date getEffectiveUpto() {
		return effectiveUpto;
	}

	public void setEffectiveUpto(Date effectiveUpto) {
		this.effectiveUpto = effectiveUpto;
	}
	
	public PartnerValidity convertTo(int mode){
		PartnerValidity partnerValidity = new PartnerValidity();
		partnerValidity.setEffectiveFrom(effectiveFrom);
		partnerValidity.setEffectiveUpto(effectiveUpto);
		partnerValidity.setId(id);
		partnerValidity.setPartnerRef(partnerRef.getPartnerCode());
		partnerValidity.setTenantId(tenantId);
		
		return partnerValidity;
	}
}
