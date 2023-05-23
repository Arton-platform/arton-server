package com.arton.backend.review.application.port.out;

import com.arton.backend.review.domain.Review;

import java.util.Optional;

public interface ReviewFindPort {
    Optional<Review> findById(Long id);
}
