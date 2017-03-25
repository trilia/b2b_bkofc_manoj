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
import java.util.Iterator;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Service("deptService")
public class DepartmentServiceImpl extends AbstractServiceImpl<DepartmentBean, Long> implements DepartmentService {
    
    @Autowired
    @Qualifier("deptRepository")
    private DepartmentRepository deptRepo;
    
    @Autowired
    @Qualifier("empRepository")
    private EmployeeRepository empRepo;
    
    public DepartmentServiceImpl() {
        super();
    }
    
    @Override
    @Transactional(readOnly=true)
    public DepartmentBean findByDeptCode(String deptCode) {
        
        DepartmentBean bean = deptRepo.findByDeptCode(deptCode);
        
        return(bean);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<EmployeeBean> getEmployees(Long id) {
        
        DepartmentBean dept = deptRepo.findOne(id);
        
        List<EmployeeBean> list = dept.getEmployees();
        
        return(list);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<EmployeeBean> getEmployees(String deptCode) {
        
        DepartmentBean dept = deptRepo.findByDeptCode(deptCode);
        
        List<EmployeeBean> list = dept.getEmployees();
        
        return(list);
    }
    
    @Override
    @Transactional
    public boolean removeEmployee(DepartmentBean dept, String empNumber) {
        
        if (dept == null)
            return(false);
        
        List<EmployeeBean> list = dept.getEmployees();
        boolean result = false; int index = -1;
        for (int j = 0; list != null && j < list.size(); j++) {
            EmployeeBean bean = list.get(j);
            if (bean.getEmployeeNumber().equals(empNumber)) {
                index = j;
                break;
            }
        }
        if (index > -1) {
            list.remove(index);
            result = true;
        }
        
        return(result);
    }
    
    /*
    @Transactional
    public void delete(String deptCode) {
        
        DepartmentBean dept = findByDeptCode(deptCode);
        removeReferences(dept);
        deptRepo.delete(dept);
        
    }
    
    @Override
    @Transactional
    public void deleteAll(boolean ignoreError) {
        
        List<DepartmentBean> list = findAll();
        for (int i = 0; list != null && i < list.size(); i++) {
            DepartmentBean bean = list.get(i);
            removeReferences(bean);
            deptRepo.delete(bean);
        }
    }
    
    @Override
    public List<DepartmentBean> find(SortBean sort) {
        
        List<DepartmentBean> list = null;
        
        if (sort == null) {
            list = deptRepo.findAll();
        } else {
            boolean isAscending = true;
            if (sort.getSortOrder() != null && sort.getSortOrder() == SortOrderBean.DESC)
                isAscending = false;
            Sort s = new Sort(isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sort.getAttributes());
            list = deptRepo.findAll(s);
        }
        
        return(list);
    }
    
    @Override
    public List<DepartmentBean> findAll() {
        
        List<DepartmentBean> list = deptRepo.findAll();
        
        return(list);
    }
    
    @Override
    public DepartmentBean find(Long id) {
        
        DepartmentBean bean = deptRepo.findOne(id);
        
        return(bean);
    }
    
    @Override
    public List<DepartmentBean> find(Iterable<Long> itrbl) {
        
        List<DepartmentBean> list = deptRepo.findAll(itrbl);
        
        return(list);
    }
    
    @Override
    public Long add(DepartmentBean dept) {
        
        Long deptId = null;
        
        if (dept != null) {
            
            dept.setId(null);
            deptRepo.save(dept);
            deptId = dept.getId();
        
        }
        
        return(deptId);
    }
    
    @Override
    public List<Long> addAll(Iterable<DepartmentBean> deptList) {
        
        List<Long> idList = addAll(deptList, true);
        
        return(idList);
    }
    
    @Override
    public List<Long> addAll(Iterable<DepartmentBean> deptList, boolean ignoreError) {
        
        Logger logger = Logger.getLogger(this.getClass().getName());
        
        if (deptList == null) {
            logger.log(Level.WARNING, "No entity list found !!");
            return null;
        }
        
        ArrayList<Long> idList = new ArrayList<>();
        Iterator<DepartmentBean> iter = deptList.iterator();
        
        if (ignoreError) {
            while (iter.hasNext()) {
                DepartmentBean bean = iter.next();
                try {
                    deptRepo.save(bean);
                    idList.add(bean.getId());
                } catch (Throwable t) {
                    logger.log(Level.WARNING, "Error while adding entity instance !!", t);
                }
            }
        } else {
            deptRepo.save(deptList);
            while (iter.hasNext()) {
                DepartmentBean bean = iter.next();
                idList.add(bean.getId());
            }
        }
        
        return(idList);
        
    }
    
    @Override
    public DepartmentBean update(DepartmentBean entity) {
        
        Long id = entity.getId();
        if (id == null)
            throw new RuntimeException("The id cannot be null !! Dept Code - " + entity.getDeptCode());
        
        deptRepo.save(entity);
        
        return(entity);
    }
    
    public void deleteAll() {
        
        deptRepo.deleteAll();
        
    }
    
    */
    
    @Override
    protected JpaRepository<DepartmentBean, Long> getRepository() {
        return(deptRepo);
    }
    
    @Override
    protected ITextRepository<DepartmentBean, Long> getTextRepository() {
        return(deptRepo);
    }

    @Override
    protected String getAlternateKeyAsString(DepartmentBean entity) {
        
        StringBuilder buff = new StringBuilder(50);
        buff.append("{ \"dept_code\" : \"").append(entity.getDeptCode()).append("\" }");
        
        return(buff.toString());
    }
    
    @Override
    protected Outcome preProcess(int opCode, DepartmentBean entity) {
        
        Outcome result = new Outcome();
        
        switch(opCode) {
            
            case DELETE : {
                removeReferences(entity);
                result.setResult(true);
                break;
            }
            case DELETE_BULK : {
                removeReferences(entity);
                result.setResult(true);
                break;
            }
            default : {
                result.setResult(true);
            }
            
        }
        
        return(result);
    }

    @Override
    protected Outcome postProcess(int opCode, DepartmentBean entity) {
        
        Outcome result = new Outcome();
        result.setResult(true);
        
        return(result);
    }
    
    
    private void removeReferences(DepartmentBean dept) {
        
        if (dept == null)
            return;
        
        List<EmployeeBean> list = dept.getEmployees();
        for (int i = 0; list != null && i < list.size(); i++) {
            EmployeeBean emp = list.get(i);
            emp.setDepartmentRef(null);
        }
        
        dept.getEmployees().clear();
    }
    
    //@Transactional
    private void removeReferences(List<DepartmentBean> list) {
        
        List<DepartmentBean> list2;
        if (list == null) {
            list2 = findAll();
        } else {
            list2 = list;
        }
        
        if (list2 != null) {
            
            Iterator<DepartmentBean> iter = list2.iterator();
            while (iter != null && iter.hasNext()) {
                DepartmentBean bean = iter.next();
                List<EmployeeBean> empList;
                if (bean.getId() == null) {
                    empList = empRepo.findByDeptCode(bean.getDeptCode());
                } else {
                    empList = empRepo.findByDeptId(bean.getId());
                }
                
                if (empList != null) {
                    Iterator<EmployeeBean> iter2 = empList.iterator();
                    while (iter2 != null && iter2.hasNext()) {
                        EmployeeBean empBean = iter2.next();
                        empBean.setDepartmentRef(null);
                    }
                    //empRepo.save(empList);
                }
            }
            
        }
        
    }

}
