package com.collibra.collibratesttaskweb.repository;

import com.collibra.collibratesttaskweb.entity.AssetEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AssetRepository extends ReactiveCrudRepository<AssetEntity, Long> {
}
