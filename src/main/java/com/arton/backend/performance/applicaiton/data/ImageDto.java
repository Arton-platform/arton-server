package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.image.domain.PerformanceImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ImageDto {
    private String imageUrl;

    @Builder
    @QueryProjection
    public ImageDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageDto domainToDto(PerformanceImage performanceImage) {
        return new ImageDto(performanceImage.getImageUrl());
    }
}
