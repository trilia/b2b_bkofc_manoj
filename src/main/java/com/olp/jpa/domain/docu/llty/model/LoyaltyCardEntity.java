package com.olp.jpa.domain.docu.llty.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.olp.annotations.KeyAttribute;
import com.olp.annotations.MultiTenant;
import com.olp.jpa.common.RevisionControlBean;

@Entity
@Table(name = "trl_loyalty_card", uniqueConstraints = @UniqueConstraint(columnNames = { "tenant_id",
		"cs_loyalty_code" }))
@NamedQueries({
		@NamedQuery(name = "LoyaltyCardEntity.findByCustomerCode", query = "SELECT t FROM LoyaltyCardEntity t WHERE t.customerCode = :custCode and t.tenantId = :tenant "),
		@NamedQuery(name = "LoyaltyCardEntity.findAllByCustomerCode", query = "SELECT t FROM LoyaltyCardEntity t WHERE t.customerCode = :custCode and t.tenantId = :tenant order by t.effectiveFrom")		
		})
@Cacheable(true)
@Indexed(index = "SetupDataIndex")
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
public class LoyaltyCardEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "loyalty_card_id", nullable = false)
	@DocumentId
	private Long id;

	@KeyAttribute
	@Column(name = "tenant_id", nullable = false)
	@Fields({ @Field(analyze = Analyze.NO, store = Store.YES) })
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name = "cs_loyalty_ref")
	@ContainedIn
	private CustomerLoyaltyEntity csLoyaltyRef;
	
	@Column(name = "cs_loyalty_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String csLoyaltyCode;
	
	@Column(name = "customer_code", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String customerCode;
	
	@Column(name = "effective_from", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveFrom;
	
	@Column(name = "effective_upto", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private Date effectiveUpto;
	
	@Column(name = "card_number", nullable = false)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardNumber;
	
	@Column(name = "card_number2", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardNumber2;
	
	@Column(name = "card_source2", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardSource2;
	
	@Column(name = "card_number3", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardNumber3;
	
	@Column(name = "card_source3", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardSource3;
	
	@Column(name = "card_number4", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardNumber4;
	
	@Column(name = "card_source4", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardSource4;
	
	@Column(name = "card_number5", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardNumber5;
	
	@Column(name = "card_source5", nullable = true)
	@Fields({ @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO) })
	private String cardSource5;
	
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

	public CustomerLoyaltyEntity getCsLoyaltyRef() {
		return csLoyaltyRef;
	}

	public void setCsLoyaltyRef(CustomerLoyaltyEntity csLoyaltyRef) {
		this.csLoyaltyRef = csLoyaltyRef;
	}

	public String getCsLoyaltyCode() {
		return csLoyaltyCode;
	}

	public void setCsLoyaltyCode(String csLoyaltyCode) {
		this.csLoyaltyCode = csLoyaltyCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardNumber2() {
		return cardNumber2;
	}

	public void setCardNumber2(String cardNumber2) {
		this.cardNumber2 = cardNumber2;
	}

	public String getCardSource2() {
		return cardSource2;
	}

	public void setCardSource2(String cardSource2) {
		this.cardSource2 = cardSource2;
	}

	public String getCardNumber3() {
		return cardNumber3;
	}

	public void setCardNumber3(String cardNumber3) {
		this.cardNumber3 = cardNumber3;
	}

	public String getCardSource3() {
		return cardSource3;
	}

	public void setCardSource3(String cardSource3) {
		this.cardSource3 = cardSource3;
	}

	public String getCardNumber4() {
		return cardNumber4;
	}

	public void setCardNumber4(String cardNumber4) {
		this.cardNumber4 = cardNumber4;
	}

	public String getCardSource4() {
		return cardSource4;
	}

	public void setCardSource4(String cardSource4) {
		this.cardSource4 = cardSource4;
	}

	public String getCardNumber5() {
		return cardNumber5;
	}

	public void setCardNumber5(String cardNumber5) {
		this.cardNumber5 = cardNumber5;
	}

	public String getCardSource5() {
		return cardSource5;
	}

	public void setCardSource5(String cardSource5) {
		this.cardSource5 = cardSource5;
	}

	public RevisionControlBean getRevisionControl() {
		return revisionControl;
	}

	public void setRevisionControl(RevisionControlBean revisionControl) {
		this.revisionControl = revisionControl;
	}
	
	public LoyaltyCard convertTo(int mode){
		LoyaltyCard loyaltyCard = new LoyaltyCard();
		loyaltyCard.setCardNumber(cardNumber);
		loyaltyCard.setCardNumber2(cardNumber2);
		loyaltyCard.setCardNumber3(cardNumber3);
		loyaltyCard.setCardNumber4(cardNumber4);
		loyaltyCard.setCardNumber5(cardNumber5);
		loyaltyCard.setCardSource2(cardSource2);
		loyaltyCard.setCardSource3(cardSource3);
		loyaltyCard.setCardSource4(cardSource4);
		loyaltyCard.setCardSource5(cardSource5);
		loyaltyCard.setCsLoyaltyRef(csLoyaltyRef.getCsLoyaltyCode());
		loyaltyCard.setCustomerCode(customerCode);
		loyaltyCard.setEffectiveFrom(effectiveFrom);
		loyaltyCard.setEffectiveUpto(effectiveUpto);
		loyaltyCard.setId(id);
		loyaltyCard.setRevisionControl(revisionControl);
		loyaltyCard.setTenantId(tenantId);
		
		return loyaltyCard;
	}
}
