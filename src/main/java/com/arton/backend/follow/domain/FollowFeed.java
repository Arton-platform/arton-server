package com.arton.backend.follow.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowFeed {
    private Long id;
    private Long fromUser;
    private Long toUser;
    private Long reviewId;
    private Long commentId;
    private String feedContent;
}
