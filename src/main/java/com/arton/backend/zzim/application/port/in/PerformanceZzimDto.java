package com.arton.backend.zzim.application.port.in;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.*;

/**
 * performance, user -> performanceZzim
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PerformanceZzimDto {
    private PerformanceEntity performance;
    private User user;

    public static PerformanceZzim of(PerformanceEntity performance, User user) {
        return PerformanceZzim.builder()
                .performance(performance)
                .user(user)
                .build();
    }
}
