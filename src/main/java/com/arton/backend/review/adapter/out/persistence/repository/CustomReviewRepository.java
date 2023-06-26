package com.arton.backend.review.adapter.out.persistence.repository;

import com.arton.backend.review.application.data.MyPageReviewQueryDSLDto;

import java.util.List;

public interface CustomReviewRepository {
    List<MyPageReviewQueryDSLDto> getReviewDetail(long userId);
}
