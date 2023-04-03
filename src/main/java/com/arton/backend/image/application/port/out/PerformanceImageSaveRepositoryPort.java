package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.PerformanceImage;

import java.util.List;

public interface PerformanceImageSaveRepositoryPort {
    PerformanceImage save(PerformanceImage performanceImage);
    void saveAll(List<PerformanceImage> performanceImageList);
}
