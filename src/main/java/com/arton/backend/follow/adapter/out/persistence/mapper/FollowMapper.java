package com.arton.backend.follow.adapter.out.persistence.mapper;

import com.arton.backend.follow.adapter.out.persistence.entity.FollowEntity;
import com.arton.backend.follow.domain.Follow;

public class FollowMapper {

    public static Follow toDomain(FollowEntity follow) {
        return Follow.builder()
                .createdDate(follow.getCreatedDate())
                .updateDate(follow.getUpdatedDate())
                .fromUser(follow.getFromUser())
                .toUser(follow.getToUser())
                .build();
    }

    public static FollowEntity toEntity(Follow follow) {
        return FollowEntity.builder()
                .createdDate(follow.getCreatedDate())
                .updatedDate(follow.getUpdateDate())
                .fromUser(follow.getFromUser())
                .toUser(follow.getToUser())
                .build();
    }
}
