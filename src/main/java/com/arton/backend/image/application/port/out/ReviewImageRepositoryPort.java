package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.ReviewImage;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepositoryPort {
    Optional<ReviewImage> findById(Long id);
    List<ReviewImage> findByReviewId(Long reviewId);
}
