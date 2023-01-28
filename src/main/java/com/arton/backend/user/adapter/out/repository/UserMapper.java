package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.image.adapter.out.repository.UserImageMapper;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimMapper;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimMapper;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .performanceZzims(Optional.ofNullable(user.getPerformanceZzims()).orElseGet(Collections::emptyList).stream().map(PerformanceZzimMapper::toDomain).collect(Collectors.toList()))
                .artistZzims(Optional.ofNullable(user.getArtistZzims()).orElseGet(Collections::emptyList).stream().map(ArtistZzimMapper::toDomain).collect(Collectors.toList()))
                .selfDescription(Optional.ofNullable(user.getSelfDescription()).orElse(""))
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
                .performanceZzims(Optional.ofNullable(user.getPerformanceZzims()).orElseGet(Collections::emptyList).stream().map(PerformanceZzimMapper::toEntity).collect(Collectors.toList()))
                .artistZzims(Optional.ofNullable(user.getArtistZzims()).orElseGet(Collections::emptyList).stream().map(ArtistZzimMapper::toEntity).collect(Collectors.toList()))
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdateDate())
                .selfDescription(Optional.ofNullable(user.getSelfDescription()).orElse(""))
                .build();
    }

}
