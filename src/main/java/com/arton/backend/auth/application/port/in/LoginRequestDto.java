package com.arton.backend.auth.application.port.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "로그인 요청 데이터")
public class LoginRequestDto {
    @NotBlank
    @Schema(description = "이메일", required = true)
    private String email;
    @NotBlank
    @Schema(description = "패스워드", required = true)
    private String password;
}
