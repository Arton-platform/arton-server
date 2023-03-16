package com.arton.backend.zzim.application.service;

import com.arton.backend.zzim.application.data.ArtistZzimResponseDto;
import com.arton.backend.zzim.application.data.PerformanceZzimResponseDto;
import com.arton.backend.zzim.application.port.in.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.in.ZzimUseCase;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ZzimService implements ZzimUseCase {

    private final ZzimRepositoryPort zzimRepository;

    @Override
    public void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto) {
        // 삭제
        zzimRepository.deleteUserFavoriteArtists(userId, deleteDto.getArtists());
        // performance 삭제
        zzimRepository.deleteUserFavoritePerformances(userId, deleteDto.getPerformances());
    }

    @Override
    public List<PerformanceZzimResponseDto> performanceList(Long userId) {
        return zzimRepository.getPerformanceZzimByUserId(userId).stream().map(PerformanceZzimResponseDto::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ArtistZzimResponseDto> artistList(Long userId) {
        return zzimRepository.getArtistZzimByUserId(userId).stream().map(ArtistZzimResponseDto::toDto).collect(Collectors.toList());
    }
}
