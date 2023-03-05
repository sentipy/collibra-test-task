package com.collibra.common.dto.web.response;

public record AssetPromotionWebResponseDTO(
        long assetId,
        AssetPromotionWebResponseStatus status,
        String error
) {

}
