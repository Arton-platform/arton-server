package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.document.PerformanceDocument;
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
//        return performanceSearchRepository.findByTitle(title);
        return performanceSearchRepository.findByTitleContains(title);
    }

    @Override
    public List<PerformanceDocument> findByPerformanceType(PerformanceType performanceType) {
//        return performanceSearchRepository.findByPerformanceType(performanceType);
        return performanceSearchRepository.findPerformanceDocumentsByPerformanceType(performanceType);
    }

    @Override
    public List<PerformanceDocument> findByPlace(String place) {
        return performanceSearchRepository.findByPlaceContains(place);
    }

    @Override
    public List<PerformanceDocument> searchByCondition(PerformanceSearchDto performanceSearchDto) {
        return performanceSearchRepository.findByCondition(performanceSearchDto);
    }
}
