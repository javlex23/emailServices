/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.mail.Address;

/**
 *
 * @author jcernaq
 */
public final class Util {
    
    /**
     * Constructor
     */
    protected Util(){
        throw new UnsupportedOperationException();
    }
    
    /**
     * Método para extraer las direcciones de un arreglo de Address
     * @param addresses  
     * @return String[]
     */
    public static String[] extractEmail(Address[] addresses){
        String from = Arrays.toString(addresses);
        String[] fromArray = from.split(",").length > 0 ? from.split(",")
                : new String[]{from.replace("[", "").replace("]", "")};
        String[] newFrom = new String[fromArray.length];
        List<String> mails = new ArrayList<>();
        for(int k =0; k < fromArray.length; k++){
            newFrom[k] = fromArray[k].contains("<") 
                    ? fromArray[k].substring(fromArray[k].indexOf("<") + 1, fromArray[k].indexOf(">"))
                    : fromArray[k].replace("[", "").replace("]", "");
            if(newFrom[k].contains("@"))mails.add(newFrom[k]);
        }
        return mails.toArray(new String[mails.size()]);
    }
    
    /**
     * Método para obtener la extensión de un archivo
     * @param file 
     * @return extension
     */
    public static String getFileExtension(File file){
        String filename = file.getName();
        if(filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0){
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }
}
