/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut;

import com.olp.jpa.domain.docu.ut.repo.EmployeeService;
import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.fwk.common.BaseSpringAwareTest;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author raghosh
 */
public class EmployeeServiceTest extends BaseSpringAwareTest {
    
    @Autowired
    @Qualifier("empService")
    private EmployeeService __service;
    
    @Test
    public void test_dummy() {
        assertTrue(true);
    }
    
    //@Test
    public void test_addEmployee() {
        
        EmployeeBean emp = makeEmp(27L);
        
        __service.add(emp);
        
        assertNotNull("ID should not be null", emp.getId());
    }
    
    //@Test
    public void test_getEmployee() {
        
        EmployeeBean emp = makeEmp(27L);
        
        __service.add(emp);
        
        Long id = emp.getId();
        
        EmployeeBean emp2 = __service.find(id);
        
        assertNotNull("Inserted and queried employee record cannot be null", emp2);
        
    //    assertNotNull("Referential read should not be null", emp2.getDepartment());
        
    //    assertNotNull("Referential read of non-id attributes should not be null", emp2.getDepartment().getDeptCode());
        
    }
    
    //@Test
    public void test_recreateTextIndex() {
        
        //__service.createTextIndex();
        
    }
    
    //@Test
    public void test_findEmployee() {
        /*
        List<EmployeeBean> list = __service.findEmployee("John", false);
        
        assertNotNull("Employee list should not be null", list);
        
        int count = list.size();
        
        assertTrue("List should have atleast two employees", count > 2);
        */
    }
    
    
    private EmployeeBean makeEmp() {
        
        EmployeeBean bean = new EmployeeBean();
        
        bean.setFirstName("John");
        bean.setLastName("Doe");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 01, 02);
        Date date = cal.getTime();
        bean.setDateOfJoning(date);
        bean.setNickName("Johnny");
        
        DepartmentBean dept = new DepartmentBean();
        
        String s = getRandom();
        dept.setDeptCode("DEPT_" + s);
        dept.setDeptName("Department  " + s);
        dept.setDescription("This is a random department, just to check search effectiveness !");
        
        bean.setDepartmentRef(dept);
        
        return(bean);
    }
    
    private EmployeeBean makeEmp(Long deptId) {
        
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
        
        return(bean);
    }
    
    private static final String EMP_A11538 = 
            "<ns3:employee employeeNumber=\"A11538\" xmlns:ns2=\"http://trilia-cloud.com/schema/entity/ut/ut-department/\" xmlns:ns3=\"http://trilia-cloud.com/schema/entity/ut/ut-employee/\">\n" +
            "    <id>1822</id>\n" +
            "    <firstName>Coleen</firstName>\n" +
            "    <lastName>Roonie</lastName>\n" +
            "    <nickName>Roonie</nickName>\n" +
            "    <dateOfJoning>2013-03-01T05:30:00+05:30</dateOfJoning>\n" +
            "    <email>coleen.roonie@trilia-tech.com</email>\n" +
            "    <manager>A12758</manager>\n" +
            "    <ns2:departmentRef>OPRTN</ns2:departmentRef>\n" +
            "    <revisionControl>\n" +
            "        <createdBy>UT</createdBy>\n" +
            "        <createdById>100</createdById>\n" +
            "        <creationDate>2013-02-28T18:54:47+05:30</creationDate>\n" +
            "        <objectVersionNumber>1.0.0</objectVersionNumber>\n" +
            "        <revisedBy>UT</revisedBy>\n" +
            "        <revisedById>100</revisedById>\n" +
            "        <revisionDate>2016-02-25T14:02:02.278+05:30</revisionDate>\n" +
            "    </revisionControl>\n" +
            "</ns3:employee>";
    
}
