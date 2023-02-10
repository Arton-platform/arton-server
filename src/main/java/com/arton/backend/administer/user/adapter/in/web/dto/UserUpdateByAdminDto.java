package com.arton.backend.administer.user.adapter.in.web.dto;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserUpdateByAdminDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Gender gender;
    private AgeRange ageRange;
    private UserRole auth;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .gender(this.gender)
                .ageRange(this.ageRange)
                .auth(this.auth)
                .build();
    }
}
