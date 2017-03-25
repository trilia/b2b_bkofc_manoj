/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.jpa.common.IJpaService;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface DepartmentService extends IJpaService<DepartmentBean, Long> {
    
    public DepartmentBean findByDeptCode(String deptCode);
    
    public List<EmployeeBean> getEmployees(Long id);
    
    public List<EmployeeBean> getEmployees(String deptCode);
    
    public boolean removeEmployee(DepartmentBean dept, String empNumber);
}
