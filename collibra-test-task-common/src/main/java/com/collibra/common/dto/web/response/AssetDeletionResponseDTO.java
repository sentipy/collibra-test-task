package com.collibra.common.dto.web.response;

public record AssetDeletionResponseDTO (
        long id,
        AssetDeletionResponseStatus AssetDeletionResponseStatus,
        String error
) {

}
