package com.arton.backend.artist.application.service;

import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.application.port.in.ArtistZzimDto;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService implements ArtistUseCase {
    private final ArtistRepositoryPort artistRepositoryPort;

    @Override
    public List<ArtistZzimDto> showArtistListForZzim(String type) {
        PerformanceType performanceType = PerformanceType.get(type);
        return artistRepositoryPort.findByPerformanceType(performanceType).stream()
                .map(ArtistZzimDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ArtistZzimDto> showArtistListForZzim() {
        return artistRepositoryPort.findAll().stream()
                .map(ArtistZzimDto::of).collect(Collectors.toList());
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepositoryPort.save(artist);
    }
}
