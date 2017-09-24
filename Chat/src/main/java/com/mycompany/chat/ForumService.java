/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat;

import com.mycompany.chat.domain.Conversation;
import com.mycompany.chat.domain.Message;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kristian
 */
@Stateless
@Path("messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ForumService {   
    @PersistenceContext
    EntityManager em;
    
    @GET
    public List<Message> getMessages(@QueryParam("name") String name) {
        List<Message> result = null;
        if(name != null) {
            result = em.createQuery("SELECT m FROM Message m WHERE m.conversation.id = :id", 
                    Message.class)
                .setParameter("id", name)
                .getResultList();
        }
        
        return result != null ? result : Collections.EMPTY_LIST;
    }
    
    @GET
    @Path("conversations")
    public List<Conversation> getConversations(){
        List<Conversation> result;   
        result = em.createQuery("SELECT c FROM Conversation c", 
        Conversation.class).getResultList();
        return result != null ? result : Collections.EMPTY_LIST;
    }
    
    @POST
    @Path("new")
    public Response newConv(@QueryParam("name")String name) {
        Conversation c = new Conversation(name);
        em.persist(c);
        return Response.ok(c).build();   
    }
        
    @POST
    @Path("add")
    public Response addMessage(@QueryParam("name")String name, Message message) {
        if(name != null) {
            Conversation c = em.find(Conversation.class, name);
            if(c == null) {
                c = new Conversation(name);
                em.persist(c);
            }
            message.setConversation(c);
            em.persist(message);

            return Response.ok(message).build();
        } else {
            return Response.noContent().build();
        }
    }
}
