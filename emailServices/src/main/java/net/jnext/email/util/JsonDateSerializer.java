/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jnext.email.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 *
 * @author jcernaq
 */
public class JsonDateSerializer extends JsonSerializer<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void serialize(LocalDate t, JsonGenerator jg, SerializerProvider sp) 
            throws IOException, JsonProcessingException {
        String dateString = t.format(formatter);
        jg.writeString(dateString);
    }
    
}
