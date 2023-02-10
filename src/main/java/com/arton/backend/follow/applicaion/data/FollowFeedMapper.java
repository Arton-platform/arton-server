package com.arton.backend.follow.applicaion.data;

import com.arton.backend.comment.adapter.out.persistence.CommentMapper;
import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.domain.FollowFeed;
import com.arton.backend.review.adapter.out.persistence.ReviewMapper;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowFeedMapper {
    private final CommentMapper commentMapper;
    private final ReviewMapper reviewMapper;

    public FollowFeed toDomain(FollowFeedEntity followFeed, String feedContent){
        return FollowFeed.builder()
                .fromUser(UserMapper.toDomain(followFeed.getFromUser()))
                .toUser(UserMapper.toDomain(followFeed.getToUser()))
                .review(reviewMapper.toDomain(followFeed.getReview()))
                .comment(commentMapper.toDomain(followFeed.getComment()))
                .feedContent(feedContent)
                .build();
    }

    public FollowFeedEntity toEntity(FollowFeed followFeed, String feedContent){
        return FollowFeedEntity.builder()
                .fromUser(UserMapper.toEntity(followFeed.getFromUser()))
                .toUser(UserMapper.toEntity(followFeed.getToUser()))
                .review(reviewMapper.toEntity(followFeed.getReview()))
                .comment(commentMapper.toEntity(followFeed.getComment()))
                .feedContent(feedContent)
                .build();
    }
}
