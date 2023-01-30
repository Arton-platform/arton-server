package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.image.domain.UserImage;

import java.util.List;
import java.util.Optional;

public interface PerformanceImageRepositoryPort {
    Optional<PerformanceImage> findById(Long id);
    List<PerformanceImage> findByPerformanceId(Long id);
}
