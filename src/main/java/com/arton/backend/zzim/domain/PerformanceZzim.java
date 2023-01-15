package com.arton.backend.zzim.domain;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
}
