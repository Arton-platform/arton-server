package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.data.PerformanceAdminSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentDeletePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceSearchRepositoryAdapter implements PerformanceDocumentSearchPort, PerformanceDocumentDeletePort, PerformanceDocuemntSavePort {
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
        return performanceSearchRepository.findByDtoInAdmin(searchDto, pageable);
    }

    @Override
    public void saveAll(List<PerformanceDocument> performanceDocuments) {
        performanceSearchRepository.saveAll(performanceDocuments);
    }

    @Override
    public void save(PerformanceDocument performanceDocument) {
        performanceSearchRepository.save(performanceDocument);
    }

    @Override
    public void deleteById(Long id) {
        performanceSearchRepository.deleteById(id);
    }
}
