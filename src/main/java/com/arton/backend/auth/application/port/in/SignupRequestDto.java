package com.arton.backend.auth.application.port.in;

import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

/**
 * 회원가입
 * gender MALE, FEMALE, ETC
 * ageRange 10, 20, 30, 40, 50
 * marketingAgreement Y, N
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    @NotBlank
    private String email;
    /** 패스워드 */
    @NotBlank
    private String password;
    /** 확인용 패스워드 */
    @NotBlank
    private String checkPassword;
    private String profileImageUrl;
    @NotBlank
    private String nickname;
    @NotBlank
    private String gender;
    private int ageRange;
    @NotBlank
    private String termsAgree;

    public static User toUser(SignupRequestDto signupRequestDto, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .profileImageUrl(signupRequestDto.getProfileImageUrl())
                .nickname(signupRequestDto.getNickname())
                .gender(Gender.get(signupRequestDto.getGender()))
                .ageRange(AgeRange.get(signupRequestDto.getAgeRange()/10))
                .termsAgree(signupRequestDto.getTermsAgree())
                .signupType(SignupType.ARTON)
                .build();
    }
}
