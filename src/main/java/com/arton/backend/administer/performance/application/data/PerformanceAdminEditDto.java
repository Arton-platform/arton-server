package com.arton.backend.administer.performance.application.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Data
@Schema(description = "관리자 페이지 공연 수정 Dto")
@ToString
@Builder
public class PerformanceAdminEditDto {
    @Schema(description = "ID")
    private Long performanceId;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "설명")
    private String description;
    @Schema(description = "공연 시작일")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Schema(description = "공연 종료일")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
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
    @Schema(description = "티켓팅 오픈일")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ticketOpenDate;
    @Schema(description = "티켓팅 종료일")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ticketEndDate;
    @Schema(description = "매수 제한")
    private Integer purchaseLimit;
    @Schema(description = "노출 상태")
    private ShowCategory showCategory;
//    @Schema(description = "공연 이미지")
//    private List<MultipartFile> images;

    public static PerformanceAdminEditDto domainToDto(Performance performance) {
        return PerformanceAdminEditDto.builder()
                .performanceId(performance.getPerformanceId())
                .purchaseLimit(performance.getPurchaseLimit())
                .performanceType(performance.getPerformanceType())
                .ticketOpenDate(performance.getTicketOpenDate().truncatedTo(ChronoUnit.MINUTES))
                .ticketEndDate(performance.getTicketEndDate().truncatedTo(ChronoUnit.MINUTES))
                .description(performance.getDescription())
                .title(performance.getTitle())
                .startDate(performance.getStartDate().toLocalDate())
                .endDate(performance.getEndDate().toLocalDate())
                .interMission(performance.getInterMission())
                .limitAge(performance.getLimitAge())
                .link(performance.getLink())
                .place(performance.getPlace())
                .runningTime(performance.getRunningTime())
                .limitTime(performance.getLimitTime())
                .showCategory(performance.getShowCategory())
                .build();
    }

    public Performance dtoToDomain() {
        return Performance.builder()
                .performanceId(this.performanceId)
                .purchaseLimit(this.purchaseLimit)
                .performanceType(this.performanceType)
                .ticketOpenDate(this.ticketOpenDate)
                .ticketEndDate(this.ticketEndDate)
                .description(this.description)
                .title(this.title)
                .startDate(this.startDate.atStartOfDay())
                .endDate(this.endDate.atTime(LocalTime.MAX))
                .interMission(this.interMission)
                .limitAge(this.limitAge)
                .link(this.link)
                .place(this.place)
                .runningTime(this.runningTime)
                .limitTime(this.limitTime)
                .showCategory(this.showCategory)
                .build();
    }
}
