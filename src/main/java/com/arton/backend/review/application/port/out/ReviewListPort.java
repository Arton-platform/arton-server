package com.arton.backend.review.application.port.out;

import com.arton.backend.performance.domain.Performance;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewListPort {
    Optional<List<ReviewEntity>> reviewList(Performance performance);
}
