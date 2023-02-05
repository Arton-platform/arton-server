package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.user.adapter.out.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PerformanceTicketOpenDateFromZzimDto {
    private PerformanceEntity performance;
    private UserEntity user;
}
