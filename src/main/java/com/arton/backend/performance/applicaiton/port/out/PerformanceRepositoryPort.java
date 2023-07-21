package com.arton.backend.performance.applicaiton.port.out;

import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV2;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDtoV3;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performance.domain.PerformanceType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PerformanceRepositoryPort {
    List<Performance> findAll();
    List<Performance> findAllByLimit(int offset, int limit);
    List<Performance> findAllBySortAndPaging(Pageable pageable, String sort);
    List<Performance> findAllPerformances();
    List<Performance> findAllMusicals();
    List<Performance> findAllMusicals(Pageable pageable);
    List<Performance> findAllConcerts();
    List<Performance> findAllConcerts(Pageable pageable);
    List<Performance> findByIds(List<Long> ids);
    List<Performance> findByIds(List<Long> ids, int offset, int limit);
    List<Performance> findPopularPerformances(int offset, int limit);
    List<Performance> findStartingSoonPerformances(int offset, int limit);
    List<Performance> findEndingSoonPerformances(int offset, int limit);
    Optional<Performance> findById(Long id);
    Optional<Performance> findByTitleAndStartDate(String title, LocalDateTime startDate);
    boolean existByTitleAndStartDate(String title, LocalDateTime startDate);
    boolean existsById(Long id);
    Optional<Performance> findOne(Long id);
    PerformanceDetailQueryDslDto getV2(Long id);
    /**
     * 아티스트 정보도 같이 가져오기
     * @param id
     * @return
     */
    PerformanceDetailQueryDslDtoV2 getOneWithArtistInfo(Long id);
    PerformanceDetailQueryDslDtoV3 getOneWithArtistReviewInfo(Long userId, Long id);
    List<PerformanceDetailQueryDslDto> getAllRelatedInfosByType(Pageable pageable, PerformanceType performanceType);
    List<PerformanceDetailQueryDslDtoV2> getPerformanceWithArtistInfoByType(Pageable pageable, PerformanceType performanceType);

    void updatePerformanceStartScore(Long performanceId);
}
