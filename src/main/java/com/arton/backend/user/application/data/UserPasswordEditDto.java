package com.arton.backend.user.application.data;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordEditDto {
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;
}
