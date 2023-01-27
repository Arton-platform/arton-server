package com.arton.backend.follow.applicaion.port.in;

public interface FollowUseCase {
    UserFollowDto getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto);
    UserFollowDto getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto);
    Long removeFollower(Long toUser, Long fromUser);
    Long unfollow(Long fromUser, Long toUser);
}
