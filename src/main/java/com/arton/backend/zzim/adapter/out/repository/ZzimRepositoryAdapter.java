package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ZzimRepositoryAdapter implements ZzimRepositoryPort {
    private final ArtistZzimRepository artistZzimRepository;
    private final PerformanceZzimRepository performanceZzimRepository;

    @Override
    public List<ArtistZzimEntity> saveArtists(List<ArtistZzimEntity> artistZzims) {
        return artistZzimRepository.saveAll(artistZzims);
    }

    @Override
    public List<PerformanceZzimEntity> savePerformances(List<PerformanceZzimEntity> performanceZzims) {
        return performanceZzimRepository.saveAll(performanceZzims);
    }
}
