/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author raghosh
 */
//@Entity
//@Table(name="ut_person")
public class PersonEntity implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name="person_id")
    private Long id;
    
    //@Column(name="first_name")
    private String firstName;
    
    //@Column(name="last_name")
    private String lastName;
    
    //@Column(name="age")
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public PersonEntityPub convertTo() {
        PersonEntityPub entity = new PersonEntityPub();
        entity.setId(this.id);
        entity.setLastName(this.lastName);
        entity.setFirstName(this.firstName);
        entity.setAge(this.age);
        
        return(entity);
    }
}
