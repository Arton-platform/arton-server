package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.domain.Performance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPerformanceRepositoryImpl implements CustomPerformanceRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Performance> getPerformanceByStartDateASC() {
        return null;
    }

    @Override
    public List<Performance> getPerformanceByEndDateASC() {
        return null;
    }

    @Override
    public List<Performance> getPopularPerformances() {
        return null;
    }
}
