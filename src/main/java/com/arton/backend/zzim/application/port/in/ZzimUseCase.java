package com.arton.backend.zzim.application.port.in;

import com.arton.backend.zzim.application.data.ArtistZzimResponseDtoV2;
import com.arton.backend.zzim.application.data.PerformanceZzimResponseDtoV2;

import java.util.List;

public interface ZzimUseCase {
    void deleteUsersFavorite(Long userId, ZzimDeleteDto deleteDto);
    List<PerformanceZzimResponseDtoV2> performanceList(Long userId);
    List<ArtistZzimResponseDtoV2> artistList(Long userId);
}
