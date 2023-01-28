package com.arton.backend.image.domain;

import com.arton.backend.performance.domain.Performance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PerformanceImage {
    private Long id;

    private String imageUrl;

    private Performance performance;

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }
}
