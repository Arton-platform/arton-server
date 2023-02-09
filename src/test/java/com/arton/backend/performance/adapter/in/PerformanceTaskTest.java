package com.arton.backend.performance.adapter.in;

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
    }
}
