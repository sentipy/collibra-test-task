package com.collibra.collibratesttaskweb.controller;

import com.collibra.collibratesttaskweb.entity.AssetEntity;
import com.collibra.collibratesttaskweb.repository.AssetRepository;
import com.collibra.common.dto.web.request.AssetCreationRequestDTO;
import com.collibra.common.dto.web.request.AssetUpdateRequestDTO;
import com.collibra.common.dto.web.response.*;
import com.collibra.common.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@RequestMapping(Constants.ASSET_API)
@RestController
public class AssetController {

    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private static final EntityToDTOConverter ENTITY_TO_DTO_CONVERTER = new EntityToDTOConverter();

    private final AssetRepository assetRepository;

    public AssetController(
            AssetRepository assetRepository
    ) {
        this.assetRepository = assetRepository;
    }

    @GetMapping("/{assetId}")
    public Mono<ResponseEntity<AssetResponseDTO>> getAsset(@PathVariable Long assetId) {
        return this.assetRepository.
                findById(assetId)
                .map(ENTITY_TO_DTO_CONVERTER)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(
                        ResponseEntity.badRequest().body(
                                new AssetResponseDTO(
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        "Asset not found"
                                )
                        )
                );
    }

    @PostMapping
    public Mono<ResponseEntity<AssetCreationResponseDTO>> create(@RequestBody AssetCreationRequestDTO assetCreationRequestDTO) {
        logger.info("Got request for asset creation");
        String assetDescription = assetCreationRequestDTO.assetDescription();
        if (StringUtils.isEmpty(assetDescription)) {
            AssetCreationResponseDTO body = new AssetCreationResponseDTO(
                    null,
                    AssetCreationResponseStatus.ERROR,
                    "Asset description must not be null"
            );
            return Mono.just(ResponseEntity.badRequest().body(body));
        }
        try {
            Mono<AssetEntity> monoAssetEntity = this.assetRepository.save(
                    new AssetEntity(
                            null,
                            assetDescription,
                            "INIT",
                            1,
                            TimeUtils.getUTCLocalDateTimeNow()
                    )
            );
            return monoAssetEntity.map(
                    assetEntity -> {
                        logger.info("Created asset with id {}", assetEntity.id());
                        AssetCreationResponseDTO body = new AssetCreationResponseDTO(
                                assetEntity.id(),
                                AssetCreationResponseStatus.SUCCESS,
                                null
                        );
                        return ResponseEntity.ok(body);
                    }
            );
        } catch (Exception ex) {
            logger.error("Failed to create asset", ex);
            AssetCreationResponseDTO body = new AssetCreationResponseDTO(
                    null,
                    AssetCreationResponseStatus.ERROR,
                    ex.getMessage()
            );
            return Mono.just(ResponseEntity.internalServerError().body(body));
        }
    }

    @PutMapping
    public Mono<ResponseEntity<AssetResponseDTO>> updateAsset(@RequestBody AssetUpdateRequestDTO assetUpdateRequestDTO) {
        //TODO check that assetId exists first
        Mono<AssetEntity> assetEntityMono = this.assetRepository.findById(assetUpdateRequestDTO.id());
        return assetEntityMono
                .map(
                        assetEntity -> new AssetEntity(
                                assetEntity.id(),
                                assetUpdateRequestDTO.description(),
                                assetEntity.assetStatusCd(),
                                assetEntity.assetVersion() + 1,
                                TimeUtils.getUTCLocalDateTimeNow()
                        )
                )
                .flatMap(assetRepository::save)
                .map(ENTITY_TO_DTO_CONVERTER)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{assetId}")
    public Mono<ResponseEntity<AssetDeletionResponseDTO>> deleteAsset(@PathVariable Long assetId) {
        //TODO check that assetId exists first
        return this.assetRepository.deleteById(assetId).map(
                assetEntity -> ResponseEntity.ok(
                        new AssetDeletionResponseDTO(
                                assetId,
                                AssetDeletionResponseStatus.SUCCESS,
                                null
                        )
                )
        ).onErrorResume(
                throwable -> Mono.just(ResponseEntity.internalServerError()
                        .body(new AssetDeletionResponseDTO(
                                        assetId,
                                        AssetDeletionResponseStatus.ERROR,
                                        throwable.getMessage()
                                )
                        )
                )
        );
    }

    private static class EntityToDTOConverter implements Function<AssetEntity, AssetResponseDTO> {

        @Override
        public AssetResponseDTO apply(AssetEntity assetEntity) {
            return new AssetResponseDTO(
                    assetEntity.id(),
                    assetEntity.description(),
                    assetEntity.assetStatusCd(),
                    assetEntity.assetVersion(),
                    TimeUtils.getUTCMillisecondsFromLocalDateTime(assetEntity.lastChangeTs()),
                    null
            );
        }
    }
}
