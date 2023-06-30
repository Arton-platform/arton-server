package com.arton.backend.image.application.port.out;

public interface ReviewImageDeleteRepositoryPort {
    void deleteReviewImages(Long reviewId);
    void deleteById(Long id);
}
