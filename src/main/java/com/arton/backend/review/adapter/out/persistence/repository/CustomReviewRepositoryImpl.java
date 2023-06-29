package com.arton.backend.review.adapter.out.persistence.repository;


import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.CommonReviewQueryDslDto;
import com.arton.backend.review.application.data.QCommonReviewQueryDslDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.arton.backend.image.adapter.out.persistence.entity.QUserImageEntity.userImageEntity;
import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.review.adapter.out.persistence.entity.QReviewEntity.reviewEntity;
import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 대댓글 제외한 댓글 정보를 가져온다.
     * @param userId
     * @return
     */
    @Override
    public List<ReviewEntity> getUserReviewList(long userId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .leftJoin(reviewEntity.parent)
                .fetchJoin()
                .where(reviewEntity.user.id.eq(userId).or(reviewEntity.parent.user.id.eq(userId)))
                .orderBy(reviewEntity.parent.id.asc().nullsFirst(), reviewEntity.createdDate.asc())
                .fetch();
    }

    @Override
    public List<ReviewEntity> getAllReviews(long performanceId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .leftJoin(reviewEntity.parent)
                .fetchJoin()
                .where(reviewEntity.performance.id.eq(performanceId))
                .orderBy(reviewEntity.parent.id.asc().nullsFirst(), reviewEntity.createdDate.asc())
                .fetch();
    }

    @Override
    public List<ReviewEntity> getReviewChilds(long reviewId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .leftJoin(reviewEntity.parent)
                .fetchJoin()
                .where(reviewEntity.id.eq(reviewId).or(reviewEntity.parent.id.eq(reviewId)))
                .orderBy(reviewEntity.parent.id.asc().nullsFirst(), reviewEntity.createdDate.asc())
                .fetch();
    }
}
