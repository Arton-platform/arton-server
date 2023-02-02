package com.arton.backend.follow.adapter.out.repository;

import com.arton.backend.follow.applicaion.data.UserFollowSearchDto;
import com.arton.backend.user.adapter.out.repository.UserEntity;

import java.util.List;

public interface CustomFollowRepository {
    List<UserEntity> getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto);
    List<UserEntity> getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto);
}
