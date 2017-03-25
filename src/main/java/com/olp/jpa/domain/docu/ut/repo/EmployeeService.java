/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.common.IJpaService;
import com.olp.jpa.common.SortCriteriaBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
//@WebService(targetNamespace="http://trilia-cloud.com/schema/entity/ut/ut-employee-service/")
public interface EmployeeService extends IJpaService<EmployeeBean, Long> {
    
    @Override
    @WebMethod(operationName="listAllEmployees")
    public List<EmployeeBean> findAll();
    
    @Override
    @WebMethod(operationName="listAllEmployeesSorted")
    public List<EmployeeBean> findAll(@WebParam(name="sortCriteria") SortCriteriaBean sort);
    
    @Override
    @WebMethod(operationName="findEmployeeById")
    public EmployeeBean find(@WebParam(name="employeeInternalId") Long id);
    
    @WebMethod(operationName="findEmployeeByCode")
    public EmployeeBean find(String empCode);
    
    @Override
    @WebMethod(operationName="addEmployee")
    public EmployeeBean add(@WebParam(name="employee") EmployeeBean entity);
    
    @Override
    @WebMethod(operationName="updateEmployee")
    public EmployeeBean update(@WebParam(name="employee") EmployeeBean entity);
    
    @WebMethod(operationName="createEmployeeAndAssignManager")
    public EmployeeBean createEmployee(@WebParam(name="employee") EmployeeBean employee, 
            @WebParam(name="managerEmployeeCode") String managerEmpCode);
    
    @WebMethod(operationName="createEmployeeAssignManagerDept")
    public EmployeeBean createEmployee(@WebParam(name="employee") EmployeeBean employee, 
            @WebParam(name="managerEmployeeCode") String managerEmpCode, @WebParam(name="departmentCode") String deptCode);
    
    @WebMethod(operationName="assignDepartment")
    public boolean assignDepartment(@WebParam(name="employeeCode") String employeeCode, @WebParam(name="departmentCode") String deptCode);
    
    @WebMethod(operationName="assignManager")
    public boolean assignManager(@WebParam(name="employeeCode") String employeeCode, @WebParam(name="managerEmployeeCode") String managerEmpCode);
    
    @Override
    public List<Long> addAll(List<EmployeeBean> list, boolean ignoreError);
    
    @Override
    public List<Long> updateAll(List<EmployeeBean> list, boolean ignoreError);
    
    @Override
    public void deleteAll(boolean ignoreError);
    
    @Override
    public List<EmployeeBean> find(List<Long> list);
}
