/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.service;

import com.olp.jpa.common.PageRequestBean;
import com.olp.jpa.common.PageResponseBean;
import com.olp.jpa.common.SearchCriteriaBean;
import com.olp.jpa.common.SortCriteriaBean;
import com.olp.service.ServiceVersion;
import com.olp.jpa.domain.docu.ut.repo.DepartmentService;
import com.olp.jpa.domain.docu.ut.model.DepartmentBean;
import com.olp.jpa.domain.docu.ut.model.DeptBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmpBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmployeeBean;
import com.olp.jpa.util.JpaUtil;
import com.olp.service.FaultUtil;
import com.olp.service.ServiceException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.annotations.UseAsyncMethod;
import org.apache.cxf.jaxws.ServerAsyncResponse;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Component("deptWebService")
@WebService(serviceName="DeptService", 
        endpointInterface="com.olp.jpa.domain.docu.ut.service.DeptWebService", 
        targetNamespace="http://trilia-cloud.com/webservices/entity/ut/ut-dept-service", 
        portName="DeptService_Port"
        )
@Path("/departments")
public class DeptWebServiceImpl implements DeptWebService {
    
    @Resource
    private WebServiceContext context;

    @Autowired
    @Qualifier("deptService")
    private DepartmentService service;
    
    @Autowired
    private ServiceVersion svcVersion;
    
    @Override
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ServiceVersion getVersion() {
        return(svcVersion);
    }
    
    
    @Override
    @Transactional(readOnly=true)
    @Path("/department/{deptId: \\d+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeptBeanPub findOne(@PathParam("deptId") Long id) throws ServiceException {
        
        DeptBeanPub bean2 = null;
        try {
            DepartmentBean bean1 = service.find(id);
            bean2 = bean1.convertTo();
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in listAllDepts service", t);
            throw se;
        }
        
        return(bean2);
    }
    
    
    @Override
    @Transactional(readOnly=true)
    @Path("/department/{deptCode: ^[a-zA-Z]+(\\d+)*}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeptBeanPub findByAltKey(@PathParam("deptCode") String deptCode) throws ServiceException {
        
        DeptBeanPub bean2 = null;
        try {
            DepartmentBean bean1 = service.findByDeptCode(deptCode);
            bean2 = bean1.convertTo();
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in findDeptByCode service", t);
            throw se;
        }
        
        return(bean2);
    }
    
    
    @Override
    @Transactional
    @Path("/department")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeptBeanPub add(DeptBeanPub dept) throws ServiceException {
        
        if (dept == null) {
            ServiceException se = FaultUtil.makeServiceException("Null department parameter received", new IllegalArgumentException("Null department parameter received !"));
            throw se;
        }
        
        if (dept.getDeptCode() == null) {
            ServiceException se = FaultUtil.makeServiceException("Null department parameter received", new IllegalArgumentException("Null department code attribute received !"));
            throw se;
        }
        
        DeptBeanPub bean2 = null;
        try {
            DepartmentBean bean1 = dept.convertTo(0);
            service.add(bean1);
            dept.setId(bean1.getId());
            dept.setRevisionControl(bean1.getRevisionControl());
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occurred in addDept service", t);
            throw se;
        }
        
        return(bean2);
    }
    
