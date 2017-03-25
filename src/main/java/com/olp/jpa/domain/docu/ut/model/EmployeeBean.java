/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import com.olp.annotations.MultiTenant;
import com.olp.annotations.SortCriteria;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilter;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
//import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
//import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author raghosh
 */
@Entity
@Table(name="ut_employee"
        , uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id", "employee_number"}) // Enable constraint later
)
@NamedQueries({
    @NamedQuery(name="UtEmployeeBean.findByDeptId", query="SELECT t FROM EmployeeBean t WHERE t.tenantId = :tenant AND departmentRef.id = :deptId"),
    @NamedQuery(name="UtEmployeeBean.findByDeptCode", query="SELECT t FROM EmployeeBean t WHERE t.tenantId = :tenant AND departmentRef.deptCode = :deptCode"),
    @NamedQuery(name="UtEmployeeBean.findByEmpCode", query="SELECT t FROM EmployeeBean t WHERE t.tenantId = :tenant AND employeeNumber = :empCode")
    
})
@Cacheable(true)
@Indexed(index="UnitTest")
@FullTextFilterDef(name="filter-emp-by-tenant", impl=TenantBasedSearchFilterFactory.class)
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
@SortCriteria(attributes={"lastName", "firstName"})
public class EmployeeBean implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="employee_id", nullable=false)
    @DocumentId
    //@Type(type = "objectid")
    private Long id;
    
    //@Version
    @Column(name="opt_lock")
    private Long version;
    
    @Column(name="tenant_id", nullable=false)
    @Field(analyze=Analyze.NO, store = Store.YES)
    private String tenantId;
    
    @Column(name="first_name", nullable=false)
    @Fields({
        @Field,
        @Field(name="firstName-sort", index=Index.YES, analyze=Analyze.NO, store=Store.YES)
    })
    //@SortableField
    private String firstName;
    
    @Column(name="last_name", nullable=false)
    @Fields({
        @Field,
        @Field(name="lastName-sort", index=Index.YES, analyze=Analyze.NO, store=Store.YES)
    })
    //@SortableField
    private String lastName;
    
    @Column(name="employee_number", nullable=false)
    @Fields({
        @Field,
        @Field(name="employeeNumber-sort", index=Index.YES, analyze=Analyze.NO, store=Store.YES)
    })
    //@SortableField
    private String employeeNumber;
    
    @Column(name="nick_name", nullable=true)
    @Field
    private String nickName;
    
    @Column(name="date_of_joining", nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    @Field(analyze=Analyze.NO, store=Store.YES)
    @DateBridge(resolution=Resolution.DAY)
    private Date dateOfJoning;
    
    @Column(name="emp_email", nullable=true)
    @Field
    private String email;
    
    @Column(name="employee_type", nullable=false)
    @Field
    //@Facet(name="emp-type-facet")
    private String employeeType;    // Full-time, part-time, intern etc
    
    //@XmlIDREF
    @ManyToOne
    @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
    @Boost(value=0.5F)
    private EmployeeBean manager;
    
    @ManyToOne
    @JoinColumn(name="department_ref")
    @ContainedIn
    private DepartmentBean departmentRef;
    
    
    @Embedded
    //@Column(name="revision_control", nullable=false)
    @IndexedEmbedded
    private RevisionControlBean revisionControl;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getDateOfJoning() {
        return dateOfJoning;
    }

    public void setDateOfJoning(Date dateOfJoning) {
        this.dateOfJoning = dateOfJoning;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeBean getManager() {
        return manager;
    }

    public void setManager(EmployeeBean manager) {
        this.manager = manager;
    }

    public DepartmentBean getDepartmentRef() {
        return departmentRef;
    }

    public void setDepartmentRef(DepartmentBean department) {
        this.departmentRef = department;
    }

    public RevisionControlBean getRevisionControl() {
        return revisionControl;
    }

    public void setRevisionControl(RevisionControlBean revisionControl) {
        this.revisionControl = revisionControl;
    }
    
    
    public EmpBeanPub convertTo() {
        
        EmpBeanPub emp = new EmpBeanPub();
        
        emp.setId(this.id);
        emp.setTenantId(this.tenantId);
        emp.setEmployeeNumber(this.employeeNumber);
        emp.setFirstName(this.firstName);
        emp.setLastName(this.lastName);
        emp.setNickName(this.nickName);
        if (this.manager != null)
            emp.setManager(this.manager.employeeNumber);
        
        if (this.departmentRef != null)
            emp.setDepartment(this.departmentRef.getDeptCode());
        
        return(emp);
    }
    
    public EmpBeanPub convertToLOV() {
        
        EmpBeanPub emp = new EmpBeanPub();
        
        emp.setId(this.id);
        emp.setTenantId(this.tenantId);
        emp.setEmployeeNumber(this.employeeNumber);
        emp.setFirstName(this.firstName);
        emp.setLastName(this.lastName);
        
        return(emp);
        
    }
    
}
