package com.arton.backend.administer.user.adapter.in.web.dto;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserRegisterByAdminDto {
    private String email;
    private String password;
    private String nickname;
    private Gender gender;
    private AgeRange ageRange;
    private UserRole auth;
    private SignupType signupType;
    public UserEntity toEntity() {
        return UserEntity.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .gender(this.gender)
                .ageRange(this.ageRange)
                .auth(this.auth)
                .signupType(this.signupType)
                .build();
    }
}
