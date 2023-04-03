package com.arton.backend.image.application.port.out;

public interface PerformanceImageDeleteRepositoryPort {
    void deletePerformanceImages(Long performanceId);
    void deleteById(Long id);
}
