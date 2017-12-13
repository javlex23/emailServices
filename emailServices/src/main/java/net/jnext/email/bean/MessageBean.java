/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import net.jnext.email.util.LocalDateAdapter;

/**
 *
 * @author jcernaq
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String subject;
    private String[] from;
    private String[] recipients;
    //@JsonDeserialize(using = JsonDateDeserializer.class)
    //@JsonSerialize(using = JsonDateSerializer.class)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate sendDate;
    //@JsonDeserialize(using = JsonDateDeserializer.class)
    //@JsonSerialize(using = JsonDateSerializer.class)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate receivedDate;
    private String contentType;
    private boolean attachmentFiles;
    private List<Attachment> attachments;
    
    /**
     * Constructor por defecto
     */
    public MessageBean(){
        from = new String[0];
        recipients = new String[0];
        attachments = new ArrayList<>();
    }
    
    /**
     * Constructor
     * @param subject 
     * @param from 
     * @param recipients 
     * @param sendDate 
     * @param receivedDate 
     * @param contentType 
     * @param attachmentFiles 
     */
    public MessageBean(String subject, String[] from, String[] recipients, LocalDate sendDate,
            LocalDate receivedDate, String contentType, boolean attachmentFiles){
        this.subject = subject;
        this.from = from;
        this.recipients = recipients;
        this.sendDate = sendDate;
        this.receivedDate = receivedDate;
        this.contentType = contentType;
        this.attachmentFiles = attachmentFiles;
    }
    
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the from
     */
    public String[] getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String[] from) {
        this.from = from;
    }

    /**
     * @return the replyTo
     */
    public String[] getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the replyTo to set
     */
    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the sendDate
     */
    public LocalDate getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the receivedDate
     */
    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    /**
     * @param receivedDate the receivedDate to set
     */
    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the attachmentFiles
     */
    public boolean isAttachmentFiles() {
        return attachmentFiles;
    }

    /**
     * @param attachmentFiles the attachmentFiles to set
     */
    public void setAttachmentFiles(boolean attachmentFiles) {
        this.attachmentFiles = attachmentFiles;
    }

    /**
     * @return the attachments
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
       
    @Override
    public String toString(){
        return "Message { subject = " + subject +", from = " + Arrays.toString(from) 
                + ", replyTo = " + Arrays.toString(recipients) + ", sendDate = " + sendDate 
                + ", receivedDate = " + receivedDate + ", contentType = " + contentType
                + ", isMyme = " + (attachmentFiles ? "SI" : "NO") 
                + ", attachments = " + attachments.toString()
                + "}";
    }
}
