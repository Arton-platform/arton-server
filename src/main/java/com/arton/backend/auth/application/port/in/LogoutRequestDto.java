package com.arton.backend.auth.application.port.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LogoutRequestDto {
    private String accessToken;
}
