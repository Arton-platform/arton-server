package com.arton.backend.reviewhit.adapter.out.persistence.repository;

import com.arton.backend.reviewhit.adapter.out.persistence.mapper.ReviewHitMapper;
import com.arton.backend.reviewhit.application.port.out.ReviewHitDeletePort;
import com.arton.backend.reviewhit.application.port.out.ReviewHitFindPort;
import com.arton.backend.reviewhit.application.port.out.ReviewHitSavePort;
import com.arton.backend.reviewhit.domain.ReviewHit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.arton.backend.reviewhit.adapter.out.persistence.mapper.ReviewHitMapper.*;

@Repository
@RequiredArgsConstructor
public class ReviewHitRepositoryAdapter implements ReviewHitFindPort, ReviewHitDeletePort, ReviewHitSavePort {

    private final ReviewHitRepository reviewHitRepository;

    @Override
    public void deleteByUserAndReview(Long userId, Long reviewId) {
        reviewHitRepository.deleteByUserIdAndReviewId(userId, reviewId);
    }

    @Override
    public Optional<ReviewHit> findByUserAndReview(Long userId, Long reviewId) {
        return reviewHitRepository.findByUserIdAndReviewId(userId, reviewId).map(ReviewHitMapper::toDomain);
    }

    @Override
    public ReviewHit save(ReviewHit reviewHit) {
        return toDomain(reviewHitRepository.save(toEntity(reviewHit)));
    }
}
