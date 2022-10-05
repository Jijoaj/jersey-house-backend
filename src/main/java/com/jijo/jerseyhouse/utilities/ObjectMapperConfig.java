package com.jijo.jerseyhouse.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    /**
     * Bean for object Mapper
     * @return objectMapper
     */
    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
