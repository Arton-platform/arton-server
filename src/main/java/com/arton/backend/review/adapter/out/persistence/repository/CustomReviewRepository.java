package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;

import java.util.List;

public interface CustomReviewRepository {
    /**
     * 유저 페이지에 제공하기 위한 메소드
     * 유저가 쓴 댓글 정보를 반환한다.
     * @param userId
     * @return
     */
    List<CommonReviewQueryDslDto> getUserReviewList(long userId);

    /**
     * 특정 공연의 모든 댓글 정보를 반환합니다.
     * @param performanceId
     * @return
     */
    List<ReviewEntity> getAllReviews(long performanceId);

    /**
     * 특정 리뷰의 대댓글을 반환합니다.
     * @param reviewId
     * @return
     */
    List<ReviewEntity> getReviewChilds(long reviewId);
}
