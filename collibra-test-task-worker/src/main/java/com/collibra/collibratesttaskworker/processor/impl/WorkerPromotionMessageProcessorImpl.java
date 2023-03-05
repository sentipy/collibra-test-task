package com.collibra.collibratesttaskworker.processor.impl;

import com.collibra.collibratesttaskworker.processor.WorkerPromotionMessageProcessor;
import com.collibra.collibratesttaskworker.service.PromotionService;
import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerPromotionMessageProcessorImpl implements WorkerPromotionMessageProcessor<AssetPromotionMessageDTO> {

    private static final Logger logger = LoggerFactory.getLogger(WorkerPromotionMessageProcessorImpl.class);

    private final PromotionService promotionService;

    public WorkerPromotionMessageProcessorImpl(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public void process(AssetPromotionMessageDTO assetPromotionMessageDTO) {
        logger.info("Received message about promotion: {}", assetPromotionMessageDTO);
        this.promotionService.promote(assetPromotionMessageDTO.assetId());
    }
}
