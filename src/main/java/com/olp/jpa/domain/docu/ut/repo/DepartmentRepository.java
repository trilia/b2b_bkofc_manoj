/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import com.olp.jpa.common.ITextRepository;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
public interface DepartmentRepository extends JpaRepository<DepartmentBean, Long>, ITextRepository<DepartmentBean, Long> {
    
    public DepartmentBean findByDeptCode(String deptCode);
    
}
