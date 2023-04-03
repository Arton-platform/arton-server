package com.arton.backend.price.application.port.out;

import com.arton.backend.price.domain.PriceGrade;

import java.util.List;
import java.util.Optional;

public interface PriceGradeRepositoryPort {
    List<PriceGrade> findByPerformanceId(Long performanceId);
    Optional<PriceGrade> findById(Long id);
}
