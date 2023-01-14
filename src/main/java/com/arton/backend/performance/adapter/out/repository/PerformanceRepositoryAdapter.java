package com.arton.backend.performance.adapter.out.repository;

import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryAdapter implements PerformanceRepositoryPort {
    private final PerformanceRepository performanceRepository;

    @Override
    public List<PerformanceEntity> findAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    public List<PerformanceEntity> findAllMusicals() {
        return performanceRepository.findAllByPerformanceType(PerformanceType.MUSICAL);
    }

    @Override
    public List<PerformanceEntity> findAllConcerts() {
        return performanceRepository.findAllByPerformanceType(PerformanceType.CONCERT);
    }

    @Override
    public List<PerformanceEntity> findByIds(List<Long> ids) {
        return performanceRepository.findAllById(ids);
    }

    @Override
    public PerformanceEntity save(PerformanceEntity performance) {
        return performanceRepository.save(performance);
    }

    @Override
    public void deletePerformance(PerformanceEntity performance) {
        performanceRepository.delete(performance);
    }

    @Override
    public void deleteById(Long id) {
        performanceRepository.deleteById(id);
    }
}
