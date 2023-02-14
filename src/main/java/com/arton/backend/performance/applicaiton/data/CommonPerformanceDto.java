package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "메인 페이지에 공통으로 사용할 공연 Dto")
public class CommonPerformanceDto {
    @Schema(description = "공연 ID")
    private Long id;
    @Schema(description = "공연 제목")
    private String title;
    @Schema(description = "이미지 링크")
    private String imageUrl;
    @Schema(description = "공연타입(뮤지컬/콘서트)")
    private PerformanceType performanceType;

    @Builder
    public CommonPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
    }
}
