package com.arton.backend.search.application.data;

import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

import static org.springframework.util.ObjectUtils.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchResultDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String place;
    private String startDate;
    private String endDate;
    private String performanceType;
    private String showCategory;

    @Builder
    public SearchResultDto(Long id, String imageUrl, String title, String place, String startDate, String endDate, String performanceType, String showCategory) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.performanceType = performanceType;
        this.showCategory = showCategory;
    }

    public static SearchResultDto toResultFromDocument(PerformanceDocument document) {
        return SearchResultDto.builder()
                .id(document.getId())
                .imageUrl(document.getImageUrl())
                .title(document.getTitle())
                .place(document.getPlace())
                .startDate(isEmpty(document.getStartDate()) ? null : document.getStartDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .endDate(isEmpty(document.getEndDate()) ? null : document.getEndDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .performanceType(document.getPerformanceType())
                .showCategory(document.getShowCategory())
                .build();
    }

}
