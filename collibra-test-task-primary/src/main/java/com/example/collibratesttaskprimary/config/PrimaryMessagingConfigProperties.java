package com.example.collibratesttaskprimary.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("collibra.testtask.messaging")
public class PrimaryMessagingConfigProperties {

    private final String promotionRequestTopic;
    private final String promotionWorkTopic;

    public PrimaryMessagingConfigProperties(
            String promotionRequestTopic,
            String promotionWorkTopic
    ) {
        this.promotionRequestTopic = promotionRequestTopic;
        this.promotionWorkTopic = promotionWorkTopic;
    }

    public String getPromotionRequestTopic() {
        return promotionRequestTopic;
    }

    public String getPromotionWorkTopic() {
        return promotionWorkTopic;
    }
}
