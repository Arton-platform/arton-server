package com.arton.backend.auth.application.port.in;

import com.arton.backend.auth.application.data.*;
import com.arton.backend.infra.mail.MailDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface AuthUseCase {
    // 회원가입 양식, 이미지 파일
    boolean signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile);
    boolean logout(HttpServletRequest request);
    boolean validateSignupRequest(SignupValidationDto signupValidationDto);
    boolean withdraw(HttpServletRequest request, WithdrawalRequestDto withdrawalRequestDto);
    TokenDto reissue(TokenReissueDto tokenReissueDto);
    TokenDto login(LoginRequestDto loginRequestDto);
    MailDto resetPassword(PasswordResetDto passwordResetDto);
}
