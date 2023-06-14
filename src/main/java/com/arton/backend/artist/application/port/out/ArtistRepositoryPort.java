package com.arton.backend.artist.application.port.out;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArtistRepositoryPort {
    List<Artist> findAll();
    Page<Artist> findAll(Pageable pageable);
    List<Artist> findAllByLimit(int offset, int limit);
    List<Artist> findByIds(List<Long> ids);
    List<Artist> findByName(String name);
    Optional<Artist> findById(Long id);
    List<Artist> findByPerformanceType(PerformanceType performanceType);
    List<Artist> findByPerformanceType(PerformanceType performanceType, Pageable pageable);
    Artist save(Artist artist);
    List<Artist> saveAll(List<Artist> artists);
    Boolean checkDup(String name, String profileImageUrl);
}
