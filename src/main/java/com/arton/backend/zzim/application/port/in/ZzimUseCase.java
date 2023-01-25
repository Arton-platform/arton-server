package com.arton.backend.zzim.application.port.in;

import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface ZzimUseCase {
    void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto);
    List<PerformanceZzim> performanceList(Long userId);
    List<ArtistZzim> artistList(Long userId);
}
