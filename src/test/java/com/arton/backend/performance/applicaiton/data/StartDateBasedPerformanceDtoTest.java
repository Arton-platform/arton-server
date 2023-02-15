package com.arton.backend.performance.applicaiton.data;

import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
class StartDateBasedPerformanceDtoTest {
    @Autowired
    PerformanceRepositoryPort performanceRepository;

    @Test
    void getDtoTest() {
        List<StartDateBasedPerformanceDto> startDateBasedPerformanceDtos = performanceRepository.findAll().stream().map(StartDateBasedPerformanceDto::domainToDto).collect(Collectors.toList());
        for (StartDateBasedPerformanceDto startDateBasedPerformanceDto : startDateBasedPerformanceDtos) {
            System.out.println("startDateBasedPerformanceDto = " + startDateBasedPerformanceDto);
        }
    }
}