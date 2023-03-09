package com.arton.backend.user.application.service;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.data.CommonPerformanceDto;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.user.application.data.MainPageDto;
import com.arton.backend.user.application.port.in.MainPageUseCase;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MainPageService implements MainPageUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final ZzimRepositoryPort zzimRepositoryPort;
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final ArtistRepositoryPort artistRepositoryPort;

    /**
     * 홈 화면에 필요한 데이터 제공
     * 최대 10개 까지만 제공
     * @param userId
     * @return
     */
    @Override
    public MainPageDto getMainPage(Long userId, int offset, int limit) {
        userRepositoryPort.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        List<CommonPerformanceDto> performances = performanceRepositoryPort.findAllByLimit(offset, limit).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());
        List<Long> performanceIds = zzimRepositoryPort.getPerformanceZzimByUserId(userId).stream().map(PerformanceZzim::getPerformanceId).collect(Collectors.toList());
        List<CommonPerformanceDto> performanceZzims = performanceRepositoryPort.findByIds(performanceIds, offset, limit).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());
        List<CommonArtistDto> artists = artistRepositoryPort.findAllByLimit(offset, limit).stream().map(CommonArtistDto::domainToDto).collect(Collectors.toList());
        List<CommonPerformanceDto> popularPerformances = performanceRepositoryPort.findPopularPerformances(offset, limit).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());
        List<CommonPerformanceDto> endingSoonPerformances = performanceRepositoryPort.findEndingSoonPerformances(offset, limit).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());
        List<CommonPerformanceDto> startingSoonPerformances = performanceRepositoryPort.findStartingSoonPerformances(offset, limit).stream().map(CommonPerformanceDto::domainToDto).collect(Collectors.toList());

        return MainPageDto.builder()
                .performances(performances)
                .zzims(performanceZzims)
                .artists(artists)
                .popular(popularPerformances)
                .endingSoon(endingSoonPerformances)
                .startingSoon(startingSoonPerformances)
                .build();
    }
}
