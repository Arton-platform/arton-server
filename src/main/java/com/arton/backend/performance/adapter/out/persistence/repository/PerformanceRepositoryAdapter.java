package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV2;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV3;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper.toDomain;
import static com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryAdapter implements PerformanceRepositoryPort, PerformanceSavePort, PerformanceDeletePort {
    private final PerformanceRepository performanceRepository;

    @Override
    public List<Performance> findAll() {
        return Optional.ofNullable(performanceRepository.findAll())
                .orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllByLimit(int offset, int limit) {
        return Optional.ofNullable(performanceRepository.findAllByLimit(offset, limit))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllBySortAndPaging(Pageable pageable, String sort) {
        return performanceRepository.getPerformanceBySortAndPage(pageable, sort);
    }

    @Override
    public List<Performance> findAllPerformances() {
        return Optional.ofNullable(performanceRepository.findAll()).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllMusicals() {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.MUSICAL)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllMusicals(Pageable pageable) {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.MUSICAL, pageable)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllConcerts() {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.CONCERT)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllConcerts(Pageable pageable) {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.CONCERT, pageable)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findByIds(List<Long> ids) {
        return Optional.ofNullable(performanceRepository.findAllById(ids)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findByIds(List<Long> ids, int offset, int limit) {
        return Optional.ofNullable(performanceRepository.findZzimsByLimit(ids, offset, limit))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(PerformanceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Performance> findPopularPerformances(int offset, int limit) {
        return performanceRepository.getPopularPerformances(offset, limit);
    }

    @Override
    public List<Performance> findStartingSoonPerformances(int offset, int limit) {
        return performanceRepository.getPerformanceByStartDateASC(offset, limit);
    }

    @Override
    public List<Performance> findEndingSoonPerformances(int offset, int limit) {
        return performanceRepository.getPerformanceByEndDateASC(offset, limit);
    }

    @Override
    public Performance save(Performance performance) {
        return toDomain(performanceRepository.save(toEntity(performance)));
    }

    @Override
    public void deletePerformance(Performance performance) {
        performanceRepository.delete(toEntity(performance));
    }

    @Override
    public void deleteById(Long id) {
        performanceRepository.deleteById(id);
    }

    @Override
    public Optional<Performance> findById(Long id) {
        return performanceRepository.findById(id).map(PerformanceMapper::toDomain);
    }

    @Override
    public Optional<Performance> findByTitleAndStartDate(String title, LocalDateTime startDate) {
        return performanceRepository.findByTitleAndStartDateEquals(title, startDate).map(PerformanceMapper::toDomain);
    }

    @Override
    public boolean existByTitleAndStartDate(String title, LocalDateTime startDate) {
        return performanceRepository.existsByTitleAndStartDateEquals(title, startDate);
    }

    @Override
    public boolean existsById(Long id) {
        return performanceRepository.existsById(id);
    }

    @Override
    public Optional<Performance> findOne(Long id) {
        return performanceRepository.findById(id).map(PerformanceMapper::toDomain);
    }

    @Override
    public PerformanceDetailQueryDslDto getV2(Long id) {
        return performanceRepository.getPerformanceDetails(id);
    }

    @Override
    public PerformanceDetailQueryDslDtoV2 getOneWithArtistInfo(Long id) {
        return performanceRepository.getPerformanceDetailsV2(id);
    }

    @Override
    public PerformanceDetailQueryDslDtoV3 getOneWithArtistReviewInfo(Long userId, Long id) {
        return performanceRepository.getPerformanceDetailsV3(userId, id);
    }

    @Override
    public List<PerformanceDetailQueryDslDto> getAllRelatedInfosByType(Pageable pageable, PerformanceType performanceType) {
        return performanceRepository.getPerformanceDetailsByType(pageable, performanceType);
    }

    @Override
    public List<PerformanceDetailQueryDslDtoV2> getPerformanceWithArtistInfoByType(Pageable pageable, PerformanceType performanceType) {
        return performanceRepository.getPerformanceAllDetailsByType(pageable, performanceType);
    }

    @Override
    public void updatePerformanceStartScore(Long performanceId) {
        performanceRepository.updatePerformanceStarScore(performanceId);
    }
}
