package com.collibra.common.dto.web.request;

public record AssetCompositionCreateRequestDTO (
        Long parentAsset,
        Long childAsset
) {


}
