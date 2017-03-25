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
import com.olp.jpa.domain.docu.ut.model.DeptBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmpBeanPub;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import org.springframework.data.repository.NoRepositoryBean;
import com.olp.service.IBulkService;
import com.olp.service.ICRUDService;
import com.olp.service.ISearchService;
import com.olp.service.ServiceException;
import java.util.concurrent.Future;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import javax.xml.ws.ResponseWrapper;
import org.apache.cxf.annotations.WSDLDocumentation;
import org.apache.cxf.annotations.WSDLDocumentationCollection;

/**
 * 
 * @author raghosh
 */
@NoRepositoryBean
@WebService(targetNamespace="http://trilia-cloud.com/webservices/entity/ut/ut-dept-service")
@WSDLDocumentationCollection(
    {
        @WSDLDocumentation(value="Dept Service Port Type", placement=WSDLDocumentation.Placement.PORT_TYPE),
        @WSDLDocumentation(value = "Dept service is a sample service, which provides introduction to the underlying "
                + "infrastructure and a default debugging mechanism. You may control the amount of data returned by the "
                + "list types by setting the following outputmodes . Value - 0 (default) : complete definition"
                + "value - 1 : with child entities but without revisioncontrol information "
                + "value - 2 : minimal information about department, preferably used as LOV",
                           placement = WSDLDocumentation.Placement.TOP),
        @WSDLDocumentation(value = "Dept service default SOAP binding",
                           placement = WSDLDocumentation.Placement.BINDING)
    }
)
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface DeptWebService extends IBulkService<DeptBeanPub> , ISearchService<DeptBeanPub> /* , ICRUDService<DeptBeanPub, Long> */ {
    
    //@Override
    @WSDLDocumentation("Gets the service version")
    @WebMethod(operationName="getServiceVersion", action="getServiceVersion")
    public @WebResult(name="serviceVersion") ServiceVersion getVersion() 
            throws ServiceException;
    
    //public SearchableFieldMetaData getSearchableFields(Integer depth) throws ServiceException;
    
    //public FieldMetaData getFieldsMetaData(Integer depth) throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds Department by Id")
    @WebMethod(operationName="findDeptById", action="findDeptById")
    public @WebResult(name="department") 
        DeptBeanPub findOne(
            @WebParam(name="deptIdReq") Long id) 
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds Department by code")
    @WebMethod(operationName="findDeptByCode", action="findDeptByCode")
    public @WebResult(name="department") 
        DeptBeanPub findByAltKey(
            @WebParam(name="deptCodeReq") String deptCode) 
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Adds a new department to the persistent store")
    @WebMethod(operationName="addDept", action="addDept")
    public @WebResult(name="department") 
        DeptBeanPub add(
                @WebParam(name="department") DeptBeanPub dept) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Updates an exiting department definition. The id and department code must match.")
    @WebMethod(operationName="updateDept", action="updateDept")
    public @WebResult(name="department") 
        DeptBeanPub update(
                @WebParam(name="department") DeptBeanPub dept) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes an exiting department by matching id. Exception is thrown if not found.")
    @WebMethod(operationName="deleteDeptById", action="deleteDeptById")
    public  
        void delete(
                @WebParam(name="deptId") Long id) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes an exiting department by matching id. Exception is thrown if not found.")
    @WebMethod(operationName="deleteDeptByCode", action="deleteDeptByCode")
    public  
        void delete(
                @WebParam(name="deptCode") String deptCode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs a simple search. See output mode definition.")
    @WebMethod(operationName="doSimpleSearch", action="doSimpleSearch")
    @ResponseWrapper(localName="departments", className="com.olp.jpa.domain.docu.ut.model.DeptContainer")
    public @WebResult(name="deptListResponse") 
        List<DeptBeanPub> doSimpleSearch(
            @WebParam(name="keywords") String keywords, 
            @WebParam(name="isFuzzy") Boolean fuzzy,  
            @WebParam(name="sort") SortCriteriaBean sort,
            @WebParam(name="outputMode") Integer outputMode) 
            throws ServiceException;
        
    //@Override
    @WSDLDocumentation("Performs a simple search. Produces paginated output. See output mode definition.")
    @WebMethod(operationName="doSimpleSearchPaged", action="doSimpleSearchPaged")
    public @WebResult(name="deptListPagedResponse") 
        PageResponseBean<DeptBeanPub> doSimpleSearch(
            @WebParam(name="keywords") String keywords, 
            @WebParam(name="isFuzzy") Boolean fuzzy, 
            @WebParam(name="outputMode") Integer outputMode, 
            @WebParam(name="pageRequest") PageRequestBean request)
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs advanced search, with filters. See output mode definition.")
    @WebMethod(operationName="doAdvancedSearch", action="doAdvancedSearch")
    @ResponseWrapper(localName="departments", className="com.olp.jpa.domain.docu.ut.model.DeptContainer")
    public @WebResult(name="deptListResponse") 
        List<DeptBeanPub> doAdvancedSearch(
                @WebParam(name="search") SearchCriteriaBean search, 
                @WebParam(name="sort") SortCriteriaBean sort,
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs advanced search, with filters. Produces paginated output. See output mode definition.")
    @WebMethod(operationName="doAdvancedSearchPaged", action="doAdvancedSearchPaged")
    public @WebResult(name="deptListPagedResponse") 
        PageResponseBean<DeptBeanPub> doAdvancedSearch(
                @WebParam(name="search") SearchCriteriaBean search,  
                @WebParam(name="outputMode") Integer outputMode, 
                @WebParam(name="pageRequest") PageRequestBean request)
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Retrieves all departments with pagination support. See output mode definition.")
    @WebMethod(operationName="listAllDeptsPaged", action="listAllDeptsPaged")
    public @WebResult(name="deptListPagedResponse") 
        PageResponseBean<DeptBeanPub> findAll(
                @WebParam(name="outputMode") Integer outputMode, 
                @WebParam(name="pageRequest") PageRequestBean request) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds all department. See outputmode definition.")
    @WebMethod(operationName="listAllDepts", action="listAllDepts")
    @ResponseWrapper(localName="departments", className="com.olp.jpa.domain.docu.ut.model.DeptContainer")
    public @WebResult(name="deptListResponse") 
        List<DeptBeanPub> findAll(
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Adds a list of departments into persistent store. The element dept-id should not be present. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not. See also outputmode definition")
    @WebMethod(operationName="addAllDepts", action="addAllDepts")
    @ResponseWrapper(localName="departments", className="com.olp.jpa.domain.docu.ut.model.DeptContainer")
    public @WebResult(name="deptListResponse") 
        List<DeptBeanPub> addAll(
                @WebParam(name="department") List<DeptBeanPub> list, 
                @WebParam(name="ignoreError") boolean ignoreError, 
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    /*
    @Override
    public  Future<?> addAllAsync(List<DeptBeanPub> list, 
                    boolean ignoreError, Integer outputMode,
                    AsyncHandler<List<DeptBeanPub>> asyncHandler) throws ServiceException;
    
    @Override
    public Response addAllAsync(List<DeptBeanPub> list, boolean ignoreError, Integer outputMode) throws ServiceException;
    */
    
    //@Override
    @WSDLDocumentation("Updates a list of departments in the persistent store. The element dept-id should be passed. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not. See also outputmode definition")
    @WebMethod(operationName="updateAllDepts", action="updateAllDepts")
    @ResponseWrapper(localName="departments", className="com.olp.jpa.domain.docu.ut.model.DeptContainer")
    public @WebResult(name="deptListResponse") 
        List<DeptBeanPub> updateAll(
                @WebParam(name="department") List<DeptBeanPub> list, 
                @WebParam(name="ignoreError") boolean ignoreError, 
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes all departments from persistent store. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not.")
    @WebMethod(operationName="deleteAllDepts", action="deleteAllDepts")
    public 
        void deleteAll(
                @WebParam(name="ignoreError") boolean ignoreError) 
                throws ServiceException;
        
    @WSDLDocumentation("Lists all employees in the department using department id")
    @WebMethod(operationName="getEmployeesByDeptId", action="getEmployeesByDeptId")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> getEmployees(
            @WebParam(name="deptIdReq") Long id,
            @WebParam(name="outputMode") Integer outputMode) 
            throws ServiceException;
    
    @WSDLDocumentation("Lists all employees in the department using department code")
    @WebMethod(operationName="getEmployeesByDeptCode", action="getEmployeesByDeptCode")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> getEmployees(
            @WebParam(name="deptCodeReq") String deptCode, 
            @WebParam(name="outputMode") Integer outputMode) 
            throws ServiceException;
    
        
    //@Override
    //@WebMethod(exclude=true)
    //public List<DeptBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, List<String> ascFields, List<String> descFields, Integer outputMode) throws ServiceException;
    
    //@Override
    //@WebMethod(exclude=true)
    //public PageResponseBean<DeptBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, Integer outputMode, Integer pageNum, Integer pageSize, 
    //        List<String> ascFields, List<String> descFields) throws ServiceException;
}
