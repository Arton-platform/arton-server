package com.arton.backend.follow.applicaion.port.in;

import com.arton.backend.follow.domain.Follow;

public interface FollowRegisterUseCase {
    Follow followUser(Long fromUser, PostFollowDto postFollowDto);
}
