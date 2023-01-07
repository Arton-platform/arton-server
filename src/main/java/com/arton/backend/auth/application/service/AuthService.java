package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.port.in.AuthUseCase;
import com.arton.backend.auth.application.port.in.LoginRequestDto;
import com.arton.backend.auth.application.port.in.LoginResponseDto;
import com.arton.backend.auth.application.port.in.SignupRequestDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.file.MD5Generator;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Value("${default.image}")
    private String defaultImage;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;

    /**
     * 회원가입
     * 이메일 중복, 패스워드 체크는 다음을 누르면 체크 API를 호출해서 처리해주자
     * @param signupRequestDto
     * @param multipartFile
     */
    @Override
    public boolean signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile) {
        if (checkEmailDup(signupRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_IS_EXIST.getMessage(), ErrorCode.EMAIL_IS_EXIST);
        }
        if (checkPassword(signupRequestDto.getPassword(), signupRequestDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }
        // 기본 이미지
        signupRequestDto.setProfileImageUrl(defaultImage);
        // 저장
        User user = SignupRequestDto.toUser(signupRequestDto, passwordEncoder);
        User savedUser = userRepository.save(user);

        Long id = savedUser.getId();
        // 프로필 이미지 업로드
        if (multipartFile != null) {
            String filename = multipartFile.getOriginalFilename();
            String format = filename.substring(filename.lastIndexOf(".") + 1); // 포맷
            String newFileName = new MD5Generator(filename).toString() + "." + format; // 이미지 이름 해싱
            FileUploadUtils.saveFile("/image/profiles/"+id, newFileName, multipartFile); // 업로드
            savedUser.setProfileImageUrl("/" + id + "/" + newFileName);
        }

        return true;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }

    public boolean checkEmailDup(String email){
        return userRepository.checkEmailDup(email);
    }

    public boolean checkPassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }
}
