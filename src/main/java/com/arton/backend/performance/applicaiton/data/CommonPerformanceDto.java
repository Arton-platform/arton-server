package com.arton.backend.performance.applicaiton.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "메인 페이지에 공통으로 사용할 Dto")
public class CommonPerformanceDto {
    @Schema(description = "공연 ID")
    private Long id;
    @Schema(description = "공연 제목")
    private String title;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    @Builder
    public CommonPerformanceDto(Long id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
