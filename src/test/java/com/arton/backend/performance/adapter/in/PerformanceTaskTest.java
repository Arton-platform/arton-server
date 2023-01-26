package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepositoryAdapter;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.applicaiton.service.PerformanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class PerformanceTaskTest {

    @Autowired
    PerformanceTask performanceTask;

    @Test
    @Transactional
    void testPerformanceSchedule() {
        performanceTask.performanceSchedule();
//        performanceUseCase.getAllPerformances().stream().forEach(performance -> System.out.println(performance));
//        performanceUseCase.getAllPerformances();
//        service.getAllPerformances();
        performanceTask.performanceSchedule();
//        repository.findAll().stream().forEach(performanceEntity -> System.out.println(performanceEntity.toString()));

    }
}
