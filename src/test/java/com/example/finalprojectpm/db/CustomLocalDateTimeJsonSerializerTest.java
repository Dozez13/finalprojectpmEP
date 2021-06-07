package com.example.finalprojectpm.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class CustomLocalDateTimeJsonSerializerTest {
    private static ObjectMapper mapper;
    @BeforeAll
    static void init(){
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class,new CustomLocalDateTimeJsonSerializer());
        mapper.registerModule(module);
    }
    @Test
    void serialize() throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.of(2010,6,15,2,34,55);
        assertEquals("\"2010-06-15 02:34\"",mapper.writeValueAsString(localDateTime));
    }

}