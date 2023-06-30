package com.arton.backend.image.domain;

import com.arton.backend.review.domain.Review;
import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"review"})
public class ReviewImage {
    private Long id;
    private String imageUrl;
    private Long reviewId;
    /** 등록일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;

    @Builder
    public ReviewImage(Long id, String imageUrl, Long reviewId, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.reviewId = reviewId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setReview(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
