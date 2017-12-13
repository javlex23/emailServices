/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.bean;

import net.jnext.email.enums.TypeFile;
import java.io.InputStream;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jcernaq
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Attachment implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String filename;
    private TypeFile type;
    private InputStream content;

    /**
     * Constructor por defectos
     */
    public Attachment(){
    }
    
    /**
     * Constructor
     * @param filename 
     * @param type 
     * @param content 
     */
    public Attachment(String filename, TypeFile type, InputStream content){
        this.filename = filename;
        this.type = type;
        this.content = content;
    }
    
    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the nameFile to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the type
     */
    public TypeFile getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TypeFile type) {
        this.type = type;
    }

    /**
     * @return the content
     */
    public InputStream getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(InputStream content) {
        this.content = content;
    }
    
    @Override
    public String toString(){
        return "Attachment { filename = " + filename + ", type = " + type + "}";
    }
}
