package com.arton.backend.review.adapter.out.persistence.repository;


import com.arton.backend.review.application.data.MyPageReviewQueryDSLDto;
import com.arton.backend.review.application.data.QMyPageReviewQueryDSLDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.arton.backend.performance.adapter.out.persistence.entity.QPerformanceEntity.performanceEntity;
import static com.arton.backend.review.adapter.out.persistence.entity.QReviewEntity.reviewEntity;
import static com.arton.backend.user.adapter.out.persistence.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MyPageReviewQueryDSLDto> getReviewDetail(long userId) {
        return jpaQueryFactory.select(new QMyPageReviewQueryDSLDto(
                reviewEntity.id,
                performanceEntity.id,
                userEntity.id,
                userEntity.nickname,
                performanceEntity.title,
                reviewEntity.starScore,
                reviewEntity.createdDate,
                reviewEntity.content,
                reviewEntity.hit))
                .from(reviewEntity)
                .leftJoin(performanceEntity).on(performanceEntity.eq(reviewEntity.performance))
                .leftJoin(userEntity).on(userEntity.eq(reviewEntity.user))
                .fetchJoin()
                .where(reviewEntity.user.id.eq(userId))
                .groupBy(reviewEntity.id,
                        performanceEntity.id,
                        userEntity.id,
                        userEntity.nickname,
                        performanceEntity.title,
                        reviewEntity.starScore,
                        reviewEntity.createdDate,
                        reviewEntity.content,
                        reviewEntity.hit)
                .fetch();
    }
}
