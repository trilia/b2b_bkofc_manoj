/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.olp.jpa.common.EntityContainerTemplate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author raghosh
 */
@XmlRootElement(name="departments", namespace="http://trilia-cloud.com/schema/entity/ut/ut-dept")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"deptList"})
public class DeptContainer implements EntityContainerTemplate<DeptBeanPub, Long> {
    
    @XmlElement( name = "department" )
    private List<DeptBeanPub> deptList;
    
    @Override
    public List<DeptBeanPub> getEntityList() {
        return(deptList);
    }
    
    public void setEntityList(List<DeptBeanPub> list) {
        this.deptList = list;
    }
    
}