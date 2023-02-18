package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "곧 오픈 공연을 위한 Dto")
@ToString
public class StartDateBasedPerformanceDto extends CommonPerformanceDto{
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "티켓팅 오픈일")
    private String ticketOpenDate;

    @Builder
    public StartDateBasedPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType, String place, String musicalDateTime, String ticketOpenDate) {
        super(id, title, imageUrl, performanceType);
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.ticketOpenDate = ticketOpenDate;
    }

    public static StartDateBasedPerformanceDto domainToDto(Performance performance) {
        // 12.24 12:20
        String[] splitDate = performance.getTicketOpenDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String textDay = performance.getTicketOpenDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return StartDateBasedPerformanceDto.builder()
                .id(performance.getPerformanceId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .place(performance.getPlace())
                .musicalDateTime(performance.getMusicalDateTime())
                .ticketOpenDate(splitDate[0]+"("+textDay+")"+" "+splitDate[1])
                .build();
    }
}
