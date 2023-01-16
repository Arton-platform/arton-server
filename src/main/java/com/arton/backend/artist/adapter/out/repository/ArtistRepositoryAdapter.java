package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.artist.adapter.out.repository.ArtistMapper.*;

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
    public Artist save(Artist artist) {
        return toDomain(artistRepository.save(toEntity(artist)));
    }
}
