package com.arton.backend.artist.application.service;

import com.arton.backend.artist.application.data.ArtistInterestDetailDTO;
import com.arton.backend.artist.application.data.ArtistInterestDto;
import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ArtistInterestDetailDTO showArtistListForZzim(Pageable pageable) {
        List<ArtistInterestDto> concerts = artistRepositoryPort.findByPerformanceType(PerformanceType.CONCERT, pageable)
                .stream()
                .map(ArtistInterestDto::of)
                .collect(Collectors.toList());

        List<ArtistInterestDto> musicals = artistRepositoryPort.findByPerformanceType(PerformanceType.MUSICAL, pageable)
                .stream()
                .map(ArtistInterestDto::of)
                .collect(Collectors.toList());

        return ArtistInterestDetailDTO.builder().concerts(concerts).musicals(musicals).build();
    }

    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepositoryPort.findAll(pageable);
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepositoryPort.save(artist);
    }
}
