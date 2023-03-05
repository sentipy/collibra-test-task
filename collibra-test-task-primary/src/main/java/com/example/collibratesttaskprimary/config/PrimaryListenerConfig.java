package com.example.collibratesttaskprimary.config;

import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import com.example.collibratesttaskprimary.processor.PromotionMessagePrimaryProcessor;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;

@Configuration
public class PrimaryListenerConfig {

    private final PromotionMessagePrimaryProcessor<AssetPromotionMessageDTO> promotionMessagePrimaryProcessor;

    public PrimaryListenerConfig(PromotionMessagePrimaryProcessor<AssetPromotionMessageDTO> promotionMessagePrimaryProcessor) {
        this.promotionMessagePrimaryProcessor = promotionMessagePrimaryProcessor;
    }

    @PulsarListener(
            subscriptionName = "collibra-asset-promotion-primary",
            topics = "${collibra.testtask.messaging.promotionRequestTopic}",
            schemaType = SchemaType.JSON,
            ackMode = AckMode.RECORD
    )
    void listenRequest(AssetPromotionMessageDTO assetPromotionMessageDTO) throws Exception {
        this.promotionMessagePrimaryProcessor.process(assetPromotionMessageDTO);
    }

    @PulsarListener(
            subscriptionName = "collibra-asset-promotion-primary",
            topics = "collibra-asset-promotion-worker-confirm",
            ackMode = AckMode.RECORD
    )
    void listenConfirm(String message) {
        System.out.println("Message Received: " + message);
    }
}
