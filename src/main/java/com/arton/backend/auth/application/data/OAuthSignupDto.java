package com.arton.backend.auth.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "간편 회원가입 요청 데이터")
public class OAuthSignupDto {
    @NotBlank
    @Schema(description = "SNS_Type 입니다.\n 0: Kakao\n 1: Naver\n 2: Apple", required = true)
    private String loginType;
    @NotBlank
    @Schema(description = "KAKAO_ID/NAVER_ID/APPLE_SUB_ID", required = true)
    private String id;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "이메일", required = true)
    private String email;
    @Schema(description = "AGE_RANGE 값")
    private String age;
    @Schema(description = "성별")
    private String gender;
}
