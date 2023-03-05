package com.example.collibratesttaskprimary.config;

import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import com.example.collibratesttaskprimary.message.MessageRepository;
import com.example.collibratesttaskprimary.processor.PromotionMessagePrimaryProcessor;
import com.example.collibratesttaskprimary.processor.impl.PromotionMessagePrimaryProcessorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarTemplate;

@Configuration
public class PrimaryProcessorConfig {

    private final ObjectMapper objectMapper;
    private final MessageRepository<Long, Object> messageRepository;
    private final PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate;
    private final PrimaryMessagingConfigProperties primaryMessagingConfigProperties;

    public PrimaryProcessorConfig(
            ObjectMapper objectMapper,
            MessageRepository<Long, Object> messageRepository,
            PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate,
            PrimaryMessagingConfigProperties primaryMessagingConfigProperties
    ) {
        this.objectMapper = objectMapper;
        this.messageRepository = messageRepository;
        this.pulsarTemplate = pulsarTemplate;
        this.primaryMessagingConfigProperties = primaryMessagingConfigProperties;
    }

    @Bean
    public PromotionMessagePrimaryProcessor<AssetPromotionMessageDTO> promotionMessageProcessor() {
        return new PromotionMessagePrimaryProcessorImpl(messageRepository, pulsarTemplate, primaryMessagingConfigProperties);
    }
}
