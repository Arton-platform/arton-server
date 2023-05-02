package com.arton.backend.administer.admin.application.data;

import com.arton.backend.user.domain.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminSignupDTO {
    @NotBlank
    private String email;
    /** 패스워드 */
    @NotBlank
    private String password;
    /** 확인용 패스워드 */
    @NotBlank
    private String checkPassword;
    @NotBlank
    private String nickname;
    @NotBlank
    private String gender;
    private int ageRange;
    private String termsAgree;

    public static User toUser(AdminSignupDTO signupRequestDto, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .gender(Gender.get(signupRequestDto.getGender()))
                .ageRange(AgeRange.get(signupRequestDto.getAgeRange()/10))
                .termsAgree(signupRequestDto.getTermsAgree())
                .signupType(SignupType.ARTON)
                .auth(UserRole.ADMIN)
                .userStatus(true)
                .build();
    }

}
