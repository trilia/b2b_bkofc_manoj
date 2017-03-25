/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.repo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author raghosh
 */
@Path("/restservice")
public class SomeRestService {
    
    @GET
    @Path("/hello")
    public String find() {
        return("Hello World");
    }
    
}
