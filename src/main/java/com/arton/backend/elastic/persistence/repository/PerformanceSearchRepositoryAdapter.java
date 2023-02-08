package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.PerformanceDocument;
import com.arton.backend.elastic.persistence.repository.PerformanceSearchRepository;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import com.arton.backend.performance.domain.PerformanceType;
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
    public List<PerformanceDocument> findByPlace(String place) {
        return performanceSearchRepository.findByPlace(place);
    }

    @Override
    public void saveAll(List<PerformanceDocument> performanceDocuments) {
        performanceSearchRepository.saveAll(performanceDocuments);
    }
}
