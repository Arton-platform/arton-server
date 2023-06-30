package com.arton.backend.review.application.port.out;

import com.arton.backend.review.domain.Review;

public interface ReviewRegistPort {
    Review regist(Review review);
}
