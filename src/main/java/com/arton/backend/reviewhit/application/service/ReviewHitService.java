package com.arton.backend.reviewhit.application.service;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.reviewhit.application.port.in.ReviewHitCreateUseCase;
import com.arton.backend.reviewhit.application.port.in.ReviewHitDecreaseUseCase;
import com.arton.backend.reviewhit.application.port.out.ReviewHitDeletePort;
import com.arton.backend.reviewhit.application.port.out.ReviewHitFindPort;
import com.arton.backend.reviewhit.application.port.out.ReviewHitSavePort;
import com.arton.backend.reviewhit.domain.ReviewHit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewHitService implements ReviewHitDecreaseUseCase, ReviewHitCreateUseCase {
    private final ReviewHitFindPort reviewHitFindPort;
    private final ReviewHitDeletePort reviewHitDeletePort;
    private final ReviewHitSavePort reviewHitSavePort;

    @Override
    public void addHit(long userId, long reviewId) {
        // 이미 등록했다면 기각
        boolean isExist = reviewHitFindPort.existByUserAndReview(userId, reviewId);
        if (isExist) {
            throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
        }
        // 아니면 등록
        reviewHitSavePort.save(ReviewHit.builder().userId(userId).reviewId(reviewId).build());
    }

    @Override
    public void removeHit(long userId, long reviewId) {
        // 등록했는지 체크
        boolean isExist = reviewHitFindPort.existByUserAndReview(userId, reviewId);
        if (!isExist) {
            throw new CustomException(ErrorCode.BAD_REQUEST.getMessage(), ErrorCode.BAD_REQUEST);
        }
        // 제거
        reviewHitDeletePort.deleteByUserAndReview(userId, reviewId);
    }
}
