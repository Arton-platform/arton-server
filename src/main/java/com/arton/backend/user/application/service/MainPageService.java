package com.arton.backend.user.application.service;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.application.data.MainPageDto;
import com.arton.backend.user.application.port.in.MainPageUseCase;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public class MainPageService implements MainPageUseCase {
    private UserRepositoryPort userRepositoryPort;
    private ZzimRepositoryPort zzimRepositoryPort;
    private PerformanceRepositoryPort performanceRepositoryPort;
    private ArtistRepositoryPort artistRepositoryPort;
    @Override
    public MainPageDto getMainPage(Long userId) {
        userRepositoryPort.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        List<Performance> performances = performanceRepositoryPort.findAll();
        List<PerformanceZzim> performanceZzims = zzimRepositoryPort.getPerformanceZzimByUserId(userId);
        List<Artist> artists = artistRepositoryPort.findAll();
        return null;
    }
}
