package com.arton.backend.review.application.port.out;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewListPort {
    Optional<List<ReviewEntity<CommonResponse>>> reviewList(PerformanceEntity performanceEntity);
    Optional<List<ReviewEntity<CommonResponse>>> userReviewList(UserEntity userEntity);
}
