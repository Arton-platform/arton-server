package com.arton.backend.zzim.application.port.in;

import com.arton.backend.zzim.application.data.ZzimCreateDto;

public interface ZzimCreateUseCase {
    void createArtistZzim(Long userId, ZzimCreateDto zzimCreateDto);
    void createPerformanceZzim(Long userId, ZzimCreateDto zzimCreateDto);
}
