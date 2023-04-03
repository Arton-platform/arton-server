package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentDeletePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper.toDocumentFromDomain;

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
    public List<PerformanceDocument> findByDtoInAdminWithoutPaging(PerformanceAdminSearchDto searchDto) {
        return performanceSearchRepository.findByDtoInAdminWithoutPaging(searchDto);
    }

    @Override
    public void saveAll(List<Performance> performances) {
        List<PerformanceDocument> performanceDocuments = performances.stream().map(PerformanceMapper::toDocumentFromDomain).collect(Collectors.toList());
        performanceSearchRepository.saveAll(performanceDocuments);
    }

    @Override
    public void save(Performance performance) {
        performanceSearchRepository.save(toDocumentFromDomain(performance));
    }

    @Override
    public void deleteById(Long id) {
        performanceSearchRepository.deleteById(id);
    }

    @Override
    public Optional<PerformanceDocument> findById(Long id) {
        return performanceSearchRepository.findById(id);
    }

    @Override
    public Page<PerformanceDocument> findAll(Pageable pageable) {
        return performanceSearchRepository.findAllByOrderById(pageable);
    }
}
