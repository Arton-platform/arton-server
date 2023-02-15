package com.arton.backend.performance.applicaiton.data;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PerformanceSearchDto {
    private String performanceType;
    private String title;
    private String place;
    private String artist;
}
