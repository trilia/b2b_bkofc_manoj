/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.service;

import com.olp.jpa.domain.docu.ut.model.PersonEntityPub;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author raghosh
 */
@NoRepositoryBean
@WebService(targetNamespace="http://trilia-cloud.com/webservices/entity/ut/ut-person-service/")
public interface PersonWebService {
    
    @WebMethod(operationName="listAllPersons")
    public @WebResult(name="personsResponse") List<PersonEntityPub> findAll();
    
    @WebMethod(operationName="findPerson")
    public @WebResult(name="personResponse") PersonEntityPub findOne(@WebParam(name="personId") Long id);
    
    @WebMethod(operationName="addPerson")
    public @WebResult(name="personResponse") PersonEntityPub add(@WebParam(name="person") PersonEntityPub person);
    
}
