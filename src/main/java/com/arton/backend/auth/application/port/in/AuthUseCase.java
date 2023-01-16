package com.arton.backend.auth.application.port.in;

import com.arton.backend.infra.mail.MailDto;
import org.springframework.web.multipart.MultipartFile;

public interface AuthUseCase {
    // 회원가입 양식, 이미지 파일
    boolean signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile);
    boolean logout(LogoutRequestDto logoutRequestDto);
    boolean validateSignupRequest(SignupValidationDto signupValidationDto);
    TokenDto login(LoginRequestDto loginRequestDto);
    MailDto resetPassword(PasswordResetDto passwordResetDto);
}
