/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.util;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jcernaq
 */
public final class EmailConfig {
    private static final Logger LOGGER = LogManager.getLogger(EmailConfig.class);
    
    private static Store storeFactory;
    
    /**
     * Constructor
     */
    protected EmailConfig(){
        throw new UnsupportedOperationException();
    }
    
    private static Store connect(){
        ResourceBundle resource = ResourceBundle.getBundle("application");
        
        String host = resource.getString("inbox.host");
        String username = resource.getString("inbox.username");
        String password = resource.getString("inbox.password");
        String provider = resource.getString("inbox.provider");
        
        Session session = Session.getDefaultInstance(new Properties(), null);
        session.setDebug(true);
        
        Store store = null;
        try {
            store = session.getStore(provider);
            store.connect(host, username, password);
        } catch (NoSuchProviderException ex) {
            LOGGER.error("Revisar propiedad inbox.provider del archivo application.properties: " + ex.getMessage());
        } catch (MessagingException ex) {
            LOGGER.error("Error de conexión: " + ex.getMessage());
        }
        
        return store;
    }
    
    /**
     * Obtiene la referencia al Store para conectarse al inbox
     * @return referencia a Store
     */
    public static Store getStoreSessionFactory(){
        synchronized(EmailConfig.class){
            if(storeFactory == null){
                storeFactory = connect();
            }
        }
        return storeFactory;
    }
}
