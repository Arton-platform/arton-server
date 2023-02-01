package com.arton.backend.auth.application.port.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Schema(description = "회원탈퇴 요청 데이터")
public class WithdrawalRequestDto {
    @NotBlank
    @Schema(description = "유저 ID")
    private String withdrawalId;
    @Schema(description = "탈퇴 사유")
    private String comment;
}
