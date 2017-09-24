/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Kristian
 */
@Data @NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Message implements Serializable{
    @Id @GeneratedValue
    Long id;
    
    @Column(name = "userid")String user;
    String text;
    
    @Version
    Timestamp version;
    
    @XmlTransient
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)Conversation conversation;
    
    public Message(String user, String text){
        this.user = user;
        this.text = text;
    }
    
}
