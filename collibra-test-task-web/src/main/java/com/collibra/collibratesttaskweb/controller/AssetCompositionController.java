package com.collibra.collibratesttaskweb.controller;

import com.collibra.collibratesttaskweb.entity.AssetCompositionEntity;
import com.collibra.collibratesttaskweb.repository.AssetCompositionRepository;
import com.collibra.common.dto.web.request.AssetCompositionCreateRequestDTO;
import com.collibra.common.dto.web.response.AssetCompositionCreateResponseDTO;
import com.collibra.common.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping(Constants.ASSET_COMPOSITION_API)
@RestController
public class AssetCompositionController {

    private static final Logger logger = LoggerFactory.getLogger(AssetCompositionController.class);

    private static final AssetCompositionCreateResponseDTO EMPTY_RESPONSE = new AssetCompositionCreateResponseDTO(null);
    private final AssetCompositionRepository assetCompositionRepository;

    public AssetCompositionController(AssetCompositionRepository assetCompositionRepository) {
        this.assetCompositionRepository = assetCompositionRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<AssetCompositionCreateResponseDTO>> create(@RequestBody AssetCompositionCreateRequestDTO assetCompositionCreateRequestDTO) {

        return this.assetCompositionRepository.save(
                        new AssetCompositionEntity(
                                null,
                                assetCompositionCreateRequestDTO.parentAsset(),
                                assetCompositionCreateRequestDTO.childAsset(),
                                TimeUtils.getUTCLocalDateTimeNow()
                        )
                )
                .map(assetCompositionEntity -> ResponseEntity.ok(EMPTY_RESPONSE))
                .onErrorResume(
                        throwable -> {
                            logger.error("Failed to create composition", throwable);
                            return Mono.just(ResponseEntity
                                    .internalServerError()
                                    .body(
                                            new AssetCompositionCreateResponseDTO(
                                                    "Failed to create composition"
                                            )
                                    )
                            );
                        }
                );
    }
}
