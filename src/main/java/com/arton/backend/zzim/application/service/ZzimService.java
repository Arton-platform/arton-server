package com.arton.backend.zzim.application.service;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.application.data.ArtistZzimResponseDtoV2;
import com.arton.backend.zzim.application.data.PerformanceZzimResponseDtoV2;
import com.arton.backend.zzim.application.data.ZzimCreateDto;
import com.arton.backend.zzim.application.data.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.in.ZzimCreateUseCase;
import com.arton.backend.zzim.application.port.in.ZzimUseCase;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.cluster.ClusterState;
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
public class ZzimService implements ZzimUseCase, ZzimCreateUseCase {

    private final ZzimRepositoryPort zzimRepository;
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final ArtistRepositoryPort artistRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final PerformanceSavePort performanceSavePort;

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

    @Override
    public void createArtistZzim(Long userId, ZzimCreateDto zzimCreateDto) {
        // 중복 체크
        boolean isExist = zzimRepository.checkArtistZzimDup(userId, zzimCreateDto.getId());
        if (isExist){
            throw new CustomException(ErrorCode.ZZIM_IS_EXIST.getMessage(), ErrorCode.ZZIM_IS_EXIST);
        }
        Artist artist = artistRepositoryPort.findById(zzimCreateDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND.getMessage(), ErrorCode.ARTIST_NOT_FOUND));
        zzimRepository.zzimArtist(ArtistZzim.builder().artistId(artist.getId()).userId(userId).build());
    }

    /**
     * 공연 좋아요 추가해야함.
     * @param userId
     * @param zzimCreateDto
     */
    @Override
    public void createPerformanceZzim(Long userId, ZzimCreateDto zzimCreateDto) {
        // 중복 체크
        boolean isExist = zzimRepository.checkPerformanceZzimDup(userId, zzimCreateDto.getId());
        if (isExist){
            throw new CustomException(ErrorCode.ZZIM_IS_EXIST.getMessage(), ErrorCode.ZZIM_IS_EXIST);
        }
        Performance performance = performanceRepositoryPort.findById(zzimCreateDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND));
        zzimRepository.zzimPerformance(PerformanceZzim.builder().performanceId(performance.getPerformanceId()).userId(userId).build());
        // update performance
        performance.addHit();
        performanceSavePort.save(performance);
    }
}
