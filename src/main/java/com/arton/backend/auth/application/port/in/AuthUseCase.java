package com.arton.backend.auth.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface AuthUseCase {
    // 회원가입 양식, 이미지 파일
    boolean signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
