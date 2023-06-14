package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PerformanceRepositoryAdapterTest {

    @Autowired
    private PerformanceRepositoryPort performanceRepositoryPort;


    @Test
    void findByTitleAndDateTest() {
        Assertions.assertThrows(CustomException.class, () -> performanceRepositoryPort.findByTitleAndStartDate("aa", LocalDateTime.now()).orElseThrow(() -> new CustomException(ErrorCode.PERFORMANCE_NOT_FOUND.getMessage(), ErrorCode.PERFORMANCE_NOT_FOUND)));
    }

}