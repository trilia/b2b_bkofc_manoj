/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import com.olp.jpa.common.RevisionControlBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author raghosh
 */
@XmlRootElement(name="department", namespace="http://trilia-cloud.com/schema/entity/ut/ut-dept")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlAccessorOrder(value=XmlAccessOrder.UNDEFINED)
@XmlType(propOrder={"id", "deptName", "description", "revisionControl", "employees"})
public class DeptBeanPub implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    @XmlElement(name="dept-id")
    private Long id;
    
    @XmlTransient
    private String tenantId;
    
    @XmlAttribute(name="dept-code")
    private String deptCode;
    
    @XmlElement(name="dept-name")
    private String deptName;
    
    @XmlElement(name="description")
    private String description;
    
    @XmlElement(name="employee")
    private List<String> employees;
    
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

    public RevisionControlBean getRevisionControl() {
        return revisionControl;
    }

    public void setRevisionControl(RevisionControlBean revisionControl) {
        this.revisionControl = revisionControl;
    }
    
    
    
    public DepartmentBean convertTo(int mode) {
        
        DepartmentBean bean = new DepartmentBean();
        
        bean.setId(this.id);
        bean.setTenantId(this.tenantId);
        bean.setDeptCode(this.deptCode);
        bean.setDeptName(this.deptName);
        bean.setDescription(this.description);
        if (this.revisionControl != null)
            bean.setRevisionControl(this.revisionControl.clone()); // may need to use a cloned bean
        
        if (this.employees != null) {
            ArrayList<EmployeeBean> emps = new ArrayList<>();
            for (String empCode : this.employees) {
                EmployeeBean emp = new EmployeeBean();
                emp.setEmployeeNumber(empCode);
                emps.add(emp);
            }
            bean.setEmployees(emps);
        }
        
        return(bean);
    }
    
}
