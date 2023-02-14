package com.arton.backend.performance.applicaiton.data;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "곧 종료 공연을 위한 Dto")
public class EndDateBasedPerformanceDto extends CommonPerformanceDto{
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "공연 종료일")
    private String endDate;
}
