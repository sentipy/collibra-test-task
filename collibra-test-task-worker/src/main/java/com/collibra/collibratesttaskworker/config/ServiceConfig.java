package com.collibra.collibratesttaskworker.config;

import com.collibra.collibratesttaskworker.service.PromotionService;
import com.collibra.collibratesttaskworker.service.impl.PromotionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Configuration
public class ServiceConfig {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public ServiceConfig(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Bean
    public PromotionService promotionService() {
        return new PromotionServiceImpl(this.namedParameterJdbcOperations);
    }
}
