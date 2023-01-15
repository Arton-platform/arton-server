package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomArtistRepository{
    List<ArtistEntity> getArtistByPerformanceType(PerformanceType performanceType);
}
