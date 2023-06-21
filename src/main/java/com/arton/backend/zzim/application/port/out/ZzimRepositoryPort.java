package com.arton.backend.zzim.application.port.out;

import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

/**
 * 도메인으로 통신해야 DB가 바뀌더라도 포트는 고칠부분이 없음. 어댑터 부분만 수정하면됨.
 */
public interface ZzimRepositoryPort {
    ArtistZzim zzimArtist(ArtistZzim artistZzim);
    PerformanceZzim zzimPerformance(PerformanceZzim performanceZzim);
    List<ArtistZzim> saveArtists(List<ArtistZzim> artistZzims);
    List<ArtistZzim> getArtistZzimByUserId(Long userId);
    List<PerformanceZzim> savePerformances(List<PerformanceZzim> performanceZzims);
    List<PerformanceZzim> getPerformanceZzimByUserId(Long userId);
    long deleteUserFavoriteArtists(Long userId, List<Long> ids);
    long deleteUserFavoritePerformances(Long userId, List<Long> ids);
    void deleteAllFavoriteArtist(Long userId);
    void deleteAllFavoritePerformance(Long userId);
    void deleteAllFavorites(Long userId);

    boolean checkArtistZzimDup(Long userId, Long artistId);
    boolean checkPerformanceZzimDup(Long userId, Long performanceId);
}
