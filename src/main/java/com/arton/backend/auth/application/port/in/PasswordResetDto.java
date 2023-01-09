package com.arton.backend.auth.application.port.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PasswordResetDto {
    private String nickname;
    private String email;
}
