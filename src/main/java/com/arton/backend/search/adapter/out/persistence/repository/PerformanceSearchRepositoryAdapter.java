package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.data.PerformanceAdminSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSearchRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceSearchRepositoryAdapter implements PerformanceSearchRepositoryPort {
    private final PerformanceSearchRepository performanceSearchRepository;

    @Override
    public SearchPage<PerformanceDocument> findByTitle(String title, String sort, Pageable pageable) {
        return performanceSearchRepository.findByTitle(title, sort, pageable);
    }

    @Override
    public SearchPage<PerformanceDocument> findByPerformanceType(String type, String sort, Pageable pageable) {
        return performanceSearchRepository.findByType(type, sort, pageable);
    }

    @Override
    public SearchPage<PerformanceDocument> findByPlace(String place, String sort, Pageable pageable) {
        return performanceSearchRepository.findByPlace(place, sort, pageable);
    }

    @Override
    public SearchPage<PerformanceDocument> findByKeyword(String keyword, String sort, Pageable pageable) {
        return performanceSearchRepository.findByKeyword(keyword, sort, pageable);
    }

    @Override
    public SearchPage<PerformanceDocument> findByDtoInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public void saveAll(List<PerformanceDocument> performanceDocuments) {
        performanceSearchRepository.saveAll(performanceDocuments);
    }

    @Override
    public void save(PerformanceDocument performanceDocument) {
        performanceSearchRepository.save(performanceDocument);
    }
}
