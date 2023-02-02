package com.arton.backend.auth.application.data;

import com.arton.backend.user.domain.*;
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
public class SignupValidationDto {
    @NotBlank
    private String email;
    /** 패스워드 */
    @NotBlank
    private String password;
    /** 확인용 패스워드 */
    @NotBlank
    private String checkPassword;

}
