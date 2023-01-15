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
    private Performance performance;
    private User user;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setUser(User user) {
        this.user = user;
    }

    @Builder
    public PerformanceZzim(Long id, Performance performance, User user, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.performance = performance;
        this.user = user;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
