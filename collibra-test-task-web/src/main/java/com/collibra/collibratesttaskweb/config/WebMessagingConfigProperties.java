package com.collibra.collibratesttaskweb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("collibra.testtask.messaging")
public class WebMessagingConfigProperties {

    private final String promotionRequestTopic;

    public WebMessagingConfigProperties(String promotionRequestTopic) {
        this.promotionRequestTopic = promotionRequestTopic;
    }

    public String getPromotionRequestTopic() {
        return promotionRequestTopic;
    }
}
