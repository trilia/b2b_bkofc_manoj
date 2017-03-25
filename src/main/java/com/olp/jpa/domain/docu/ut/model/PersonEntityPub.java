/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olp.jpa.domain.docu.ut.model;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raghosh
 */
@XmlRootElement(name="person", namespace="http://trilia-cloud.com/schema/entity/ut/ut-person/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(value=XmlAccessOrder.UNDEFINED)
public class PersonEntityPub {
    
    @XmlElement(name="personId")
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private int age;
    
    private String likes = "burger";

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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
    
    
    
    public PersonEntity convertTo() {
        
        PersonEntity entity = new PersonEntity();
        entity.setId(this.id);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setAge(this.age);
        
        return(entity);
    }
}
