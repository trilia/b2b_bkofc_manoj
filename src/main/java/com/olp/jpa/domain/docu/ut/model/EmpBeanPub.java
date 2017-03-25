/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import com.olp.jpa.common.RevisionControlBean;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author raghosh
 */
@XmlRootElement(name="employee", namespace="http://trilia-cloud.com/schema/entity/ut/ut-emp")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlAccessorOrder(value=XmlAccessOrder.UNDEFINED)
@XmlType(propOrder={"id", "firstName", "lastName", "employeeNumber", "nickName", "dateOfJoining", "email", "employeeType", "manager", "department", "revisionControl"})
public class EmpBeanPub implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    @XmlElement(name="emp-id")
    private Long id;
    
    @XmlTransient
    private String tenantId;
    
    @XmlElement(name="first-name")
    private String firstName;
    
    @XmlElement(name="last-name")
    private String lastName;
    
    @XmlAttribute(name="emp-code")
    private String employeeNumber;
    
    @XmlElement(name="nick-name")
    private String nickName;
    
    @XmlElement(name="date-of-joining")
    private Date dateOfJoining;
    
    @XmlElement(name="email")
    private String email;
    
    @XmlElement(name="employee-type")
    private String employeeType;
    
    @XmlElement(name="manager")
    private String manager;
    
    @XmlElement(name="department")
    private String department;
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public RevisionControlBean getRevisionControl() {
        return revisionControl;
    }

    public void setRevisionControl(RevisionControlBean revisionControl) {
        this.revisionControl = revisionControl;
    }
    
    public EmployeeBean convertTo() {
        
        EmployeeBean bean = new EmployeeBean();
        
        bean.setId(this.id);
        bean.setTenantId(this.tenantId);
        bean.setFirstName(this.firstName);
        bean.setLastName(this.lastName);
        bean.setNickName(this.nickName);
        bean.setEmployeeNumber(this.employeeNumber);
        bean.setDateOfJoning(this.dateOfJoining);
        bean.setEmail(this.email);
        bean.setEmployeeType(employeeType);
        
        if (!(this.manager == null || "".equals(this.manager))) {
            EmployeeBean bean2 = new EmployeeBean();
            bean2.setEmployeeNumber(this.manager);
            bean.setManager(bean2);
        }
        
        if (!(this.department == null || "".equals(this.department))) {
            DepartmentBean bean3 = new DepartmentBean();
            bean3.setDeptCode(this.department);
            bean.setDepartmentRef(bean3);
        }
        
        bean.setRevisionControl(this.revisionControl);
        
        return(bean);
        
    }
    
}
