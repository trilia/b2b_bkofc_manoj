package com.olp.jpa.domain.docu.fin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.fin.model.FinEnums.LedgerStatus;

@XmlRootElement(name = "ledger", namespace = "http://trilia-cloud.com/schema/entity/wm")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tenantId", "ledgerName", "ledgerDesc", "postingDate", "lifeCycleStatus",
		"ledgerLines", "revisionControl" })
public class Ledger implements Serializable{
	
	private static final long serialVersionUID = -1L;

	@XmlElement(name = "ledgerId")
	private Long id;

	@XmlTransient
	private String tenantId;

	@XmlElement
	private String ledgerName;

	@XmlElement
	private String ledgerDesc;

	@XmlElement
	private Date postingDate;

	@XmlElement
	private LedgerStatus lifeCycleStatus;

	@XmlElement
	private List<LedgerLineEntity> ledgerLines;

	private RevisionControlBean revisionControl;

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
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

    /**
	 * @return the ledgerName
	 */
	public String getLedgerName() {
		return ledgerName;
	}

	/**
	 * @param ledgerName the ledgerName to set
	 */
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	/**
	 * @return the ledgerDesc
	 */
	public String getLedgerDesc() {
		return ledgerDesc;
	}

	/**
	 * @param ledgerDesc the ledgerDesc to set
	 */
	public void setLedgerDesc(String ledgerDesc) {
		this.ledgerDesc = ledgerDesc;
	}

	/**
	 * @return the postingDate
	 */
	public Date getPostingDate() {
		return postingDate;
	}

	/**
	 * @param postingDate the postingDate to set
	 */
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	/**
	 * @return the lifeCycleStatus
	 */
	public LedgerStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	/**
	 * @param lifeCycleStatus the lifeCycleStatus to set
	 */
	public void setLifeCycleStatus(LedgerStatus lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
	}

	/**
	 * @return the ledgerLines
	 */
	public List<LedgerLineEntity> getLedgerLines() {
		return ledgerLines;
	}

	/**
	 * @param ledgerLines the ledgerLines to set
	 */
	public void setLedgerLines(List<LedgerLineEntity> ledgerLines) {
		this.ledgerLines = ledgerLines;
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

	public LedgerEntity convertTo(int mode){
    	LedgerEntity bean = new LedgerEntity();   	
    	bean.setId(id);
    	bean.setTenantId(tenantId);
    	bean.setLedgerDesc(ledgerDesc);
    	bean.setLedgerLines(ledgerLines);
    	bean.setLedgerName(ledgerName);
    	bean.setPostingDate(postingDate);
    	bean.setRevisionControl(revisionControl);
    	
    	return bean;
    }	
}
