package com.collibra.collibratesttaskweb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(schema = "asset", name = "asset_composition")
public record AssetCompositionEntity(
        @Id
        Long id,
        @Column("asset_parent")
        Long assetParent,
        @Column("asset_child")
        Long assetChild,
        @Column("lst_chg_ts")
        LocalDateTime lastChangeTs
) {
}
