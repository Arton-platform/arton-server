package com.arton.backend.administer.performance.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "공연 생성 Response DTO")
public class PerformanceAdminCreateResponseDto implements Serializable {
    @Schema(description = "ID")
    private Long performanceId;
    @Schema(description = "아티스트 ID")
    private List<Long> artistIds = new ArrayList<>();


    @Builder
    public PerformanceAdminCreateResponseDto(Long performanceId, List<Long> artistIds) {
        this.performanceId = performanceId;
        this.artistIds = artistIds;
    }
}
