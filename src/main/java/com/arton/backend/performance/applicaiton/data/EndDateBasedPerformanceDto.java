package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "곧 종료 공연을 위한 Dto")
@ToString
public class EndDateBasedPerformanceDto extends CommonPerformanceDto{
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "공연 종료일")
    private String endDate;

    @Builder
    public EndDateBasedPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType, String place, String musicalDateTime, String endDate) {
        super(id, title, imageUrl, performanceType);
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.endDate = endDate;
    }
}
