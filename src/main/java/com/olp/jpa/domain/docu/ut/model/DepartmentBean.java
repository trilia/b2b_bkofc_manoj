/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import com.olp.annotations.MultiTenant;
import com.olp.annotations.SortCriteria;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.TenantBasedSearchFilterFactory;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
//import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author raghosh
 */
@Entity
@Table(name="ut_department"
       , uniqueConstraints=@UniqueConstraint(columnNames={"tenant_id", "dept_code"})
)
@NamedQueries({
    @NamedQuery(name="DepartmentBean.findByCode", query="SELECT t from DepartmentBean t join fetch t.employees WHERE t.deptCode = :code and t.tenantId = :tenant")
})
@Cacheable(true)
//@NamedEntityGraph(name="graph.departmentBean.employees", attributeNodes = @NamedAttributeNode("employees"))
@Indexed(index="UnitTest")
@FullTextFilterDef(name="filter-dept-by-tenant", impl=TenantBasedSearchFilterFactory.class)
@MultiTenant(level = MultiTenant.Levels.ONE_TENANT)
@SortCriteria(attributes={"deptCode"})
public class DepartmentBean implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @Column(name="department_id", nullable=false)
    //@Type(type = "objectid")
    private Long id;

    //@Version
    @Column(name="opt_lock")
    private Long version;
    
    @Column(name="tenant_id", nullable=false)
    @Field(store=Store.YES, analyze=Analyze.NO)
    private String tenantId;
    
    //@XmlID
    @Column(name="dept_code", nullable=false)
    @Fields({
        @Field,
        @Field(name="deptCode-sort", index=Index.YES, store=Store.YES, analyze=Analyze.NO)
    })
    private String deptCode;
    
    @Column(name="dept_name", nullable=false)
    @Fields({
        @Field,
        @Field(name="deptName-sort", index=Index.YES, store=Store.YES, analyze=Analyze.NO)
    })
    //@SortableField(forField="deptName-sort")
    private String deptName;
    
    @Column(name="dept_description")
    @Field
    private String description;
    
    //@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @OneToMany(mappedBy="departmentRef", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @IndexedEmbedded(includeEmbeddedObjectId=true, depth=1)
    private List<EmployeeBean> employees = new ArrayList<>();
    
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
    
    public List<EmployeeBean> getEmployees() {
        return (employees);
    }

    public void setEmployees(List<EmployeeBean> employees) {
        this.employees = employees;
    }

    public RevisionControlBean getRevisionControl() {
        return revisionControl;
    }

    public void setRevisionControl(RevisionControlBean revisionControl) {
        this.revisionControl = revisionControl;
    }
    
    public DeptBeanPub convertTo() {
        
        DeptBeanPub dept = new DeptBeanPub();
        dept.setId(this.id);
        dept.setTenantId(this.tenantId);
        dept.setDeptCode(this.deptCode);
        dept.setDeptName(this.deptName);
        dept.setDescription(this.description);
        ArrayList<String> list = this.employees == null ? null : new ArrayList<String>();
        for (int i=0; this.employees != null && i < this.employees.size(); i++) {
            EmployeeBean emp = employees.get(i);
            list.add(emp.getEmployeeNumber());
        }
        dept.setEmployees(list);
        
        if (this.revisionControl != null)
            dept.setRevisionControl(this.revisionControl.clone());
        
        return(dept);
    }
    
    public DeptBeanPub convertToLOV() {
        
        DeptBeanPub dept = new DeptBeanPub();
        dept.setId(this.id);
        dept.setTenantId(this.tenantId);
        dept.setDeptCode(this.deptCode);
        dept.setDeptName(this.deptName);
        
        return(dept);
    }

}
