package com.arton.backend.follow.applicaion.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowFeedRequestDto {
    private Long fromUserId;
    private Long toUserId;
    private Long reviewId;
    private Long commentId;

    @Builder
    public FollowFeedRequestDto(Long fromUserId, Long toUserId, Long reviewId, Long commentId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.reviewId = reviewId;
        this.commentId = commentId;
    }
}
