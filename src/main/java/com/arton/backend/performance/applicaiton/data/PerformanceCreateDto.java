package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "공연 생성을 위한 Dto")
@ToString
public class PerformanceCreateDto implements Serializable {
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
    @Schema(description = "공연 이미지")
    List<MultipartFile> images;

    public Performance dtoToDomain() {
        return Performance.builder()
                .purchaseLimit(getPurchaseLimit())
                .performanceType(getPerformanceType())
                .ticketOpenDate(getTicketOpenDate())
                .ticketEndDate(getTicketEndDate())
                .description(getDescription())
                .title(getTitle())
                .startDate(getStartDate().atStartOfDay())
                .endDate(getEndDate().atTime(LocalTime.MAX))
                .hit(0L)
                .interMission(getInterMission())
                .limitAge(getLimitAge())
                .link(getLink())
                .place(getPlace())
                .runningTime(getRunningTime())
                .starScore(0)
                .limitTime(getLimitTime())
                .musicalDateTime(getStartDate().toString()+"~"+getEndDate().toString())
                .build();
    }
}
