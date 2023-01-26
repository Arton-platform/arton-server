package com.arton.backend.follow.adapter.out.repository;

import com.arton.backend.follow.domain.Follow;

public class FollowMapper {

    public static Follow toDomain(FollowEntity follow) {
        return Follow.builder()
                .createdDate(follow.getCreatedDate())
                .updateDate(follow.getUpdateDate())
                .fromUser(follow.getFromUser())
                .toUser(follow.getToUser())
                .build();
    }

    public static FollowEntity toEntity(Follow follow) {
        return FollowEntity.builder()
                .createdDate(follow.getCreatedDate())
                .updateDate(follow.getUpdateDate())
                .fromUser(follow.getFromUser())
                .toUser(follow.getToUser())
                .build();
    }
}
