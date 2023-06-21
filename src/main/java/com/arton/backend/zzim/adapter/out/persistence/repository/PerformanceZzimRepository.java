package com.arton.backend.zzim.adapter.out.persistence.repository;

import com.arton.backend.zzim.adapter.out.persistence.entity.PerformanceZzimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 삭제시 어차피 FK 관계로 속하기 때문에 벌크 삭제해도 문제가 없음.
 */
public interface PerformanceZzimRepository extends JpaRepository<PerformanceZzimEntity, Long>, CustomPerformanceZzimRepository {
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM PerformanceZzimEntity pz WHERE pz.user.id = :id")
    void deleteAllByUserId(@Param("id") Long userId);
    List<PerformanceZzimEntity> findAllByUserId(Long userId);
    boolean existsByUserIdAndPerformanceId(Long userId, Long performanceId);
    void deleteByUserIdAndPerformanceId(Long userId, Long performanceId);
}
