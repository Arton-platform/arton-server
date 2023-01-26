package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.domain.Follow;
import com.arton.backend.user.domain.User;

import java.util.List;

public interface FollowRepositoryPort {
    Long getFollowersCount(Long userId);
    Long getFollowingsCount(Long userId);
    Follow add(Follow follow);
    void delete(Follow follow);
    List<User> getFollowingList(Long userId);
    List<User> getFollowerList(Long userId);
}
