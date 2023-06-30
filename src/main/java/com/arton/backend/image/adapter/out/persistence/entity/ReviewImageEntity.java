package com.arton.backend.image.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.review.adapter.out.persistence.entity.ReviewEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 이미지가 외래키를 갖어야함.
 * 만약 공연이 외래키를 갖는다면 데이터 정규성에 어울리지 않는듯함..
 */
@Entity
@Table(name = "Review_Image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    public void setReview(ReviewEntity review) {
        this.review = review;
    }

    @Builder
    public ReviewImageEntity(LocalDateTime createdDate, LocalDateTime updatedDate, Long id, String imageUrl, ReviewEntity review) {
        super(createdDate, updatedDate);
        this.id = id;
        this.imageUrl = imageUrl;
        this.review = review;
    }
}
