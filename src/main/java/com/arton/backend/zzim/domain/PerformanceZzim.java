package com.arton.backend.zzim.domain;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class PerformanceZzim {
    private Long id;
    private PerformanceEntity performance;
    private UserEntity user;
}
