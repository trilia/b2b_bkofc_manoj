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
import com.olp.jpa.domain.docu.ut.model.DeptBeanPub;
import com.olp.jpa.domain.docu.ut.model.EmpBeanPub;
import com.olp.service.IBulkService;
import com.olp.service.ISearchService;
import com.olp.service.ServiceException;
import com.olp.service.ServiceVersion;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.ResponseWrapper;
import org.apache.cxf.annotations.WSDLDocumentation;
import org.apache.cxf.annotations.WSDLDocumentationCollection;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
@WebService(targetNamespace="http://trilia-cloud.com/webservices/entity/ut/ut-emp-service")
@WSDLDocumentationCollection(
    {
        @WSDLDocumentation(value="Emp Service Port Type", placement=WSDLDocumentation.Placement.PORT_TYPE),
        @WSDLDocumentation(value = "Emp service is a sample service, which provides introduction to the underlying "
                + "infrastructure and a default debugging mechanism. You may control the amount of data returned by the "
                + "list types by setting the following outputmodes . Value - 0 (default) : complete definition"
                + "value - 1 : with child entities but without revisioncontrol information "
                + "value - 2 : minimal information about department, preferably used as LOV",
                           placement = WSDLDocumentation.Placement.TOP),
        @WSDLDocumentation(value = "Emp service default SOAP binding",
                           placement = WSDLDocumentation.Placement.BINDING)
    }
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EmpWebService extends IBulkService<EmpBeanPub> , ISearchService<EmpBeanPub> /* , ICRUDService<EmpBeanPub, Long> */{
    	
    //@Override
    @WSDLDocumentation("Gets the service version")
    @WebMethod(operationName="getServiceVersion", action="getServiceVersion")
    public @WebResult(name="serviceVersion") ServiceVersion getVersion() 
            throws ServiceException;
    
    //public SearchableFieldMetaData getSearchableFields(Integer emph) throws ServiceException;
    
    //public FieldMetaData getFieldsMetaData(Integer emph) throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds Department by Id")
    @WebMethod(operationName="findEmpById", action="findEmpById")
    public @WebResult(name="employee") 
        EmpBeanPub findOne(
            @WebParam(name="empIdReq") Long id) 
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds Department by code")
    @WebMethod(operationName="findEmpByCode", action="findEmpByCode")
    public @WebResult(name="employee") 
        EmpBeanPub findByAltKey(
            @WebParam(name="empCodeReq") String empCode) 
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Adds a new employee to the persistent store")
    @WebMethod(operationName="addEmp", action="addEmp")
    public @WebResult(name="employee") 
        EmpBeanPub add(
                @WebParam(name="employee") EmpBeanPub emp) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Updates basic information of an exiting employee definition.  The id and employee code must match. Use changeManager or changeDept for updating the manager & department respectively")
    @WebMethod(operationName="updateEmp", action="updateEmp")
    public @WebResult(name="employee") 
        EmpBeanPub update(
                @WebParam(name="employee") EmpBeanPub emp) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes an exiting employee by matching id. Exception is thrown if not found.")
    @WebMethod(operationName="deleteEmpById", action="deleteEmpById")
    public  
        void delete(
                @WebParam(name="empId") Long id) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes an exiting employee by matching id. Exception is thrown if not found.")
    @WebMethod(operationName="deleteEmpByCode", action="deleteEmpByCode")
    public  
        void delete(
                @WebParam(name="empCode") String empCode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs a simple search. See output mode definition.")
    @WebMethod(operationName="doSimpleSearch", action="doSimpleSearch")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> doSimpleSearch(
            @WebParam(name="keywords") String keywords, 
            @WebParam(name="isFuzzy") Boolean fuzzy,  
            @WebParam(name="sort") SortCriteriaBean sort,
            @WebParam(name="outputMode") Integer outputMode) 
            throws ServiceException;
        
    //@Override
    @WSDLDocumentation("Performs a simple search. Produces paginated output. See output mode definition.")
    @WebMethod(operationName="doSimpleSearchPaged", action="doSimpleSearchPaged")
    public @WebResult(name="empListPagedResponse") 
        PageResponseBean<EmpBeanPub> doSimpleSearch(
            @WebParam(name="keywords") String keywords, 
            @WebParam(name="isFuzzy") Boolean fuzzy, 
            @WebParam(name="outputMode") Integer outputMode, 
            @WebParam(name="pageRequest") PageRequestBean request)
            throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs advanced search, with filters. See output mode definition.")
    @WebMethod(operationName="doAdvancedSearch", action="doAdvancedSearch")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> doAdvancedSearch(
                @WebParam(name="search") SearchCriteriaBean search, 
                @WebParam(name="sort") SortCriteriaBean sort,
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Performs advanced search, with filters. Produces paginated output. See output mode definition.")
    @WebMethod(operationName="doAdvancedSearchPaged", action="doAdvancedSearchPaged")
    public @WebResult(name="empListPagedResponse") 
        PageResponseBean<EmpBeanPub> doAdvancedSearch(
                @WebParam(name="search") SearchCriteriaBean search,  
                @WebParam(name="outputMode") Integer outputMode, 
                @WebParam(name="pageRequest") PageRequestBean request)
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Retrieves all employees with pagination support. See output mode definition.")
    @WebMethod(operationName="listAllEmpsPaged", action="listAllEmpsPaged")
    public @WebResult(name="empListPagedResponse") 
        PageResponseBean<EmpBeanPub> findAll(
                @WebParam(name="outputMode") Integer outputMode, 
                @WebParam(name="pageRequest") PageRequestBean request) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Finds all employee. See outputmode definition.")
    @WebMethod(operationName="listAllEmps", action="listAllEmps")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> findAll(
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Adds a list of employees into persistent store. The element emp-id should not be present. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not. See also outputmode definition")
    @WebMethod(operationName="addAllEmps", action="addAllEmps")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> addAll(
                @WebParam(name="employee") List<EmpBeanPub> list, 
                @WebParam(name="ignoreError") boolean ignoreError, 
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    /*
    @Override
    public  Future<?> addAllAsync(List<EmpBeanPub> list, 
                    boolean ignoreError, Integer outputMode,
                    AsyncHandler<List<EmpBeanPub>> asyncHandler) throws ServiceException;
    
    @Override
    public Response addAllAsync(List<EmpBeanPub> list, boolean ignoreError, Integer outputMode) throws ServiceException;
    */
    
    //@Override
    @WSDLDocumentation("Updates a list of employees in the persistent store. The element emp-id should be passed. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not. See also outputmode definition")
    @WebMethod(operationName="updateAllEmps", action="updateAllEmps")
    @ResponseWrapper(localName="employees", className="com.olp.jpa.domain.docu.ut.model.EmpContainer")
    public @WebResult(name="empListResponse") 
        List<EmpBeanPub> updateAll(
                @WebParam(name="employee") List<EmpBeanPub> list, 
                @WebParam(name="ignoreError") boolean ignoreError, 
                @WebParam(name="outputMode") Integer outputMode) 
                throws ServiceException;
    
    //@Override
    @WSDLDocumentation("Deletes all employees from persistent store. "
            + "This method can be called asynchronously by callback or polling method. "
            + "The ignoreError parameter indicates whether to ignore errors with individual list elements or not.")
    @WebMethod(operationName="deleteAllEmps", action="deleteAllEmps")
    public 
        void deleteAll(
                @WebParam(name="ignoreError") boolean ignoreError) 
                throws ServiceException;
    
    
    
        
    //@Override
    //@WebMethod(exclude=true)
    //public List<EmpBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, List<String> ascFields, List<String> descFields, Integer outputMode) throws ServiceException;
    
    //@Override
    //@WebMethod(exclude=true)
    //public PageResponseBean<EmpBeanPub> doSimpleSearch(String keywords, Boolean fuzzy, Integer outputMode, Integer pageNum, Integer pageSize, 
    //        List<String> ascFields, List<String> descFields) throws ServiceException;
	
    
}
