package com.arton.backend.performance.adapter.out.persistence.repository;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV2;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV3;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPerformanceRepository {
    List<Performance> getPerformanceByStartDateASC(int offset, int limit);
    List<Performance> getPerformanceByEndDateASC(int offset, int limit);
    List<Performance> getPopularPerformances(int offset, int limit);
    List<PerformanceEntity> findAllByLimit(int offset, int limit);
    List<PerformanceEntity> findZzimsByLimit(List<Long> ids, int offset, int limit);
    PerformanceDetailQueryDslDto getPerformanceDetails(Long id);
    PerformanceDetailQueryDslDtoV2 getPerformanceDetailsV2(Long id);
    PerformanceDetailQueryDslDtoV3 getPerformanceDetailsV3(Long userId, Long id);
    List<PerformanceDetailQueryDslDto> getPerformanceDetailsByType(Pageable pageable, PerformanceType performanceType);

    /**
     * 아티스트 정보까지 포함해서 가져오기
     * @param pageable
     * @param performanceType
     * @return
     */
    List<PerformanceDetailQueryDslDtoV2> getPerformanceAllDetailsByType(Pageable pageable, PerformanceType performanceType);
}
