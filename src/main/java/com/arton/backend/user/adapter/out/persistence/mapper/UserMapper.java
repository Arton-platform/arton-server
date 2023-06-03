package com.arton.backend.user.adapter.out.persistence.mapper;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
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
                .platformId(user.getPlatformId())
                .termsAgree(user.getTermsAgree())
                .createdDate(user.getCreatedDate())
                .updateDate(user.getUpdatedDate())
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
                .platformId(user.getPlatformId())
                .termsAgree(user.getTermsAgree())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .selfDescription(Optional.ofNullable(user.getSelfDescription()).orElse(""))
                .userStatus(user.getUserStatus())
                .build();
    }

    public static UserDocument toDocumentFromDomain(User user) {
        return UserDocument
                .builder()
                .id(user.getId())
                .createdDate(user.getCreatedDate())
                .nickname(user.getNickname())
                .termsAgree(user.getTermsAgree())
                .email(user.getEmail())
                .build();
    }

}
