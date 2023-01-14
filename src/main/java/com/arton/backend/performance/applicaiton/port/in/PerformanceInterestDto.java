package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import lombok.*;

/**
 * 취향찜에서 관심 공연을 보여주는 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PerformanceInterestDto {
    private Long id;
    private String title;
    private String imageUrl;

    public static PerformanceInterestDto of(PerformanceEntity performance) {
        return PerformanceInterestDto.builder()
                .id(performance.getId())
                .title(performance.getTitle())
                .imageUrl(performance.getImageUrl())
                .build();
    }
}
