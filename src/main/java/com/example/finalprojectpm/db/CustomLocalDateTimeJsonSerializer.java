package com.example.finalprojectpm.db;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {
    private static final Logger LOGGER = LogManager.getLogger(CustomLocalDateTimeJsonSerializer.class);
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider){
        try {
            jsonGenerator.writeString(formatter.format(localDateTime));
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}