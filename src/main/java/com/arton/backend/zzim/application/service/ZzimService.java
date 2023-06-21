package com.arton.backend.zzim.application.service;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.zzim.application.data.ArtistZzimResponseDtoV2;
import com.arton.backend.zzim.application.data.PerformanceZzimResponseDtoV2;
import com.arton.backend.zzim.application.data.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.in.ZzimUseCase;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ZzimService implements ZzimUseCase {

    private final ZzimRepositoryPort zzimRepository;
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final ArtistRepositoryPort artistRepositoryPort;

    @Override
    public void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto) {
        // 삭제
        zzimRepository.deleteUserFavoriteArtists(userId, deleteDto.getArtists());
        // performance 삭제
        zzimRepository.deleteUserFavoritePerformances(userId, deleteDto.getPerformances());
    }

    @Override
    public List<PerformanceZzimResponseDtoV2> performanceList(Long userId) {
        List<Long> performanceIds = zzimRepository.getPerformanceZzimByUserId(userId).stream().map(PerformanceZzim::getPerformanceId).collect(Collectors.toList());
        return Optional.ofNullable(performanceRepositoryPort.findByIds(performanceIds)).orElseGet(Collections::emptyList).stream().map(PerformanceZzimResponseDtoV2::domainToDto).collect(Collectors.toList());
    }

    @Override
    public List<ArtistZzimResponseDtoV2> artistList(Long userId) {
        List<Long> artistIds = zzimRepository.getArtistZzimByUserId(userId).stream().map(ArtistZzim::getId).collect(Collectors.toList());
        return Optional.ofNullable(artistRepositoryPort.findByIds(artistIds)).orElseGet(Collections::emptyList).stream().map(ArtistZzimResponseDtoV2::domainToDto).collect(Collectors.toList());
    }
}
