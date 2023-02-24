package com.arton.backend.administer.performance.application.data;

import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "관리자 페이지 검색 조건 Dto")
@ToString
public class PerformanceAdminSearchDto {
    private ShowCategory showCategory;
    private PerformanceType performanceType;
    private String keyword;
}
