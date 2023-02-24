package com.arton.backend.performance.applicaiton.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "공연 검색 조건 Dto")
@ToString
public class PerformanceSearchDto {
    private String performanceType;
    private String title;
    private String place;
    private String artist;
}
