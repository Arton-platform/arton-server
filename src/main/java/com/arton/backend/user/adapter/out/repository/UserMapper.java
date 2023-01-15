package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimMapper;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity user) {
        return User.builder()
                .auth(user.getAuth())
                .signupType(user.getSignupType())
                .profileImageUrl(user.getProfileImageUrl())
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
                .performanceZzims(user.getPerformanceZzims() == null ? new ArrayList<>() : user.getPerformanceZzims().stream().map(PerformanceZzimMapper::toDomain).collect(Collectors.toList()))
                .artistZzims(user.getArtistZzims() == null ? new ArrayList<>() : user.getArtistZzims().stream().map(ArtistZzimMapper::toDomain).collect(Collectors.toList()))
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .auth(user.getAuth())
                .signupType(user.getSignupType())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .ageRange(user.getAgeRange())
                .gender(user.getGender())
                .email(user.getEmail())
                .id(user.getId())
                .naverId(user.getNaverId())
                .kakaoId(user.getKakaoId())
                .termsAgree(user.getTermsAgree())
                .performanceZzims(user.getPerformanceZzims() == null ? new ArrayList<>() : user.getPerformanceZzims().stream().map(PerformanceZzimMapper::toEntity).collect(Collectors.toList()))
                .artistZzims(user.getArtistZzims() == null ? new ArrayList<>() : user.getArtistZzims().stream().map(ArtistZzimMapper::toEntity).collect(Collectors.toList()))
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdateDate())
                .build();
    }

}
