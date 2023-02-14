package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper;
import com.arton.backend.performance.applicaiton.port.out.PerformanceDeletePort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.performance.adapter.out.persistence.mapper.PerformanceMapper.*;

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
    public List<Performance> findAllPerformances() {
        return Optional.ofNullable(performanceRepository.findAll()).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllMusicals() {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.MUSICAL)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllConcerts() {
        return Optional.ofNullable(performanceRepository.findAllByPerformanceType(PerformanceType.CONCERT)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Performance> findByIds(List<Long> ids) {
        return Optional.ofNullable(performanceRepository.findAllById(ids)).orElseGet(Collections::emptyList).stream().map(PerformanceMapper::toDomain).collect(Collectors.toList());
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
    public Optional<PerformanceEntity> findById(Long id) {
        return performanceRepository.findById(id);
    }

    @Override
    public Optional<Performance> findOne(Long id) {
        return performanceRepository.findById(id).map(PerformanceMapper::toDomain);
    }
}
