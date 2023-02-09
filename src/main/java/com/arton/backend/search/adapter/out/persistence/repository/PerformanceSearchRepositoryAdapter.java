package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceSearchRepositoryAdapter implements PerformanceSearchRepositoryPort {
    private final PerformanceSearchRepository performanceSearchRepository;

    @Override
    public List<PerformanceDocument> findByTitle(String title) {
        return performanceSearchRepository.findByTitle(title);
    }

    @Override
    public List<PerformanceDocument> findByPerformanceType(String type) {
        return performanceSearchRepository.findByType(type);
    }

    @Override
    public List<PerformanceDocument> findByPlace(String place, String sort) {
        return performanceSearchRepository.findByPlace(place, sort);
    }

    @Override
    public void saveAll(List<PerformanceDocument> performanceDocuments) {
        performanceSearchRepository.saveAll(performanceDocuments);
    }
}
