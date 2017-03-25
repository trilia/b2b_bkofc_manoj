/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import com.olp.jpa.common.EntityContainerTemplate;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author raghosh
 */
@XmlRootElement(name="employees", namespace="http://trilia-cloud.com/schema/entity/ut/ut-emp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"empList"})
public class EmpContainer implements EntityContainerTemplate<EmpBeanPub, Long> {
    
    @XmlElement( name = "employee" )
    private List<EmpBeanPub> empList;

    @Override
    public List<EmpBeanPub> getEntityList() {
        return(empList);
    }
    
}
