package com.arton.backend.administer.review.application.port.in;

import java.util.List;

import com.arton.backend.review.domain.Review;

public interface AdminReviewUseCase {

    List<Review> findReview();

    Review findReviewById(Long id);

    void deleteReview(Review review);

}
