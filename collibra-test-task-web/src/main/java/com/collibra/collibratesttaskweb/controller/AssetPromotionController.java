package com.collibra.collibratesttaskweb.controller;

import com.collibra.collibratesttaskweb.config.WebMessagingConfigProperties;
import com.collibra.common.dto.messaging.AssetPromotionMessageDTO;
import com.collibra.common.dto.web.request.AssetPromotionWebRequestDTO;
import com.collibra.common.dto.web.response.AssetPromotionWebResponseDTO;
import com.collibra.common.dto.web.response.AssetPromotionWebResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping(Constants.ASSET_PROMOTION_API)
@RestController
public class AssetPromotionController {

    private static final Logger logger = LoggerFactory.getLogger(AssetPromotionController.class);

    private final PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate;
    private final WebMessagingConfigProperties webMessagingConfigProperties;

    public AssetPromotionController(
            PulsarTemplate<AssetPromotionMessageDTO> pulsarTemplate,
            WebMessagingConfigProperties webMessagingConfigProperties,
            ObjectMapper objectMapper
    ) {
        this.pulsarTemplate = pulsarTemplate;
        this.pulsarTemplate.setSchema(Schema.JSON(AssetPromotionMessageDTO.class));
        this.webMessagingConfigProperties = webMessagingConfigProperties;
    }

    @PostMapping
    public Mono<ResponseEntity<AssetPromotionWebResponseDTO>> promote(@RequestBody AssetPromotionWebRequestDTO assetPromotionWebRequestDTO) {
        long assetId = assetPromotionWebRequestDTO.getAssetId();
        logger.info("Received asset with id {} for promotion", assetId);
        AssetPromotionMessageDTO assetPromotionMessageDTO = new AssetPromotionMessageDTO(assetId);
        try {
            //String json = this.objectMapper.writeValueAsString(assetPromotionMessageDTO);
            String promotionRequestTopic = this.webMessagingConfigProperties.getPromotionRequestTopic();
            this.pulsarTemplate.send(promotionRequestTopic, assetPromotionMessageDTO);
            logger.info("Sent asset with id {} for promotion to topic {}", assetId, promotionRequestTopic);
            AssetPromotionWebResponseDTO body = new AssetPromotionWebResponseDTO(
                    assetId,
                    AssetPromotionWebResponseStatus.SUBMITTED,
                    null
            );
            return Mono.just(ResponseEntity.ok(body));
        } catch (PulsarClientException /*| JsonProcessingException*/ e) {
            logger.error("Failed to promote entity", e);
            AssetPromotionWebResponseDTO body = new AssetPromotionWebResponseDTO(
                    assetId,
                    AssetPromotionWebResponseStatus.FAILED_TO_SUBMIT,
                    e.getMessage()
            );
            return Mono.just(ResponseEntity.internalServerError().body(body));
        }

    }
}
