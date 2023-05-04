package com.arton.backend.artist.application.port.out;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistRepositoryPort {
    List<Artist> findAll();
    List<Artist> findAllByLimit(int offset, int limit);
    List<Artist> findByIds(List<Long> ids);
    List<Artist> findByPerformanceType(PerformanceType performanceType);
    List<Artist> findByPerformanceType(PerformanceType performanceType, Pageable pageable);
    Artist save(Artist artist);
}
