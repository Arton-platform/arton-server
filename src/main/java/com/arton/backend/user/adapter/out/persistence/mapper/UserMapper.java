package com.arton.backend.user.adapter.out.persistence.mapper;

import com.arton.backend.image.adapter.out.persistence.entity.UserImageEntity;
import com.arton.backend.image.adapter.out.persistence.mapper.UserImageMapper;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.domain.User;

import java.util.Optional;

public class UserMapper {

    public static User toDomain(UserEntity user) {
        return User.builder()
                .auth(user.getAuth())
                .signupType(user.getSignupType())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .ageRange(user.getAgeRange())
                .gender(user.getGender())
                .email(user.getEmail())
                .id(user.getId())
                .naverId(user.getNaverId())
                .kakaoId(user.getKakaoId())
                .termsAgree(user.getTermsAgree())
                .createdDate(user.getCreatedDate())
                .updateDate(user.getUpdateDate())
                .selfDescription(Optional.ofNullable(user.getSelfDescription()).orElse(""))
                .userStatus(user.getUserStatus())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .auth(user.getAuth())
                .signupType(user.getSignupType())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .ageRange(user.getAgeRange())
                .gender(user.getGender())
                .email(user.getEmail())
                .id(user.getId())
                .naverId(user.getNaverId())
                .kakaoId(user.getKakaoId())
                .termsAgree(user.getTermsAgree())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdateDate())
                .selfDescription(Optional.ofNullable(user.getSelfDescription()).orElse(""))
                .userStatus(user.getUserStatus())
                .build();
    }

}
