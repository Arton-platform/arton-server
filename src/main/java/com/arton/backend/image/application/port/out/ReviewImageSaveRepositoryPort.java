package com.arton.backend.image.application.port.out;

import com.arton.backend.image.domain.ReviewImage;

import java.util.List;

public interface ReviewImageSaveRepositoryPort {
    ReviewImage save(ReviewImage reviewImage);
    void saveAll(List<ReviewImage> reviewImages);
}
