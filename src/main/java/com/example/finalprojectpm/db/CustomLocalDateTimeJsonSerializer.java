package com.example.finalprojectpm.db;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides custom serialization to LocalDateTime class
 */
public class CustomLocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
    private static final Logger LOGGER = LogManager.getLogger(CustomLocalDateTimeJsonSerializer.class);
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Serialize LocalDateTime into formatted way
     * @param localDateTime LocalDateTime object that should be formatted
     * @param jsonGenerator Object for creating Json field
     * @param serializerProvider for handling cached aspects of serializer
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider){
        try {
            jsonGenerator.writeString(formatter.format(localDateTime));
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}