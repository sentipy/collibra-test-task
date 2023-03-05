package com.example.collibratesttaskprimary.processor.impl;

import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import com.example.collibratesttaskprimary.config.PrimaryMessagingConfigProperties;
import com.example.collibratesttaskprimary.message.MessageRepository;
import com.example.collibratesttaskprimary.processor.PromotionMessagePrimaryProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.core.PulsarTemplate;

public class PromotionMessagePrimaryProcessorImpl implements PromotionMessagePrimaryProcessor<AssetPromotionMessageDTO> {

    private static final Logger logger = LoggerFactory.getLogger(PromotionMessagePrimaryProcessorImpl.class);

    private final MessageRepository<Long, Object> messageRepository;
    private final PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate;
    private final PrimaryMessagingConfigProperties primaryMessagingConfigProperties;

    public PromotionMessagePrimaryProcessorImpl(
            MessageRepository<Long, Object> messageRepository,
            PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate,
            PrimaryMessagingConfigProperties primaryMessagingConfigProperties
    ) {
        this.messageRepository = messageRepository;
        this.pulsarTemplate = pulsarTemplate;
        this.pulsarTemplate.setSchema(Schema.JSON(AssetPromotionMessageDTO.class));
        this.primaryMessagingConfigProperties = primaryMessagingConfigProperties;
    }

    @Override
    public void process(AssetPromotionMessageDTO message) throws Exception {
        logger.info("Received promotion message: {}", message);
        long assetId = message.assetId();
        try {
            //this.messageRepository.addMessage(assetId, new Object());
            String promotionWorkTopic = this.primaryMessagingConfigProperties.getPromotionWorkTopic();
            this.pulsarTemplate.send(promotionWorkTopic, message);
            logger.info("Sent asset with id {} for promotion to topic {}", assetId, promotionWorkTopic);
        }
        catch (Exception ex) {
            logger.error("Failed to send to worker asset with id = {}", assetId, ex);
            throw ex;
        }
    }
}
