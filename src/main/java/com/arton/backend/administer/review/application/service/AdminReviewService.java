package com.arton.backend.administer.review.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.review.application.port.in.AdminReviewUseCase;
import com.arton.backend.administer.review.application.port.out.AdminReviewPort;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import com.arton.backend.review.domain.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReviewService implements AdminReviewUseCase {

    private final AdminReviewPort adminReviewPort;
    private final ReviewMapper reviewMapper;

    @Override
    public List<Review> findReview() {
        return adminReviewPort.findReview()
                .orElseGet(ArrayList::new)
                .stream()
                .map(reviewMapper::toDomain)
                .collect(Collectors.toList());

    }

    @Override
    public Review findReviewById(Long id) {
        return reviewMapper.toDomain(
                adminReviewPort.findReviewById(id)
                        .orElseThrow(() -> new CustomException(
                                ErrorCode.SELECT_ERROR.getMessage(),
                                ErrorCode.SELECT_ERROR)));
    }

    @Override
    public void deleteReview(Review review) {
        adminReviewPort.deleteReview(
                reviewMapper.toEntity(review));
    }

}