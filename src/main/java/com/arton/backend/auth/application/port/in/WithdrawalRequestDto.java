package com.arton.backend.auth.application.port.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class WithdrawalRequestDto {
    @NotBlank
    private String withdrawalId;
    private String comment;
}
