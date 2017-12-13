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
public enum AttachmentCondition {
    /**
     * Valores Enum
     */
    ANY(0), TRUE(1), FALSE(2);
    
    private final int value;

    /**
     * Constructor
     * @param value 
     */
    AttachmentCondition(int value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
    
    
}
