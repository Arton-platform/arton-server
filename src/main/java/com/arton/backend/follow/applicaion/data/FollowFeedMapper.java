package com.arton.backend.follow.applicaion.data;

import com.arton.backend.comment.adapter.out.persistence.CommentMapper;
import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.domain.FollowFeed;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowFeedMapper {
    private final CommentMapper commentMapper;
    private final ReviewMapper reviewMapper;

    public FollowFeed toDomain(FollowFeedEntity followFeed, String feedContent){
        return FollowFeed.builder()
                .fromUser(followFeed.getFromUser())
                .toUser(followFeed.getToUser())
                .review(reviewMapper.toDomain(followFeed.getReview()))
                .comment(commentMapper.toDomain(followFeed.getComment()))
                .feedContent(feedContent)
                .build();
    }

    public FollowFeedEntity toEntity(FollowFeed followFeed, String feedContent){
        return FollowFeedEntity.builder()
                .fromUser(followFeed.getFromUser())
                .toUser(followFeed.getToUser())
                .review(reviewMapper.toEntity(followFeed.getReview()))
                .comment(commentMapper.toEntity(followFeed.getComment()))
                .feedContent(feedContent)
                .build();
    }
}
