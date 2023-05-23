package com.arton.backend.follow.applicaion.port.in;

import com.arton.backend.follow.applicaion.data.FollowFeedRequestDto;
import com.arton.backend.follow.domain.FollowFeed;

public interface FollowFeedUseCase {
//    void myReviewLike(FollowFeed followFeed);
//
//    void myReviewCommented(FollowFeed followFeed);
//
//    void reviewFromFollow(FollowFeed followFeed);
//
//    void commentFromFollow(FollowFeed followFeed);
//
//    void followMe(FollowFeed followFeed);


    void myReviewLike(FollowFeedRequestDto followFeed);

    void myReviewCommented(FollowFeedRequestDto followFeed);

    void reviewFromFollow(FollowFeedRequestDto followFeed);

    void commentFromFollow(FollowFeedRequestDto followFeed);

    void followMe(FollowFeedRequestDto followFeed);
}
