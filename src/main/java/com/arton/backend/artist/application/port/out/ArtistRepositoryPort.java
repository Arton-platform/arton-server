package com.arton.backend.artist.application.port.out;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;

import java.util.List;

public interface ArtistRepositoryPort {
    List<ArtistEntity> findAll();
    List<ArtistEntity> findByIds(List<Long> ids);
    List<ArtistEntity> findByPerformanceType(PerformanceType performanceType);
    ArtistEntity save(ArtistEntity artist);
}