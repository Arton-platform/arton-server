package com.arton.backend.image.domain;

import com.arton.backend.performance.domain.Performance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"performance"})
public class PerformanceImage {
    private Long id;
    private String imageUrl;
    private Performance performance;
    /** 등록일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setPerformance(Performance performance) {
        this.performance = performance;
    }
}
