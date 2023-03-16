package com.arton.backend.zzim.application.port.in;

import com.arton.backend.zzim.application.data.ArtistZzimResponseDto;
import com.arton.backend.zzim.application.data.PerformanceZzimResponseDto;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface ZzimUseCase {
    void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto);
    List<PerformanceZzimResponseDto> performanceList(Long userId);
    List<ArtistZzimResponseDto> artistList(Long userId);
}
