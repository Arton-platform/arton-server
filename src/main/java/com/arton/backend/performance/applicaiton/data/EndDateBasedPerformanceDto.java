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
@Schema(description = "곧 종료 공연을 위한 Dto")
@ToString
public class EndDateBasedPerformanceDto extends CommonPerformanceDto{
    @Schema(description = "장소")
    private String place;
    @Schema(description = "공연일시 yyyy.MM.dd~yyyy.MM.dd")
    private String musicalDateTime;
    @Schema(description = "공연 종료일")
    private String ticketEndDate;

    @Builder
    public EndDateBasedPerformanceDto(Long id, String title, String imageUrl, PerformanceType performanceType, String place, String musicalDateTime, String ticketEndDate) {
        super(id, title, imageUrl, performanceType);
        this.place = place;
        this.musicalDateTime = musicalDateTime;
        this.ticketEndDate = ticketEndDate;
    }

    public static EndDateBasedPerformanceDto domainToDto(Performance performance) {
        // 12.24 12:20
        String[] splitDate = performance.getTicketEndDate().format(DateTimeFormatter.ofPattern("MM.dd HH:mm")).split(" ");
        String textDay = performance.getTicketEndDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return EndDateBasedPerformanceDto.builder()
                .id(performance.getPerformanceId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .performanceType(performance.getPerformanceType())
                .place(performance.getPlace())
                .musicalDateTime(performance.getMusicalDateTime())
                .ticketEndDate(splitDate[0]+"("+textDay+")"+" "+splitDate[1])
                .build();
    }
}
