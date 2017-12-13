/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.enums;

/**
 *
 * @author jcernaq
 */
public enum TypeFile {
    /**
     * Tipos
     */
    CSV("csv"), PDF("pdf"), XLS("xls"), XLSX("xlsx"), DOC("doc"), DOCX("docx"), 
    JPG("jpg"), PNG("png"), LOG("log"), XML("xml"), TXT("txt"), RAR("rar"), ZIP("zip");
    
    private final String type;

    /**
     * 
     * @param type 
     */
    TypeFile(String type) {
        this.type = type;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
}
