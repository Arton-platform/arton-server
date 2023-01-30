package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.image.application.port.out.PerformanceImageRepositoryPort;
import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PerformanceImageRepositoryAdapterTest {
    @Autowired
    PerformanceImageSaveRepositoryPort performanceImageSaveRepositoryPort;
    @Autowired
    PerformanceImageRepositoryPort performanceImageRepositoryPort;
    @Autowired
    PerformanceRepositoryPort performanceRepositoryPort;

    @Transactional
    @Test
    void performanceImageSaveTest() {
        Performance test = Performance.builder().title("test").build();
        Performance saved = performanceRepositoryPort.save(test);

        List<PerformanceImage> images = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PerformanceImage build = PerformanceImage.builder()
                    .imageUrl("default.image")
                    .performance(saved)
                    .build();
            images.add(build);
        }
        performanceImageSaveRepositoryPort.saveAll(images);

        List<PerformanceImage> byPerformanceId = performanceImageRepositoryPort.findByPerformanceId(saved.getPerformanceId());
        for (PerformanceImage performanceImage : byPerformanceId) {
            System.out.println("performanceImage = " + performanceImage);
        }

        Performance performanceEntity = performanceRepositoryPort.findOne(saved.getPerformanceId()).get();
        System.out.println("performanceEntity = " + performanceEntity);
    }
}