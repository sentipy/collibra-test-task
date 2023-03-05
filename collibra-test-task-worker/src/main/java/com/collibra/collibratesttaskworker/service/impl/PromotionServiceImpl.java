package com.collibra.collibratesttaskworker.service.impl;

import com.collibra.collibratesttaskworker.service.PromotionService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public class PromotionServiceImpl implements PromotionService {

    public static final String ASSET_STATUS_BULK_UPDATE_SQL = "update asset.assets set asset_status_cd = 'PROMOTED' where id in (:ids)";
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public PromotionServiceImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    @Transactional
    public void promote(Long id) {
        updateStatusForAssets(Collections.singletonList(id));
        List<Long> children = getAssetParents(Collections.singletonList(id));
        while (children.size() > 0) {
            updateStatusForAssets(children);
            children = getAssetParents(children);
        }
    }

    private int updateStatusForAssets(List<Long> id) {
        return this.namedParameterJdbcOperations.update(
                ASSET_STATUS_BULK_UPDATE_SQL,
                new MapSqlParameterSource("ids", id)
        );
    }

    private List<Long> getAssetParents(List<Long> ids) {
        return this.namedParameterJdbcOperations.queryForList(
                "select asset_child from asset.asset_composition where asset_parent in (:assetParents)",
                new MapSqlParameterSource("assetParents", ids),
                Long.class
        );
    }
}
