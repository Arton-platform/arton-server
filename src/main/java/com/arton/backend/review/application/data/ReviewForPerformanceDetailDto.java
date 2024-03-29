package com.arton.backend.review.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceDetailDto {
    private Integer percent;
    private List<String> images = new ArrayList<>();
    private List<CommonReviewDto> reviews = new ArrayList<>();

    @Builder
    public ReviewForPerformanceDetailDto(Integer percent, List<String> images, List<CommonReviewDto> reviews) {
        this.percent = percent;
        this.images = images;
        this.reviews = reviews;
    }
}
