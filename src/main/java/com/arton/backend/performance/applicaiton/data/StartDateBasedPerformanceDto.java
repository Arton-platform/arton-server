package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "곧 오픈 공연을 위한 Dto")
public class StartDateBasedPerformanceDto extends CommonPerformanceDto{
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "공연 오픈일")
    private String startDate;

    @Builder
    public StartDateBasedPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType, String place, String musicalDateTime, String startDate) {
        super(id, title, imageUrl, performanceType);
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.startDate = startDate;
    }

    public static StartDateBasedPerformanceDto toDtoFromDomain
}
