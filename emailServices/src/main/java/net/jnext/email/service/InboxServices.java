/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import net.jnext.email.bean.MessageBean;
import net.jnext.email.dao.InboxDao;
import net.jnext.email.enums.AttachmentCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jcernaq
 */
@Path("/inbox")
public class InboxServices {
    
    private static final Logger LOGGER = LogManager.getLogger(InboxServices.class);
    
    /**
     * Servicio para obtener los ultimos n mensajes de la bandeja
     * @param countLast
     * @return lista de mensajes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listLastMessagesInbox")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public List<MessageBean> listLastMessagesInbox(@DefaultValue("0") @QueryParam("limit") int countLast){
        InboxDao dao = new InboxDao();
        return dao.listMessagesInbox(countLast);
    }
    
    /**
     * Servicio para obtener lista de mensajes de la bandeja de acuerdo a filtros
     * @param from remitente
     * @param subjectText que asunto contenga
     * @param fromDate fecha desde
     * @param toDate fecha hasta
     * @param contentText que contenido contenga
     * @return lista de mensajes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listFilterMessagesInbox")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public List<MessageBean> listFilterMessagesInbox(
            @DefaultValue("") @QueryParam("from") String from, 
            @DefaultValue("") @QueryParam("subjectText") String subjectText, 
            @QueryParam("fromDate") String fromDate, 
            @QueryParam("toDate") String toDate, 
            @DefaultValue("") @QueryParam("contextText") String contentText){
        InboxDao dao = new InboxDao();
        LocalDate pfromDate = (!"".equals(fromDate) && fromDate != null ? LocalDate.parse(fromDate) : null);
        LocalDate ptoDate = (!"".equals(toDate) && toDate != null ? LocalDate.parse(toDate) : null);
        return dao.listMessagesInbox(from, subjectText, pfromDate, ptoDate, contentText);
    }
}
