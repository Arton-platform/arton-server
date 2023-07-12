package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.zzim.domain.PerformanceZzim;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "메인 페이지에 공통으로 사용할 공연 Dto")
@ToString
public class CommonPerformanceDto {
    @Schema(description = "공연 ID")
    private Long id;
    @Schema(description = "공연 제목")
    private String title;
    @Schema(description = "이미지 링크")
    private String imageUrl;
    @Schema(description = "공연타입(뮤지컬/콘서트)")
    private PerformanceType performanceType;
    @Schema(description = "장소")
    private String place;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "티켓팅 오픈일")
    private String ticketOpenDate;
    @Schema(description = "공연 종료일")
    private String ticketEndDate;

    @Builder
    public CommonPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType, String place, LocalDateTime startDate, LocalDateTime endDate, String musicalDateTime, String ticketOpenDate, String ticketEndDate) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.performanceType = performanceType;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.musicalDateTime = musicalDateTime;
        this.ticketOpenDate = ticketOpenDate;
        this.ticketEndDate = ticketEndDate;
    }

    public static CommonPerformanceDto domainToDto(Performance performance) {
        String ticketOpenDay = null;
        String ticketEndDay = null;
        boolean isTicketStart = false;
        boolean isTicketEnd = false;
        if (!ObjectUtils.isEmpty(performance.getTicketOpenDate())) {
            String[] ticketOpenDate = Optional.ofNullable(performance.getTicketOpenDate()).orElseGet(null).format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(performance.getTicketOpenDate()).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            ticketOpenDay = ticketOpenDate[0]+"("+textDay+")"+" "+ticketOpenDate[1];
            isTicketStart = true;
        }
        if (!ObjectUtils.isEmpty(performance.getTicketEndDate())) {
            String[] ticketEndDate = Optional.ofNullable(performance.getTicketEndDate()).orElseGet(null).format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
            String textDay = Optional.ofNullable(performance.getTicketEndDate()).orElseGet(null).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            ticketEndDay = ticketEndDate[0]+"("+textDay+")"+" "+ticketEndDate[1];
            isTicketEnd = true;
        }

        return CommonPerformanceDto.builder()
                .id(performance.getPerformanceId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .place(performance.getPlace())
                .startDate(performance.getStartDate())
                .endDate(performance.getEndDate())
                .musicalDateTime(performance.getMusicalDateTime())
                .ticketOpenDate(isTicketStart ? ticketOpenDay : "날짜 정보가 없습니다.")
                .ticketEndDate(isTicketEnd ? ticketEndDay : "날짜 정보가 없습니다.")
                .build();
    }
}
