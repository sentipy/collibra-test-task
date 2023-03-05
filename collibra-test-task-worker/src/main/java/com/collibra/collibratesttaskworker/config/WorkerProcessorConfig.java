package com.collibra.collibratesttaskworker.config;

import com.collibra.collibratesttaskworker.processor.WorkerPromotionMessageProcessor;
import com.collibra.collibratesttaskworker.processor.impl.WorkerPromotionMessageProcessorImpl;
import com.collibra.collibratesttaskworker.service.PromotionService;
import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerProcessorConfig {

    private final PromotionService promotionService;

    public WorkerProcessorConfig(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Bean
    public WorkerPromotionMessageProcessor<AssetPromotionMessageDTO> promotionMessageProcessor() {
        return new WorkerPromotionMessageProcessorImpl(promotionService);
    }
}
