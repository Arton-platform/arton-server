package com.arton.backend.zzim.domain;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PerformanceZzim {
    private Long id;
    private Long performanceId;
    private Long userId;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setUser(Long userId) {
        this.userId = userId;
    }

    @Builder
    public PerformanceZzim(Long id, Long performanceId, Long userId, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
