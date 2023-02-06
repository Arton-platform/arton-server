package com.arton.backend.follow.domain;

import com.arton.backend.comment.domain.Comment;
import com.arton.backend.review.domain.Review;
import com.arton.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowFeed {
    private Long id;
    private User fromUser;
    private User toUser;
    private Review review;
    private Comment comment;
    private String feedContent;
}
