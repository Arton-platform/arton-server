package com.arton.backend.follow.applicaion.port.in;

public interface UnFollowUseCase {
    Long removeFollower(Long toUser, Long fromUser);
    Long unfollow(Long fromUser, Long toUser);
}
