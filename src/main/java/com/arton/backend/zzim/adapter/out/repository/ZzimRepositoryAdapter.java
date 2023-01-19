package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.application.port.in.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ZzimRepositoryAdapter implements ZzimRepositoryPort {
    private final ArtistZzimRepository artistZzimRepository;
    private final PerformanceZzimRepository performanceZzimRepository;

    @Override
    public List<ArtistZzim> saveArtists(List<ArtistZzim> artistZzims) {
        List<ArtistZzimEntity> response = Optional.ofNullable(artistZzims).orElseGet(Collections::emptyList).stream().map(ArtistZzimMapper::toEntity).collect(Collectors.toList());
        return Optional.ofNullable(artistZzimRepository.saveAll(response)).orElseGet(Collections::emptyList).stream().map(ArtistZzimMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<PerformanceZzim> savePerformances(List<PerformanceZzim> performanceZzims) {
        List<PerformanceZzimEntity> response = Optional.ofNullable(performanceZzims).orElseGet(Collections::emptyList).stream().map(PerformanceZzimMapper::toEntity).collect(Collectors.toList());
        return Optional.ofNullable(performanceZzimRepository.saveAll(response)).orElseGet(Collections::emptyList).stream().map(PerformanceZzimMapper::toDomain).collect(Collectors.toList());

    }

    /**
     * @param userId user id
     * @param ids artist zzim ids
     * @return
     */
    @Override
    public long deleteUserFavoriteArtists(Long userId, List<Long> ids) {
        return artistZzimRepository.deleteUsersFavoriteArtists(userId, ids);
    }

    /**
     * @param userId
     * @param ids performance zzim ids
     * @return
     */
    @Override
    public long deleteUserFavoritePerformances(Long userId, List<Long> ids) {
        return performanceZzimRepository.deleteUsersFavoritePerformances(userId, ids);
    }

    @Override
    public void deleteAllFavoriteArtist(Long userId) {
        artistZzimRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteAllFavoritePerformance(Long userId) {
        performanceZzimRepository.deleteAllByUserId(userId);
    }

    @Override
    public void deleteAllFavorites(Long userId) {
        artistZzimRepository.deleteAllByUserId(userId);
    }
}
