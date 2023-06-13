package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CrawlerPerformanceCreateDTO {
    private String title;
    private String musicalDateTime;
    private String startDate;
    private String endDate;
    private String ticketOpenDate;
    private String ticketEndDate;
    private String place;
    private String runningTime;
    private String limitAge;
    private String description;
    private String imageUrl;
    private String performanceType;
    private List<CrawlerArtistRegistDTO> artists;

}
