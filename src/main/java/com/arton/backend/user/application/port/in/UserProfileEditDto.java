package com.arton.backend.user.application.port.in;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileEditDto {
    @NotBlank
    private String nickname;
    private String selfDescription;
}
