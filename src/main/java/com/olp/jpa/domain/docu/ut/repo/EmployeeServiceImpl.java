/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.jpa.common.AbstractServiceImpl;
import com.olp.jpa.common.ITextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Service("empService")
public class EmployeeServiceImpl extends AbstractServiceImpl<EmployeeBean, Long> implements EmployeeService {
    
    @Autowired
    @Qualifier("empRepository")
    private EmployeeRepository empRepo;
    
    @Autowired
    @Qualifier("deptRepository")
    private DepartmentRepository deptRepo;
    
    @Override
    protected JpaRepository<EmployeeBean, Long> getRepository() {
        return(empRepo);
    }
    
    @Override
    protected ITextRepository<EmployeeBean, Long> getTextRepository() {
        return(empRepo);
    }
    
    
    @Override
    protected Outcome preProcess(int opCode, EmployeeBean entity) {
        
        Outcome result = new Outcome();
        switch(opCode) {
            
            case ADD : {
                result = preProcessAdd(entity);
                break;
            }
            case ADD_BULK : {
                result = preProcessAdd(entity);
                break;
            }
            default: {
                result.setResult(true);
            }
            
        }
            
        return(result);
        
    }
    
    @Override
    protected Outcome postProcess(int opCode, EmployeeBean entity) {
        
        Outcome result = new Outcome();
        result.setResult(true);
        
        return(result);
    }
    
    
    @Override
    protected String getAlternateKeyAsString(EmployeeBean entity) {
        
        StringBuilder buff = new StringBuilder(50);
        buff.append("{ \"employee_number\" : \"").append(entity.getEmployeeNumber()).append("\" }");
        
        return(buff.toString());
    }
    
    private DepartmentBean validateDept(Long deptId) {
        
        DepartmentBean dept2 = null;

        if (deptId != null) {
            
                dept2 = deptRepo.findOne(deptId);
                
        }
        
        return(dept2);
    }
    
    
    private Outcome preProcessAdd(EmployeeBean entity) {
        
        Outcome result = new Outcome();
        if (entity == null) {
            result.setResult(false);
            result.setErrorMessage("Null entity found for EmployeeBean !!");
        } else {
            DepartmentBean dept = entity.getDepartmentRef(), dept2 = null;
            if (dept != null) {
                Long deptId = dept.getId();
                if (deptId == null) {
                    String deptCode = dept.getDeptCode();
                    if (deptCode == null || "".equals(deptCode)) {
                        result.setResult(false);
                        result.setErrorMessage("Could not validate department. Either department id or code should be present !");
                    } else {
                        dept2 = deptRepo.findByDeptCode(deptCode);
                        if (dept2 == null) {
                            result.setResult(false);
                            result.setErrorMessage("Could not validate department with dept code - " + deptCode);
                        }
                    }
                } else {
                    dept2 = deptRepo.findOne(deptId);
                    if (dept2 == null) {
                        result.setResult(false);
                        result.setErrorMessage("Could not validate department with dept id - " + deptId);
                    }
                }
            }
            
            if (dept2 != null) {
                entity.setDepartmentRef(dept2);
                result.setResult(true);
            }
            
            EmployeeBean mgr = entity.getManager(), mgr2 = null;
            if (mgr != null) {
                Long mgrId = mgr.getId();
                if (mgrId == null) {
                    String mgrCode = mgr.getEmployeeNumber();
                    if (mgrCode == null || "".equals(mgrCode)) {
                        result.setResult(false);
                        result.setErrorMessage("Could not validate manager entity. Either id or employee code should be present !");
                    }
                    mgr2 = empRepo.findByEmpNumber(mgrCode);
                    if (mgr2 == null) {
                        result.setResult(false);
                        result.setErrorMessage("Could not validate manager with employee code - " + mgrCode);
                    }
                } else {
                    mgr2 = empRepo.findOne(mgrId);
                    if (mgr2 == null) {
                        result.setResult(false);
                        result.setErrorMessage("Could not validate manager with employee id - " + mgrId);
                    }
                }
            }
            
            if (mgr2 != null) {
                entity.setManager(mgr2);
                result.setResult(true);
            }
            
        }
        
        return(result);
    }
    
    @Override
    @Transactional(readOnly=true)
    public EmployeeBean find(String empCode) {
        
        EmployeeBean bean = empRepo.findByEmpNumber(empCode);
        
        return(bean);
    }

    @Override
    @Transactional
    public EmployeeBean createEmployee(EmployeeBean employee, String managerEmpCode) {
        
        if (employee == null)
            return(null);
        
        EmployeeBean manager = null;
        if (!(managerEmpCode == null || "".equals(managerEmpCode))) {
            manager = empRepo.findByEmpNumber(managerEmpCode);
        }
        
        if (manager == null)
            throw new RuntimeException("No manager with emp code - " + managerEmpCode);
        
        employee.setDepartmentRef(null);
        employee.setManager(null);
        
        empRepo.save(employee);
        
        employee.setManager(manager);
        
        return(employee);
    }

    @Override
    @Transactional
    public EmployeeBean createEmployee(EmployeeBean employee, String managerEmpCode, String deptCode) {
        
        if (employee == null)
            return(null);
        
        EmployeeBean manager = null;
        if (!(managerEmpCode == null || "".equals(managerEmpCode))) {
            manager = empRepo.findByEmpNumber(managerEmpCode);
        }
        
        if (manager == null)
            throw new RuntimeException("No manager with emp code - " + managerEmpCode);
        
        DepartmentBean dept = null;
        if (!(deptCode == null || "".equals(deptCode))) {
            dept = deptRepo.findByDeptCode(deptCode);
        }
        
        if (dept == null)
            throw new RuntimeException("No department exist with dept code - " + deptCode);
        
        employee.setDepartmentRef(null);
        employee.setManager(null);
        
        empRepo.save(employee);
        
        employee.setManager(manager);
        employee.setDepartmentRef(dept);
        
        return(employee);
        
    }

    @Override
    @Transactional
    public boolean assignDepartment(String employeeCode, String deptCode) {
        
        EmployeeBean emp = null;
        if (!(employeeCode == null || "".equals(employeeCode))) {
            emp = empRepo.findByEmpNumber(employeeCode);
        }
        
        if (emp == null)
            throw new NullPointerException("No employee found with emp code - " + employeeCode);
        
        DepartmentBean dept = null;
        if (!(deptCode == null || "".equals(deptCode))) {
            dept = deptRepo.findByDeptCode(deptCode);
        }
        
        if (dept == null)
            throw new NullPointerException("No department found with dept code - " + deptCode);
        
        
        
        return(true);
        
    }

    @Override
    public boolean assignManager(String employeeCode, String managerEmpCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
