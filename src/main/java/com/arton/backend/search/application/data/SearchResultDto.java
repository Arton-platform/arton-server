package com.arton.backend.search.application.data;

import com.arton.backend.search.persistence.document.PerformanceDocument;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchResultDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String place;
    private String startDate;
    private String endDate;

    @Builder
    public SearchResultDto(Long id, String imageUrl, String title, String place, String startDate, String endDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static SearchResultDto toResultFromDocument(PerformanceDocument document) {
        return SearchResultDto.builder()
                .id(document.getId())
                .imageUrl(document.getImageUrl())
                .title(document.getTitle())
                .place(document.getPlace())
                .startDate(ObjectUtils.isEmpty(document.getStartDate()) ? null : document.getStartDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .endDate(ObjectUtils.isEmpty(document.getEndDate()) ? null : document.getEndDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build();
    }
}
