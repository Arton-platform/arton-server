package com.arton.backend.auth.application.port.in;

import lombok.*;

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
    private String nickname;
    private String gender;
    private int ageRange;
    private String marketingAgreement;
}
