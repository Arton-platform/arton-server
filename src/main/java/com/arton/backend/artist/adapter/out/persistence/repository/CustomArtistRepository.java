package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.performance.domain.PerformanceType;

import java.util.List;

public interface CustomArtistRepository{
    List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType);
}