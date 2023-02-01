package com.arton.backend.auth.application.port.in;

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
    @Schema(description = "KAKAO_ID/NAVER_ID", required = true)
    private String id;
    @NotBlank
    @Schema(description = "닉네임", required = true)
    private String nickname;
    @Schema(description = "이메일", required = true)
    private String email;
    @Schema(description = "AGE_RANGE 값", required = true)
    private String age;
    @Schema(description = "성별", required = true)
    private String gender;
}
