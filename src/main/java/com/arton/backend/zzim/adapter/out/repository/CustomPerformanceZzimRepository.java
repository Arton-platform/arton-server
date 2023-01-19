package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface CustomPerformanceZzimRepository {
    List<PerformanceZzim> getUsersFavoritePerformances(Long userId);
    long deleteUsersFavoritePerformances(Long userId, List<Long> ids);
}
