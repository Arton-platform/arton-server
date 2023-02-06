package com.arton.backend.follow.applicaion.port.in;

import com.arton.backend.follow.applicaion.data.UserFollowDto;
import com.arton.backend.follow.applicaion.data.UserFollowSearchDto;

public interface FollowUseCase {
    UserFollowDto getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto);
    UserFollowDto getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto);
}
