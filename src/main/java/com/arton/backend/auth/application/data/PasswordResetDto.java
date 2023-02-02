package com.arton.backend.auth.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "패스워드 초기화 요청 데이터")
public class PasswordResetDto {
    @NotBlank
    @Schema(description = "닉네임", required = true)
    private String nickname;
    @NotBlank
    @Schema(description = "이메일", required = true)
    private String email;
}
