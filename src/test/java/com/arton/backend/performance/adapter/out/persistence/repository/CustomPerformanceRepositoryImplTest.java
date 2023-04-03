package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@SpringBootTest
class CustomPerformanceRepositoryImplTest {
    @Autowired
    PerformanceRepository performanceRepository;

    /**
     * getPerformanceByStartDateASC 기능 테스트
     */
    @Test
    void getPerformanceByStartDateASCTest() {
        List<Performance> performanceByStartDateASC = performanceRepository.getPerformanceByStartDateASC(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        List<PerformanceEntity> total = performanceRepository.findAll().stream().collect(Collectors.toList());
        if (performanceByStartDateASC != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performanceByStartDateASC.size());
            System.out.println("total = " + total.size());
            Assertions.assertThat(performanceByStartDateASC.size()).isEqualTo(collect.size());
        }
    }

    @Test
    void getPerformanceByEndDateASCTest() {
        List<Performance> performances = performanceRepository.getPerformanceByEndDateASC(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        if (performances != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performances.size());
            int idx = 1;
            for (Performance performance : performances) {
                System.out.println("performance " + (idx++) +" = " + performance);
            }
            Assertions.assertThat(performances.size()).isEqualTo(collect.size());
        }
    }

    @Test
    void getPopularPerformanceTest() {
        List<Performance> performances = performanceRepository.getPopularPerformances(0, 10);
        List<PerformanceEntity> collect = performanceRepository.findAll().stream().filter(performanceEntity -> performanceEntity.getStartDate().isAfter(LocalDateTime.now())).collect(Collectors.toList());
        if (performances != null && collect != null) {
            System.out.println("collect = " + collect.size());
            System.out.println("performanceByStartDateASC = " + performances.size());
            int idx = 1;
            for (Performance performance : performances) {
                System.out.println("performance " + (idx++) +" = " + performance);
            }
            Assertions.assertThat(performances.size()).isEqualTo(collect.size());
        }
    }

}