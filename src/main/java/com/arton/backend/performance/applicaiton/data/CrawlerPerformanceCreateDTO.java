package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.infra.utils.LocalDateTimeConverter;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.performance.domain.ShowCategory;
import com.arton.backend.price.application.data.CrawlerPriceCreateDTO;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class CrawlerPerformanceCreateDTO {
    @NotBlank
    private String title;
    private String musicalDateTime;
    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    private String ticketOpenDate;
    private String ticketEndDate;
    private String place;
    private String runningTime;
    private String limitAge;
    private String description;
    private String imageUrl;
    private String performanceType;
    private String link;
    private List<CrawlerArtistRegistDTO> artists;
    private List<CrawlerPriceCreateDTO> grades;

    public Performance toDomainFromDTO() {
        LocalDateTime convertedStartDate = LocalDateTimeConverter.strToDate(startDate);
        LocalDateTime convertedEndDate = LocalDateTimeConverter.strToDate(endDate);
        return Performance.builder()
                .purchaseLimit(4)
                .performanceType(PerformanceType.get(performanceType))
                .description(getDescription())
                .title(getTitle())
                .startDate(convertedStartDate)
                .endDate(convertedEndDate)
                .hit(0L)
                .interMission(0)
                .limitAge(Integer.parseInt(getLimitAge()))
                .link(getLink().isEmpty() ? "" : getLink())
                .place(getPlace())
                .runningTime(Integer.parseInt(getRunningTime()))
                .starScore(0)
                .limitTime(0)
                .musicalDateTime(getMusicalDateTime())
                .showCategory(ShowCategory.NORMAL)
                .build();
    }

}
