package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Schema(description = "공연 생성을 위한 Dto")
@ToString
public class PerformanceCreateDto {
    @Schema(description = "ID")
    private Long performanceId;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "설명")
    private String description;
    @Schema(description = "공연 시작일")
    private LocalDateTime startDate;
    @Schema(description = "공연 종료일")
    private LocalDateTime endDate;
    @Schema(description = "상영시간")
    private Integer runningTime;
    @Schema(description = "휴식시간")
    private Integer interMission;
    @Schema(description = "제한시간")
    private Integer limitTime;
    @Schema(description = "제한나이")
    private Integer limitAge;
    @Schema(description = "예매 링크")
    private String link;
    @Schema(description = " 뮤지컬 or 콘서트")
    private PerformanceType performanceType;
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "티켓팅 오픈일")
    private LocalDateTime ticketOpenDate;
    @Schema(description = "티켓팅 종료일")
    private LocalDateTime ticketEndDate;
    @Schema(description = "매수 제한")
    private Integer purchaseLimit;
}
