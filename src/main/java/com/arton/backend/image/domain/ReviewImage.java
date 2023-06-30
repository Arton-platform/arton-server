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
    private Review review;
    /** 등록일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;

    @Builder
    public ReviewImage(Long id, String imageUrl, Review review, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.review = review;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
