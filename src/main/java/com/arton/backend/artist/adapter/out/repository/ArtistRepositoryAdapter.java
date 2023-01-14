package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryAdapter implements ArtistRepositoryPort {
    private final ArtistRepository artistRepository;

    @Override
    public List<ArtistEntity> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public List<ArtistEntity> findByIds(List<Long> ids) {
        return artistRepository.findAllById(ids);
    }

    @Override
    public List<ArtistEntity> findByPerformanceType(PerformanceType performanceType) {
        return artistRepository.getArtistByPerformanceType(performanceType);
    }

    @Override
    public ArtistEntity save(ArtistEntity artist) {
        return artistRepository.save(artist);
    }
}
