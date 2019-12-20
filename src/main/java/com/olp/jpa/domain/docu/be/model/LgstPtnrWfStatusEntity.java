/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.be.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;

import com.olp.annotations.MultiTenant;
import com.olp.jpa.domain.docu.comm.model.WorkflowStatusEntity;

/**
 *
 * @author raghosh
 */
@Entity  
@DiscriminatorValue("SUPPLIER") 
@MultiTenant(level=MultiTenant.Levels.NO_MT)
public class LgstPtnrWfStatusEntity extends WorkflowStatusEntity {
    
    @ManyToOne
    @JoinColumn(name="partner_ref", nullable=true)
    @ContainedIn
    private LogisticPartnerEntity partnerRef;
    
    @Column(name="partner_code", nullable=true)
    @Fields({
            @Field( analyze = Analyze.NO )
    })
    private String partnerCode;

    public LogisticPartnerEntity getPartnerRef() {
        return partnerRef;
    }

    public void setPartnerRef(LogisticPartnerEntity partnerRef) {
        this.partnerRef = partnerRef;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    
}
