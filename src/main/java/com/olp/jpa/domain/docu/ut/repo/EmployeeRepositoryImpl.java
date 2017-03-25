/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.annotations.MultiTenant;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Repository("empRepository")
public class EmployeeRepositoryImpl extends AbstractRepositoryImpl<EmployeeBean, Long> implements EmployeeRepository {

    @Override
    public EmployeeBean findByEmpNumber(String empNum) {
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<EmployeeBean> query = getEntityManager().createNamedQuery("UtEmployeeBean.findByEmpCode", EmployeeBean.class);
        query.setParameter("empCode", empNum); // TODO: Sanitize input, although low risk due to binding
        query.setParameter("tenant", tid);
        
        EmployeeBean bean = query.getSingleResult();
        
        return(bean);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<EmployeeBean> findByDeptId(Long deptId) {
        
        List<EmployeeBean> list = findByForeignIdRef("deptId", Long.toString(deptId));
        
        return(list);
    }

    @Override
    @Transactional(readOnly=true)
    public List<EmployeeBean> findByDeptCode(String deptCode) {
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        TypedQuery<EmployeeBean> query = getEntityManager().createNamedQuery("UtEmployeeBean.findByDeptCode", EmployeeBean.class);
        query.setParameter("deptCode", deptCode); // TODO: Sanitize input, although low risk due to binding
        query.setParameter("tenant", tid);
        
        List<EmployeeBean> list = query.getResultList();
        
        return(list);
        
    }

    @Override
    @Transactional
    public EmployeeBean createEmployee(EmployeeBean employee, DepartmentBean dept, EmployeeBean manager) {
        
        save(employee);
        
        EntityManager em = getEntityManager();
        
        if (dept != null) {
            /*
            if (em.contains(dept)) {
                employee.setCurrentDept(dept);
                dept.getEmployees().add(employee);
            } else {
                
                DepartmentBean dept2 = em.merge(dept);
                employee.setCurrentDept(dept2);
                dept2.getEmployees().add(employee);
            }
            */
            
            dept = em.find(DepartmentBean.class, dept.getId());
            
            employee.setDepartmentRef(dept);
            dept.getEmployees().add(employee);
            
        }
        
        return(employee);
    }

    @Override
    public String getLazyLoadElements() {
        return(null);
    }

    @Override
    public List<EmployeeBean> findText(String keywords, boolean fuzzy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
