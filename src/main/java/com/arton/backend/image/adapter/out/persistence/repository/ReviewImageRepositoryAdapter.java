package com.arton.backend.image.adapter.out.persistence.repository;

import com.arton.backend.image.adapter.out.persistence.entity.ReviewImageEntity;
import com.arton.backend.image.adapter.out.persistence.mapper.ReviewImageMapper;
import com.arton.backend.image.application.port.out.ReviewImageDeleteRepositoryPort;
import com.arton.backend.image.application.port.out.ReviewImageRepositoryPort;
import com.arton.backend.image.application.port.out.ReviewImageSaveRepositoryPort;
import com.arton.backend.image.domain.ReviewImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewImageRepositoryAdapter implements ReviewImageRepositoryPort, ReviewImageSaveRepositoryPort, ReviewImageDeleteRepositoryPort {
    private final ReviewImageRepository reviewImageRepository;


    @Override
    public void deleteReviewImages(Long reviewId) {
        reviewImageRepository.deleteAllByReviewId(reviewId);
    }

    @Override
    public void deleteById(Long id) {
        reviewImageRepository.deleteById(id);
    }

    @Override
    public Optional<ReviewImage> findById(Long id) {
        return reviewImageRepository.findById(id).map(ReviewImageMapper::toDomain);
    }

    @Override
    public List<ReviewImage> findByReviewId(Long reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId).stream().map(ReviewImageMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public ReviewImage save(ReviewImage reviewImage) {
        return ReviewImageMapper.toDomain(reviewImageRepository.save(ReviewImageMapper.toEntity(reviewImage)));
    }

    @Override
    public void saveAll(List<ReviewImage> reviewImages) {
        List<ReviewImageEntity> reviewImageEntities = reviewImages.stream().map(ReviewImageMapper::toEntity).collect(Collectors.toList());
        reviewImageRepository.saveAll(reviewImageEntities).stream().map(ReviewImageMapper::toDomain).collect(Collectors.toList());
    }
}
