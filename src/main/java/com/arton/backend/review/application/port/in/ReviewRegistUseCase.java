package com.arton.backend.review.application.port.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.domain.Review;

public interface ReviewRegistUseCase {
    Review regist(Review review);
}
