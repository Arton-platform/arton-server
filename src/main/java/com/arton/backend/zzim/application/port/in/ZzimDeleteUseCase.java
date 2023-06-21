package com.arton.backend.zzim.application.port.in;

public interface ZzimDeleteUseCase {
    void deleteArtistZzim(Long userId, Long artistId);
    void deletePerformanceZzim(Long userId, Long performanceId);
}
