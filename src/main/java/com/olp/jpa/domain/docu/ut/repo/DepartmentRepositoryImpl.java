/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.AbstractRepositoryImpl;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Repository("deptRepository")
public class DepartmentRepositoryImpl extends AbstractRepositoryImpl<DepartmentBean, Long> implements DepartmentRepository {
    
    @Override
    @Transactional(readOnly=true)
    public DepartmentBean findByDeptCode(String deptCode) {
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        //EntityGraph graph = getEntityManager().getEntityGraph("graph.departmentBean.employees");
        
        TypedQuery<DepartmentBean> query = getEntityManager().createNamedQuery("DepartmentBean.findByCode", DepartmentBean.class);
        query.setParameter("code", deptCode); // TODO: Sanitize input, although low risk due to binding
        query.setParameter("tenant", tid);
        
        //query.setHint("javax.persistence.fetchgraph", graph); // Entity graph is not supported yet in OGM
        
        DepartmentBean bean = query.getSingleResult();
        
        //if (bean != null && bean.getEmployees() != null)
        //    bean.getEmployees().size(); // Apparently no way to avoid loading child entity instances, hence EAGER loading
        
        return(bean);
    }

    @Override
    public String getLazyLoadElements() {
        return("t.employees");
    }
    /*
    @Override
    public List<DepartmentBean> findAll(Example<DepartmentBean> exmpl) {
        return(null);
    }

    @Override
    public List<DepartmentBean> findAll(Example<DepartmentBean> exmpl, Sort sort) {
        return(null);
    }
    */
}
