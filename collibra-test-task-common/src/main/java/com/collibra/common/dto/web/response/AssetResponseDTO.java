package com.collibra.common.dto.web.response;

public record AssetResponseDTO(
        Long id,
        String description,
        String assetStatusCd,
        Long assetVersion,
        Long lastChangeTs,
        String error
) {
}
