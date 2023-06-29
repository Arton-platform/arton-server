package com.arton.backend.review.adapter.out.persistence.repository;


import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import com.arton.backend.review.application.data.MyPageReviewQueryDSLDto;
import com.arton.backend.review.application.data.QMyPageReviewQueryDSLDto;
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

    @Override
    public List<MyPageReviewQueryDSLDto> getUserReviewList(long userId) {
        return jpaQueryFactory.select(new QMyPageReviewQueryDSLDto(
                reviewEntity.id,
                performanceEntity.id,
                userEntity.id,
                userImageEntity.imageUrl,
                userEntity.nickname,
                performanceEntity.title,
                reviewEntity.starScore,
                reviewEntity.createdDate,
                reviewEntity.content,
                reviewEntity.hit))
                .from(reviewEntity)
                .leftJoin(performanceEntity).on(performanceEntity.eq(reviewEntity.performance))
                .leftJoin(userEntity).on(userEntity.eq(reviewEntity.user))
                .leftJoin(userImageEntity).on(userEntity.eq(userImageEntity.user))
                .fetchJoin()
                .where(reviewEntity.user.id.eq(userId))
                .groupBy(reviewEntity.id,
                        performanceEntity.id,
                        userEntity.id,
                        userImageEntity.imageUrl,
                        userEntity.nickname,
                        performanceEntity.title,
                        reviewEntity.starScore,
                        reviewEntity.createdDate,
                        reviewEntity.content,
                        reviewEntity.hit)
                .having(reviewEntity.parent.isNull())
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
