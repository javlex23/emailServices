/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.dao;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import net.jnext.email.bean.Attachment;
import net.jnext.email.bean.MessageBean;
import net.jnext.email.enums.AttachmentCondition;
import net.jnext.email.enums.TypeFile;
import net.jnext.email.util.EmailConfig;
import net.jnext.email.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jcernaq
 */
public class InboxDao {
    private static final Logger LOGGER = LogManager.getLogger(InboxDao.class);
    
    private static List<MessageBean> process(Message[] messages) throws MessagingException, IOException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<MessageBean> listInbox = new ArrayList<>();
        for (Message message : messages) {
                MessageBean bean = new MessageBean();
                bean.setSubject(message.getSubject());
                
                bean.setFrom(Util.extractEmail(message.getFrom()));
                Date sendDate = message.getSentDate();
                bean.setSendDate(LocalDate.parse(df.format(sendDate)));
                Date receivedDate = message.getReceivedDate();
                if(receivedDate != null) { 
                    bean.setReceivedDate(LocalDate.parse(df.format(receivedDate)));
                }
                bean.setRecipients(Util.extractEmail(message.getAllRecipients()));
                bean.setContentType(message.getContentType());
                bean.setAttachmentFiles(message.isMimeType("multipart/*"));
                
                List<Attachment> attachments = new ArrayList<>();
                
                if(message.isMimeType("multipart/MIXED")){
                    Multipart parts = (Multipart) message.getContent();
                    
                    for(int j = 0; j < parts.getCount(); j++){
                        BodyPart body = parts.getBodyPart(j);
                        LOGGER.debug("Tipo: " + body.getContentType());
                        if(body.isMimeType("APPLICATION/OCTET-STREAM")){
                            if(Util.getFileExtension(new File(body.getFileName()))
                                    .equalsIgnoreCase("csv")){
                                Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.CSV, body.getInputStream());
                                attachments.add(attach);
                            }
                            if(Util.getFileExtension(new File(body.getFileName()))
                                    .equalsIgnoreCase("log")){
                                Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.LOG, body.getInputStream());
                                attachments.add(attach);
                            }
                        }
                        if(body.isMimeType("TEXT/PLAIN")){
                            Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.TXT, body.getInputStream());
                                attachments.add(attach);
                        }
                        if(body.isMimeType("TEXT/XML")){
                            Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.XML, body.getInputStream());
                                attachments.add(attach);
                        }
                        if(body.isMimeType("APPLICATION/VND.OPENXMLFORMATS-OFFICEDOCUMENT.WORDPROCESSINGML.DOCUMENT")){
                            Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.DOCX, body.getInputStream());
                                attachments.add(attach);
                        }
                        if(body.isMimeType("IMAGE/PNG")){
                            Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.PNG, body.getInputStream());
                                attachments.add(attach);
                        }
                        if(body.isMimeType("IMAGE/JPEG")){
                            Attachment attach = new Attachment(body.getFileName(), 
                                TypeFile.JPG, body.getInputStream());
                                attachments.add(attach);
                        }
                    }
                }
                
                bean.setAttachments(attachments);
                LOGGER.debug("---------------------------------");
                LOGGER.debug("[Mensaje] " + bean);
                listInbox.add(bean);
            }
        return listInbox;
    }
    
    /**
     * Método para obtener los últimos n mensajes de la bandeja
     * @param countLast cantidad de mensajes obtenidos desde el último
     * @return lista de mensajes
     */
    public List<MessageBean> listMessagesInbox(int countLast){
        List<MessageBean> listaInbox = new ArrayList<>();
        Store store = EmailConfig.getStoreSessionFactory();
        try {
            Folder inbox = store.getFolder("Inbox");
            if (inbox == null) {
                LOGGER.debug("No INBOX");
                return null;
            }
            inbox.open(Folder.READ_ONLY);
            int countMessages = inbox.getMessageCount();
            listaInbox = process(inbox.getMessages(countMessages - countLast, countMessages));
        } catch (MessagingException ex) {
            LOGGER.error("Error en recuperar mensaje: " + ex.getMessage());
        } catch (IOException ex) {
            LOGGER.error("Error al recuperar archivo: " + ex.getMessage());
        }
        return listaInbox;
    }
    
    /**
     * Método para obtener mensajes de bandeja de acuerdo a filtros
     * @param from remitente
     * @param subjectText que asunto contenga texto
     * @param fromDate fecha desde
     * @param toDate fecha hasta
     * @param contentText que contenido contenga texto
     * @return lista de mensajes
     */
    public List<MessageBean> listMessagesInbox(String from, String subjectText, LocalDate fromDate, 
            LocalDate toDate, String contentText){
        List<MessageBean> listaInbox = new ArrayList<>();
        Store store = EmailConfig.getStoreSessionFactory();
        try {
            Folder inbox = store.getFolder("Inbox");
            if (inbox == null) {
                LOGGER.debug("No INBOX");
                return null;
            }
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm searchSubject = new SubjectTerm(subjectText);
            //SearchTerm searchFrom = new FromStringTerm(from);
            SearchTerm searchContent = new BodyTerm(contentText);
            List<SearchTerm> listSearch = new ArrayList<>();
            if("".equals(subjectText)) {
                listSearch.add(searchSubject);
            }
            if("".equals(contentText)) {
                listSearch.add(searchContent);
            }
            SearchTerm[] searchArray = new SearchTerm[listSearch.size()];
            SearchTerm searchTerm = new AndTerm(listSearch.toArray(searchArray));
            
            //Message[] messages = inbox.getMessages();
            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //int countMessages = inbox.getMessageCount();
            listaInbox = process(inbox.search(searchTerm));
        } catch (MessagingException ex) {
            LOGGER.error("Error en recuperar mensaje: " + ex.getMessage());
        } catch (IOException ex) {
            LOGGER.error("Error al recuperar archivo: " + ex.getMessage());
        }
        return listaInbox;
    }
}
