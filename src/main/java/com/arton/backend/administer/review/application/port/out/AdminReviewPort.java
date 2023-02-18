package com.arton.backend.administer.review.application.port.out;

import java.util.List;
import java.util.Optional;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;

public interface AdminReviewPort {

    Optional<List<ReviewEntity<CommonResponse>>> findReview();

    Optional<ReviewEntity<CommonResponse>> findReviewById(Long id);

    void deleteReview(ReviewEntity<CommonResponse> review);

}