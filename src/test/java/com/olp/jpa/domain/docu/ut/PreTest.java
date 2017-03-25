/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DeptContainer;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.DataLoader;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.domain.docu.ut.model.DeptBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmpBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmpContainer;
import com.olp.jpa.domain.docu.ut.service.DeptWebService;
import com.olp.jpa.domain.docu.ut.service.EmpWebService;
import com.olp.service.ServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

/**
 * 
 * @author raghosh
 */
public class PreTest extends BaseSpringAwareTest {
    
    @Autowired
    @Qualifier("deptWebService")
    private DeptWebService deptSvc;
    
    @Autowired
    @Qualifier("empWebService")
    private EmpWebService empSvc;
    
    
    @Before
    public void deleteAll() throws ServiceException {
        
        // Note: While adding any tests , remember that this method will delete all 
        // records before any test is run.
        
        deptSvc.deleteAll(false);
        
        empSvc.deleteAll(false);
        
    }
    
    @Test
    //@Rollback(false)
    public void loadTestData() {
        
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<DeptBeanPub> list1;
        
        InputStream is1 = loader.getResourceAsStream("test_dept_data_01.xml");
        DataLoader<DeptBeanPub, Long> deptLoader = new DataLoader<>(DeptContainer.class, is1);
        list1 = deptLoader.load(deptSvc, false);
        
        if (is1 != null) {
            try { is1.close(); } catch (IOException ex) {  
                //no-op
            }
        }
        
        
        List<EmpBeanPub> list2;
        InputStream is2 = loader.getResourceAsStream("test_emp_data_01.xml");
        DataLoader<EmpBeanPub, Long> empLoader = new DataLoader<>(EmpContainer.class, is2);
        list2 = empLoader.load(empSvc, false);
        
        if (is2 != null) {
            try { is2.close(); } catch (IOException ex) {
                // no-op  
            }
        }
        
    }
    
    //@Test
    //@Rollback(false)
    public void dummy() {
        
    }
    
    private DepartmentBean makeRandomDept() {
        
        DepartmentBean dept = new DepartmentBean();
        
        String s = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
                
        dept.setDeptCode("DEPT_" + s);
        dept.setDeptName("Department  " + s);
        dept.setTenantId(tid);
        
        RevisionControlBean ctrl = new RevisionControlBean();
        ctrl.setCreatedById(100);
        ctrl.setCreatedBy("john.doe@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setRevisedById(100);
        ctrl.setCreatedBy("john.doe@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setObjectVersionNumber("1.0.0");
        
        dept.setRevisionControl(ctrl);
        
        return(dept);
        
    }
    
    private EmployeeBean makeRandomEmp(Long deptId) {
        
        EmployeeBean bean = new EmployeeBean();
        
        bean.setFirstName("John");
        bean.setLastName("Doe");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 01, 02);
        Date date = cal.getTime();
        bean.setDateOfJoning(date);
        bean.setNickName("Johnny");
        //bean.setManagerId(-1L);
        
        DepartmentBean dept = new DepartmentBean();
        dept.setId(deptId);
        
        bean.setDepartmentRef(dept);
        
        RevisionControlBean ctrl = new RevisionControlBean();
        ctrl.setCreatedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setRevisedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setObjectVersionNumber("1.0.0");
        
        bean.setRevisionControl(ctrl);
        
        return(bean);
    }
    
}
