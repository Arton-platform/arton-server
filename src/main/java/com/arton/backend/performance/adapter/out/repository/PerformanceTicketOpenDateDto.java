package com.arton.backend.performance.adapter.out.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceTicketOpenDateDto {
    private long id;
    private LocalDateTime ticketOpenDate;
}
