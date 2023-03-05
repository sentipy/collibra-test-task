package com.collibra.collibratesttaskweb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(schema = "asset", name = "assets")
public record AssetEntity(
        @Id
        Long id,
        @Column("description")
        String description,
        @Column("asset_status_cd")
        String assetStatusCd,
        @Column("asset_version")
        long assetVersion,
        @Column("lst_chg_ts")
        LocalDateTime lastChangeTs
) {
}
