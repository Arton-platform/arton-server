package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.mapper.ArtistMapper;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.artist.adapter.out.persistence.mapper.ArtistMapper.*;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryAdapter implements ArtistRepositoryPort {
    private final ArtistRepository artistRepository;

    @Override
    public List<Artist> findAll() {
        return Optional.ofNullable(artistRepository.findAll()).orElseGet(Collections::emptyList)
                .stream().map(ArtistMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Artist> findAllByLimit(int offset, int limit) {
        return Optional.ofNullable(artistRepository.getArtistByLimit(offset, limit))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ArtistMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Artist> findByIds(List<Long> ids) {
        return Optional.ofNullable(artistRepository.findAllById(ids)).orElseGet(Collections::emptyList)
                .stream().map(ArtistMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Artist> findByPerformanceType(PerformanceType performanceType) {
        return Optional.ofNullable(artistRepository.getArtistByPerformanceType(performanceType)).orElseGet(Collections::emptyList)
                .stream().map(ArtistMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Artist> findByPerformanceType(PerformanceType performanceType, Pageable pageable) {
        return Optional.ofNullable(artistRepository.getArtistByPerformanceType(performanceType, pageable)).orElseGet(Collections::emptyList)
                .stream().map(ArtistMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Artist save(Artist artist) {
        return toDomain(artistRepository.save(toEntity(artist)));
    }
}
