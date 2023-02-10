package com.arton.backend.follow.applicaion.service;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowFeedEntity;
import com.arton.backend.follow.applicaion.data.FollowFeedMapper;
import com.arton.backend.follow.applicaion.port.out.FollowFeedPort;
import com.arton.backend.follow.applicaion.port.in.FollowFeedUseCase;
import com.arton.backend.follow.domain.FeedType;
import com.arton.backend.follow.domain.FollowFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class FollowFeedService implements FollowFeedUseCase {

    private final FollowFeedPort followFeedPort;
    private final FollowFeedMapper followFeedMapper;
    /**
     * @param followFeed
     * @Desc 내 리뷰에 좋아요를 눌렸을때
     */
    @Override
    public void myReviewLike(FollowFeed followFeed){
        String feedContent = followFeed.getFromUser() + " " + FeedType.LIKE_MY_REVIEW;
        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
     * @Desc 내 리뷰에 댓글이 달렸을때
     */
    @Override
    public void myReviewCommented(FollowFeed followFeed){
        String feedContent = followFeed.getFromUser() + " " + FeedType.COMMENT_MY_REVIEW;
        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 팔로우 한사람이 리뷰를 남겼을때
    */
    @Override
    public void reviewFromFollow(FollowFeed followFeed){
        String feedContent = followFeed.getFromUser() + " " +  FeedType.FOLLOW_REVIEW_REGIST;
        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 팔로우 한사람이 댓글을 남겼을때
    */
    @Override
    public void commentFromFollow(FollowFeed followFeed){
        String feedContent = followFeed.getFromUser() + " " + FeedType.FOLLOW_COMMENT_REGIST;
        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }

    /**
    * @Desc 다른사람이 나를 팔로우 했을때
    */
    @Override
    public void followMe(FollowFeed followFeed){
        String feedContent = followFeed.getFromUser() + " " + FeedType.FOLLOW_ME;
        FollowFeedEntity entity = followFeedMapper.toEntity(followFeed, feedContent);
        followFeedPort.save(entity);
        //TODO:
        // Alert User
    }
}
