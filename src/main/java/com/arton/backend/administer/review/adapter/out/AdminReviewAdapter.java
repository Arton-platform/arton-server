package com.arton.backend.administer.review.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.arton.backend.administer.review.application.port.out.AdminReviewPort;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminReviewAdapter implements AdminReviewPort {

    private final ReviewRepository repository;

    @Override
    public Optional<List<ReviewEntity<CommonResponse>>> findReview() {
        return Optional.ofNullable(repository.findAll());
    }

    @Override
    public Optional<ReviewEntity<CommonResponse>> findReviewById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteReview(ReviewEntity<CommonResponse> review) {
        repository.delete(review);
    }

}