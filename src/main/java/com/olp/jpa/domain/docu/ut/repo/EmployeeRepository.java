/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import java.util.List;
import java.util.concurrent.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface EmployeeRepository extends JpaRepository<EmployeeBean, Long>, ITextRepository<EmployeeBean, Long> {
    
    public EmployeeBean findByEmpNumber(String empNum);
    
    public List<EmployeeBean> findByDeptId(Long deptId);
    
    public List<EmployeeBean> findByDeptCode(String deptCode);
    
    public List<EmployeeBean> findText(String keywords, boolean fuzzy);
    
    public Future createTextIndex();
    
    public EmployeeBean createEmployee(EmployeeBean employee, DepartmentBean dept, EmployeeBean manager);
    
}
