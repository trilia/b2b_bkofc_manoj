/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.service;

import com.olp.jpa.domain.docu.ut.model.PersonEntityPub;
import com.olp.jpa.domain.docu.ut.model.PersonEntity;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author raghosh
 */
@Component("personWebService")
@WebService(serviceName="PersonService", endpointInterface="com.olp.jpa.domain.docu.ut.service.PersonWebService", targetNamespace="http://trilia-cloud.com/webservices/entity/ut/ut-person-service/")
@Path("/persons")
public class PersonWebServiceImpl implements PersonWebService {
    
    
    
    @Override
    @Transactional(readOnly=true)
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PersonEntityPub> findAll() {
        
        //List<PersonEntity> list1  = service.findAll();
        //ArrayList<PersonEntityPub> list2 = list1 == null ? null : new ArrayList<PersonEntityPub>();
        
        //for (int i = 0; list1 != null && i < list1.size(); i++) {
        //    PersonEntity bean1 = list1.get(i);
        //    PersonEntityPub bean2 = bean1.convertTo();
        //    list2.add(bean2);
        //}
        
        //return(list2);
        
        ArrayList<PersonEntityPub> list2 = new ArrayList<>();
        list2.add(makePerson());
        
        return(list2);
    }

    @Override
    @Transactional(readOnly=true)
    @Path("/person/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PersonEntityPub findOne(@PathParam("id") Long id) {
        
        //PersonEntity bean1 = service.findOne(id);
        
        //PersonEntityPub bean2 = bean1.convertTo();
        
        //return(bean2);
        
        PersonEntityPub bean2 = makePerson();
        
        return(bean2);
    }

    @Override
    @Transactional
    @Path("/person")
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public PersonEntityPub add(PersonEntityPub person) {
        
        //PersonEntity bean = person.convertTo();
        
        //service.add(bean);
        
        //PersonEntityPub bean2 = bean.convertTo();
        
        //return(bean2);
        
        return(person);
    }
    
    private PersonEntityPub makePerson() {
        
        PersonEntity per = new PersonEntity();
        per.setId(1001L);
        per.setFirstName("Some");
        per.setLastName("Guy");
        per.setAge(50);
        
        PersonEntityPub entity = per.convertTo();
        
        return(entity);
    }
    
}
