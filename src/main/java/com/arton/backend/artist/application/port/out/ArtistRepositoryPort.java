package com.arton.backend.artist.application.port.out;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;

import java.util.List;

public interface ArtistRepositoryPort {
    List<Artist> findAll();
    List<Artist> findByIds(List<Long> ids);
    List<Artist> findByPerformanceType(PerformanceType performanceType);
    Artist save(Artist artist);
}