    @Override
    @Transactional
    @Path("/department")
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeptBeanPub update(DeptBeanPub dept) throws ServiceException {
        
        if (dept == null) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service", new IllegalArgumentException("Null department parameter received !"));
            throw se;
        }
        if (dept.getId() == null || dept.getId() <= 0) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service", new IllegalArgumentException("Null or invalid department id"));
            throw se;
        }
        
        DepartmentBean bean = null;
        try {
            bean = service.find(dept.getId());
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service while fetching department object with id " + dept.getId(), t);
            throw se;
        }
        
        if (bean == null) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service - null department object", new IllegalArgumentException("No department with id " + dept.getId() + " exists !"));
            throw se;
        }
        
        if (!bean.getDeptCode().equals(dept.getDeptCode())) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service - id & code mismatch", new IllegalArgumentException("No department with code " + dept.getDeptCode() + " exists for id " + dept.getId()));
            throw se;
        }
        
        bean.setDeptName(dept.getDeptName());
        bean.setDescription(dept.getDescription());
        
        try {
            service.update(bean);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in update service while updating department object with id " + dept.getId(), t);
            throw se;
        }
        
        DeptBeanPub bean2 = bean.convertTo();
        
        return(bean2);
    }

    @Override
    @Transactional
    @Path("/department/{deptId: \\d+}")
    @DELETE
    public void delete(@PathParam("deptId") Long id) throws ServiceException {
        
        if (id == null || id <= 0) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service", new IllegalArgumentException("Null department id parameter received !"));
            throw se;
        }
        
        try {
            service.delete(id);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service while deleting department with id " + id, t);
            throw se;
        }
        
    }

    @Override
    @Transactional
    @Path("/department/{deptCode: ^[a-zA-Z]+(\\d+)*}")
    @DELETE
    public void delete(@PathParam("deptCode") String deptCode) throws ServiceException {
        
        if (deptCode == null || "".equals(deptCode)) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service", new IllegalArgumentException("Null department code parameter received !"));
            throw se;
        }
        
        DepartmentBean bean = null;
        try {
            bean = service.findByDeptCode(deptCode);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service while deleting department with code " + deptCode, t);
            throw se;
        }
        
        if (bean == null) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service - null department object", new IllegalArgumentException("No department with code " + deptCode + " exists !"));
            throw se;
        }
        
        try {
            service.delete(bean.getId());
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in delete service while deleting department with code " + deptCode, t);
            throw se;
        }
        
    }

    
    @Override
    @Transactional(readOnly=true)
    public List<DeptBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, SortCriteriaBean sort, Integer outputMode) throws ServiceException {
        
        List<DeptBeanPub> list2 = null;
        try {
            List<DepartmentBean> list1 = service.findText(keywords, fuzzy, sort);
            
            list2 = convert(list1, outputMode);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in doSimpleSearch service", t);
            throw se;
        }
        
        return(list2);
    }
    
    //@Override
    @Transactional(readOnly=true)
    @Path("/search")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DeptBeanPub> doSimpleSearch(@QueryParam("q") String keywords, @QueryParam("fuzzy") Boolean fuzzy, 
            @DefaultValue("") @QueryParam("asc") List<String> ascFields, 
            @DefaultValue("") @QueryParam("desc") List<String> descFields, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode) throws ServiceException {
        
        Integer mode = (outputMode == null || outputMode < 0) ? 0 : outputMode;
        
        SortCriteriaBean sort = new SortCriteriaBean();
        if (ascFields != null)
            sort.setAscOrderFields(ascFields);
        if (descFields != null)
            sort.setDescOrderFields(descFields);

        List<DeptBeanPub> list1 = doSimpleSearch(keywords, fuzzy, sort, outputMode);
        
        return(list1);
        
    }
    
    @Override
    @Transactional(readOnly=true)
    public PageResponseBean<DeptBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, Integer outputMode, PageRequestBean request) throws ServiceException {
        
        if (keywords == null || "".equals(keywords)) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in simple search paged service ", new IllegalArgumentException("No search keyword found !"));
            throw se;
        }
        
        PageRequest req = JpaUtil.convert(request);
        
        Page<DepartmentBean> resp = null;
        PageResponseBean<DeptBeanPub> response =  null;
        try {
            resp = service.findText(keywords, fuzzy, req);
            List<DepartmentBean> list1 = resp.getContent();
            List<DeptBeanPub> list2 = convert(list1, outputMode);
        
            response = JpaUtil.tranform(resp, list2);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occurred in simple search paged service", t);
            throw se;
        }

        return(response);
    }
    
    //@Override
    @Transactional(readOnly=true)
    @Path("/pagedSearch")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PageResponseBean<DeptBeanPub> doSimpleSearch(
            @QueryParam("q") String keywords, 
            @DefaultValue("true") @QueryParam("fuzzy") Boolean fuzzy, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode,
            @DefaultValue("1") @QueryParam("page") Integer pageNum, 
            @DefaultValue("10") @QueryParam("pageSize") Integer pageSize,
            @DefaultValue("") @QueryParam("asc") List<String> ascFields, 
            @DefaultValue("") @QueryParam("desc") List<String> descFields) throws ServiceException {
        
        Integer mode = (outputMode == null || outputMode < 0) ? 0 : outputMode;
        Integer pageNum2 = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        Integer pageSize2 =  (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        
        PageRequestBean request = new PageRequestBean();
        request.setPageNum(pageNum2);
        request.setPageSize(0);
        request.setOffset(pageNum2*pageSize2);
        
        SortCriteriaBean sort = new SortCriteriaBean();
        if (ascFields != null)
                sort.setAscOrderFields(ascFields);
        if (descFields != null)
            sort.setDescOrderFields(descFields);
        
        request.setSort(sort);
        
        PageResponseBean<DeptBeanPub> response = doSimpleSearch(keywords, fuzzy, mode, request);
        
        return(response);
        
    }

    @Override
    @Transactional(readOnly=true)
    public List<DeptBeanPub> doAdvancedSearch(SearchCriteriaBean search, SortCriteriaBean sort, Integer outputMode) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly=true)
    public PageResponseBean<DeptBeanPub> doAdvancedSearch(SearchCriteriaBean search, Integer outputMode, PageRequestBean request) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    @Transactional(readOnly=true)
    public PageResponseBean<DeptBeanPub> findAll(Integer outputMode, PageRequestBean request) throws ServiceException {
        
        PageRequest req = JpaUtil.convert(request);
        Page<DepartmentBean> resp = null;
        try {
            resp = service.findAll(req);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occurred in findAll paged service", t);
            throw se;
        }
        
        List<DepartmentBean> list1 = resp.getContent();
        List<DeptBeanPub> list2 = convert(list1, outputMode);
        
        PageResponseBean<DeptBeanPub> response = JpaUtil.tranform(resp, list2);
        
        return(response);
    }
    
    @Transactional(readOnly=true)
    @Path("/pagedList")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PageResponseBean<DeptBeanPub> findAll(@DefaultValue("0") @QueryParam("outputMode") Integer outputMode, 
            @DefaultValue("1") @QueryParam("page") Integer pageNum, 
            @DefaultValue("10") @QueryParam("pageSize") Integer pageSize, 
            @DefaultValue("") @QueryParam("asc") List<String> ascFields, 
            @DefaultValue("") @QueryParam("desc") List<String> descFields) throws ServiceException {
        
        Integer mode = (outputMode == null || outputMode < 0) ? 0 : outputMode;
        Integer pageNum2 = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        Integer pageSize2 =  (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        
        PageRequestBean request = new PageRequestBean();
        request.setPageNum(pageNum2);
        request.setPageSize(0);
        request.setOffset(pageNum2*pageSize2);
        
        SortCriteriaBean sort = new SortCriteriaBean();
        if (ascFields != null)
                sort.setAscOrderFields(ascFields);
        if (descFields != null)
            sort.setDescOrderFields(descFields);
        
        request.setSort(sort);
        
        PageResponseBean<DeptBeanPub> response = findAll(mode, request);
        
        return(response);
    }
    
    
    @Override
    @Transactional(readOnly=true)
    @Path("/list")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DeptBeanPub> findAll(@DefaultValue("0") @QueryParam("outputMode") Integer outputMode) throws ServiceException {
        
        ArrayList<DeptBeanPub> list2 = null;
        try {
            List<DepartmentBean> list1 = service.findAll();
            
            list2 = list1 == null ? null : new ArrayList<DeptBeanPub>();
            for (int i = 0; list1 != null && i < list1.size(); i++) {
                DepartmentBean bean1 = list1.get(i);
                DeptBeanPub bean2 = bean1.convertTo();
                list2.add(bean2);
            }
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occured in listAllDepts service", t);
            throw se;
        }
        
        return(list2);
    }
    
    @Transactional
    @Path("/test")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DeptBeanPub> testPost(List<DeptBeanPub> list) throws ServiceException {
        
        Logger logger = Logger.getLogger(getClass().getName());
        logger.log(Level.INFO, "*** Within testPost method ***");
        
        return(list);
    }
    

    @Override
    @UseAsyncMethod
    @Transactional
    @Path("/bulk")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DeptBeanPub> addAll(List<DeptBeanPub> list, 
            @DefaultValue("false") @QueryParam("ignoreError") boolean ignoreError, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode) throws ServiceException {
        
        Logger logger = Logger.getLogger(getClass().getName());
        
        logger.log(Level.INFO, "******** addAll Sync method called *********");
        
        if (list == null || list.size() < 1) {
            ServiceException se = FaultUtil.makeServiceException("Null department parameter received", new IllegalArgumentException("Null department parameter received"));
            throw se;
        }
        
        //if (ignoreError) {
        for (int i = 0; i < list.size(); i++) {
            DeptBeanPub bean = list.get(i);
            
            try {
                DepartmentBean bean2 = bean.convertTo(0);
                service.add(bean2);
                bean.setId(bean2.getId());
                bean.setRevisionControl(bean2.getRevisionControl());
            } catch (Throwable t) {
                if (ignoreError) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception encountered while adding department with code " + bean.getDeptCode(), t);
                } else {
                    ServiceException se = FaultUtil.makeServiceException("Exception occurred in addAllDept service for department code " + bean.getDeptCode(), t);
                    throw se;
                }
            }
            
        }
        //}
        
        return(list);
    }

    @Override
    @UseAsyncMethod
    @Transactional
    @Path("/bulk")
    @DELETE
    public void deleteAll(@QueryParam("ignoreError") boolean ignoreError) throws ServiceException {
        
        try {
            service.deleteAll(ignoreError);
        } catch (Throwable t) {
            ServiceException se = FaultUtil.makeServiceException("Exception occurred in deleteAllDept service", t);
            throw se;
        }
        
    }

    @Override
    @UseAsyncMethod
    @Transactional
    @Path("/bulk")
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<DeptBeanPub> updateAll(List<DeptBeanPub> list, 
            @DefaultValue("false") @QueryParam("ignoreError") boolean ignoreError, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    @Transactional
    public Future<?> addAllAsync(List<DeptBeanPub> list, boolean ignoreError, Integer outputMode, final AsyncHandler<List<DeptBeanPub>> asyncHandler) throws ServiceException {
        
        Logger logger = Logger.getLogger(getClass().getName());
        
        logger.log(Level.INFO, "***** Within Async addAll method *****");
        
        final ServerAsyncResponse<List<DeptBeanPub>> r  = new ServerAsyncResponse<>();
        
        MessageContext mContext = context.getMessageContext();
        Set<String> s = mContext.keySet();
        Iterator<String> iter  = s == null ? null : s.iterator();
        while (iter!= null && iter.hasNext()) {
            logger.log(Level.INFO, "MessageContext property - {0}", iter.next());
        }
        
        WrappedMessageContext wmc = (WrappedMessageContext) mContext;
        Message m = wmc.getWrappedMessage();
        
        Set<String> s2 = m.keySet();
        Iterator<String> iter2  = s2 == null ? null : s2.iterator();
        while (iter2 != null && iter2.hasNext()) {
            logger.log(Level.INFO, "Message property - {0}", iter2.next());
        }
        
        AddressingProperties addressProp = (AddressingProperties) mContext.get(org.apache.cxf.ws.addressing.JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_INBOUND);
 
        EndpointReferenceType eprType = addressProp.getReplyTo();
        
        return(r);
    }
    
    @Override
    public Response addAllAsync(List<DeptBeanPub> list, boolean ignoreError, Integer outputMode) throws ServiceException {
        return(null);
    }
    
    @Override
    public Future<?> updateAllAsync(List<DeptBeanPub> list, boolean ignoreError, Integer outputMode, final AsyncHandler<List<DeptBeanPub>> asyncHandler) throws ServiceException {
        return(null);
    }
    
    @Override
    public Response updateAllAsync(List<DeptBeanPub> list, boolean ignoreError, Integer outputMode) throws ServiceException {
        return(null);
    }
    
    @Override
    public Future<?> deleteAllAsync(boolean ignoreError, final AsyncHandler asyncHandler) throws ServiceException {
        return(null);
    }
    
    @Override
    public Response deleteAllAsync(boolean ignoreError) throws ServiceException {
        return(null);
    }
    
    @Override
    @Transactional(readOnly=true)
    @Path("/department/{deptId: \\d+}/employees")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<EmpBeanPub> getEmployees(@PathParam("deptId") Long id, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode) throws ServiceException {
        
        if (id == null || id <= 0) {
            ServiceException se = FaultUtil.makeServiceException("Exception in get employee service ", new IllegalArgumentException("Invalid department id - " + id));
            throw se;
        }
        
        List<EmployeeBean> list1 = service.getEmployees(id);
        ArrayList<EmpBeanPub> list2 = new ArrayList<>();
        for (int i = 0; list1 != null && i < list1.size(); i++) {
            EmployeeBean bean1 = list1.get(i);
            EmpBeanPub bean2 = bean1.convertTo();
            list2.add(bean2);
        }
        
        return(list2);
    }

    @Override
    @Transactional(readOnly=true)
    @Path("/department/{deptCode: ^[a-zA-Z]+(\\d+)*}/employees")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<EmpBeanPub> getEmployees(@PathParam("deptCode") String deptCode, 
            @DefaultValue("0") @QueryParam("outputMode") Integer outputMode) throws ServiceException {
        
        if (deptCode == null || "".equals(deptCode)) {
            ServiceException se = FaultUtil.makeServiceException("Exception in get employee service ", new IllegalArgumentException("Invalid department id - " + deptCode));
            throw se;
        }
        
        List<EmployeeBean> list1 = service.getEmployees(deptCode);
        ArrayList<EmpBeanPub> list2 = new ArrayList<>();
        for (int i = 0; list1 != null && i < list1.size(); i++) {
            EmployeeBean bean1 = list1.get(i);
            EmpBeanPub bean2 = bean1.convertTo();
            list2.add(bean2);
        }
        
        return(list2);
    }
    
    
    private List<DeptBeanPub> convert(List<DepartmentBean> list, Integer outputMode) {
        ArrayList<DeptBeanPub> list2 = new ArrayList<>();
        Iterator<DepartmentBean> iter = list == null ? null : list.iterator();
        while (iter != null && iter.hasNext()) {
            DepartmentBean bean1 = iter.next();
            DeptBeanPub bean2 = bean1.convertTo();
            list2.add(bean2);
        }
        return(list2);
    }
    
    private Integer DEFAULT_PAGE_SIZE = 10;

    
}
