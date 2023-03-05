package com.collibra.common.dto.web.response;

public record AssetCreationResponseDTO (
        Long assetId,
        AssetCreationResponseStatus assetCreationResponseStatus,
        String error
) {

}
