package com.arton.backend.image.adapter.out.persistence.mapper;

import com.arton.backend.image.adapter.out.persistence.entity.UserImageEntity;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import org.springframework.util.ObjectUtils;

/**
 * 자식 먼저 저장해야하므로
 * user가 있으면 안됨.
 */
public class UserImageMapper {
    public static UserImage toDomain(UserImageEntity userImage) {
        return UserImage.builder()
                .id(userImage.getId())
                .user(!ObjectUtils.isEmpty(userImage.getUser()) ? UserMapper.toDomain(userImage.getUser()) : null)
                .imageUrl(userImage.getImageUrl())
                .createdDate(userImage.getCreatedDate())
                .updateDate(userImage.getUpdateDate())
                .build();
    }

    public static UserImageEntity toEntity(UserImage userImage) {
        return UserImageEntity.builder()
                .id(userImage.getId())
                .user(!ObjectUtils.isEmpty(userImage.getUser()) ? UserMapper.toEntity(userImage.getUser()) : null)
                .imageUrl(userImage.getImageUrl())
                .createdDate(userImage.getCreatedDate())
                .updateDate(userImage.getUpdateDate())
                .build();
    }
}
