package com.arton.backend.follow.adapter.out.persistence.repository;

import com.arton.backend.follow.applicaion.data.UserFollowSearchDto;
import com.arton.backend.follow.applicaion.data.UserShortQueryDSLDto;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;

import java.util.List;

public interface CustomFollowRepository {
    List<UserEntity> getFollowers(Long userId, UserFollowSearchDto userFollowSearchDto);
    List<UserShortQueryDSLDto> getFollowersV2(Long userId, UserFollowSearchDto userFollowSearchDto);
    List<UserEntity> getFollowings(Long userId, UserFollowSearchDto userFollowSearchDto);
    List<UserShortQueryDSLDto> getFollowingsV2(Long userId, UserFollowSearchDto userFollowSearchDto);
}
