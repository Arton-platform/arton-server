package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomArtistRepository{
    List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType);
    List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType, Pageable pageable);
    List<ArtistEntity> getArtistByLimit(int offset, int limit);
    List<ArtistEntity> getArtistByPage(Pageable pageable);
}