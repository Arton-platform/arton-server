package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.image.domain.UserImage;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserMapper;

/**
 * 자식 먼저 저장해야하므로
 * user가 있으면 안됨.
 */
public class UserImageMapper {
    public static UserImage toDomain(UserImageEntity userImage) {
        return UserImage.builder()
                .id(userImage.getId())
                .user(UserMapper.toDomain(userImage.getUser()))
                .imageUrl(userImage.getImageUrl())
                .createdDate(userImage.getCreatedDate())
                .updateDate(userImage.getUpdateDate())
                .build();
    }

    public static UserImageEntity toEntity(UserImage userImage) {
        return UserImageEntity.builder()
                .id(userImage.getId())
                .user(UserEntity.builder().id(userImage.getUser().getId()).build())
                .imageUrl(userImage.getImageUrl())
                .createdDate(userImage.getCreatedDate())
                .updateDate(userImage.getUpdateDate())
                .build();
    }
}
