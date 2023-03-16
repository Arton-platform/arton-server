package com.arton.backend.review.application.port.in;

import com.arton.backend.review.domain.Review;

public interface ReviewRegistUseCase {
    Review regist(Review review);
}
