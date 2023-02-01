package com.arton.backend.price.domain;

import com.arton.backend.performance.domain.Performance;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 좌석 등급별 가격은 공연마다 다름
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"performance"})
public class PriceGrade {
    private Long id;
    /** 좌석 등급 */
    private String gradeName;
    /** 좌석 가격 */
    private Long price;
    private Performance performance;
    /** 등록일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;

    @Builder
    public PriceGrade(Long id, String gradeName, Long price, Performance performance, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.gradeName = gradeName;
        this.price = price;
        this.performance = performance;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
