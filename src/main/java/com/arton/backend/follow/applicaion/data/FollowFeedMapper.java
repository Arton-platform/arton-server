package com.arton.backend.follow.applicaion.data;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.domain.FollowFeed;
import org.springframework.stereotype.Component;

@Component
public class FollowFeedMapper {

    public FollowFeed toDomain(FollowFeed followFeed, String feedContent){
        return FollowFeed.builder()
                .fromUser(followFeed.getFromUser())
                .toUser(followFeed.getToUser())
                .reviewId(followFeed.getReviewId())
                .commentId(followFeed.getCommentId())
                .feedContent(feedContent)
                .build();
    }

    public FollowFeedEntity toEntity(FollowFeed followFeed, String feedContent){
        return FollowFeedEntity.builder()
                .fromUser(followFeed.getFromUser())
                .toUser(followFeed.getToUser())
                .reviewId(followFeed.getReviewId())
                .commentId(followFeed.getCommentId())
                .feedContent(feedContent)
                .build();
    }
}
