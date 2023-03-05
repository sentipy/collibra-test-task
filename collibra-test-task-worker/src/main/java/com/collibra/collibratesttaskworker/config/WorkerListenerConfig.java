package com.collibra.collibratesttaskworker.config;

import com.collibra.collibratesttaskworker.processor.WorkerPromotionMessageProcessor;
import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;

@Configuration
public class WorkerListenerConfig {

    private final WorkerPromotionMessageProcessor<AssetPromotionMessageDTO> workerPromotionMessageProcessor;

    public WorkerListenerConfig(WorkerPromotionMessageProcessor<AssetPromotionMessageDTO> workerPromotionMessageProcessor) {
        this.workerPromotionMessageProcessor = workerPromotionMessageProcessor;
    }

    @PulsarListener(
            subscriptionName = "collibra-asset-promotion-worker",
            topics = "${collibra.testtask.messaging.promotionWorkTopic}",
            subscriptionType = SubscriptionType.Shared,
            schemaType = SchemaType.JSON,
            ackMode = AckMode.RECORD
    )
    void listen(AssetPromotionMessageDTO message) {
        this.workerPromotionMessageProcessor.process(message);
    }
}
