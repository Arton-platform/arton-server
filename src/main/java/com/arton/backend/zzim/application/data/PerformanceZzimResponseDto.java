package com.arton.backend.zzim.application.data;

import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.Builder;
import lombok.Data;

@Data
public class PerformanceZzimResponseDto {
    private Long performanceId;
    private Long userId;

    @Builder
    public PerformanceZzimResponseDto(Long performanceId, Long userId) {
        this.performanceId = performanceId;
        this.userId = userId;
    }

    public static PerformanceZzimResponseDto toDto(PerformanceZzim performanceZzim) {
        return PerformanceZzimResponseDto.builder()
                .performanceId(performanceZzim.getPerformanceId())
                .userId(performanceZzim.getUserId())
                .build();
    }
}
