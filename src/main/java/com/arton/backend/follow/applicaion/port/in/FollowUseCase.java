package com.arton.backend.follow.applicaion.port.in;

public interface FollowUseCase {
    UserFollowDto getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto);
    UserFollowDto getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto);
}
