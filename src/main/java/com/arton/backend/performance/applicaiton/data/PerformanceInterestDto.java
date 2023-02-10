package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 취향찜에서 관심 공연을 보여주는 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "공연 찜하기 DTO")
public class PerformanceInterestDto {
    @Schema(description = "공연 ID")
    private Long id;
    @Schema(description = "공연 제목")
    private String title;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    public static PerformanceInterestDto of(Performance performance) {
        return PerformanceInterestDto.builder()
                .id(performance.getPerformanceId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .build();
    }
}
