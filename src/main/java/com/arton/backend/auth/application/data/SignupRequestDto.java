package com.arton.backend.auth.application.data;

import com.arton.backend.user.domain.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원가입
 * gender MALE, FEMALE, ETC
 * ageRange 10, 20, 30, 40, 50
 * marketingAgreement Y, N
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {
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
    /** 공연 찜 */
    private List<Long> performances = new ArrayList<>();
    /** 아티스트 찜 */
    private List<Long> artists = new ArrayList<>();

    @Builder
    public SignupRequestDto(String email, String password, String checkPassword, String nickname, String gender, int ageRange, String termsAgree, List<Long> performances, List<Long> artists) {
        this.email = email;
        this.password = password;
        this.checkPassword = checkPassword;
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.termsAgree = termsAgree;
        this.performances = performances;
        this.artists = artists;
    }

    public static User toUser(SignupRequestDto signupRequestDto, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .gender(Gender.get(signupRequestDto.getGender()))
                .ageRange(AgeRange.get(signupRequestDto.getAgeRange()/10))
                .termsAgree(signupRequestDto.getTermsAgree())
                .signupType(SignupType.ARTON)
                .auth(UserRole.ROLE_NORMAL)
                .userStatus(true)
                .build();
    }
}
