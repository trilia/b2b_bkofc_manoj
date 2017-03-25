/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.repo.DepartmentService;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.fwk.common.BaseSpringAwareTest;
import com.olp.fwk.common.ContextManager;
import com.olp.fwk.common.IContext;
import com.olp.jpa.common.PageRequestBean;
import com.olp.jpa.common.PageResponseBean;
import com.olp.jpa.common.RevisionControlBean;
import com.olp.jpa.common.SortCriteriaBean;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.junit.Assert.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author raghosh
 */
public class DepartmentServiceTest extends BaseSpringAwareTest {
    
    @Autowired
    @Qualifier("deptService")
    private DepartmentService __service;
    
    //@Test
    public void test_addDepartment() {
        DepartmentBean dept = makeDept();
        __service.add(dept);
    }
    
    @Test
    public void test_getEmployees() {
        
        List<EmployeeBean> list = __service.getEmployees("BOARD");
        
        assertNotNull("Employees in BOARD dept should not be null", list);
    }
    
    //@Test
    public void test_findDeptPaged() {
        
        //PageRequestBean request = new PageRequestBean();
        //request.setPageNum(2);
        //request.setPageSize(4);
        
        PageRequest request = new PageRequest(1, 4);
        
        Page<DepartmentBean> response = __service.findAll(request);
        
        assertNotNull("Matching page search should not be null", response);
        assertNotNull("Matching page content should not be null", response.getContent());
        
        assertTrue("Atleast 1 result", response.getContent().size() >= 1);
        assertTrue("Max 4 results", response.getContent().size() == 4);
    }
    
    //@Test
    public void test_simpleSearch1() {
        
        List<DepartmentBean> list = __service.findText("cloud", false, (SortCriteriaBean) null);
        
        assertNotNull("Matching cloud keyword should not be null", list);
        assertTrue("Atleast 1 result", list.size() >= 1);
    }
    
    //@Test
    public void test_simpleSearch2() {
        
        SortCriteriaBean sort = new SortCriteriaBean();
        List<String> fields = new ArrayList<>();
        fields.add("deptCode");
        fields.add("deptName");
        
        sort.setDescOrderFields(fields);
        
        List<DepartmentBean> list = __service.findText("trilia", false, sort);
        
        assertNotNull("Matching trilia keyword should not be null", list);
        assertTrue("Atleast 1 result", list.size() >= 1);
        
    }
    
    //@Test
    public void test_simpleSearch3() {
        
        List<DepartmentBean> list = __service.findText("trla", true, (SortCriteriaBean) null);
        
        assertNotNull("Matching fuzzy search should not be null", list);
        assertTrue("Atleast 1 result", list.size() >= 1);
    }
    
    //@Test
    public void test_simpleSearch4() {
        
        //PageRequestBean request = new PageRequestBean();
        //request.setPageNum(2);
        //request.setPageSize(3);
        
        PageRequest request = new PageRequest(1, 3);
        
        Page<DepartmentBean> response = __service.findText("trla", true, request);
        
        assertNotNull("Matching page search should not be null", response);
        assertNotNull("Matching page content should not be null", response.getContent());
        
        assertTrue("Atleast 1 result", response.getContent().size() >= 1);
        assertTrue("Max 3 results", response.getContent().size() == 3);
        
    }
    
    //@Test
    public void test_marshalling() throws JAXBException {
        
        List<DepartmentBean> list = __service.findAll();
        
        DepartmentBean bean = __service.findByDeptCode("OPRTN");
        
        JAXBContext ctx = JAXBContext.newInstance(DepartmentBean.class);
        StringWriter writer = new StringWriter();
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(bean, writer);
      
        final String xml = writer.toString();
        System.out.println(xml);
        
    }
    
    private DepartmentBean makeDept() {
        
        DepartmentBean dept = new DepartmentBean();
        
        String s = getRandom();
        
        IContext ctx = ContextManager.getContext();
        String tid = ctx.getTenantId();
        
        dept.setDeptCode("DEPT_" + s);
        dept.setDeptName("Department  " + s);
        dept.setTenantId(tid);
        
        RevisionControlBean ctrl = new RevisionControlBean();
        ctrl.setCreatedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setRevisedById(100);
        ctrl.setCreatedBy("ut@trilia.com");
        ctrl.setCreationDate(new java.util.Date());
        ctrl.setObjectVersionNumber("1.0.0");
        
        dept.setRevisionControl(ctrl);
        
        return(dept);
        
    }
}
