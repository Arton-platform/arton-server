package com.arton.backend.auth.application.service;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.adapter.out.repository.ArtistMapper;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.auth.application.port.in.*;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.file.MD5Generator;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.mail.MailDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceMapper;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserMapper;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.adapter.out.repository.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimEntity;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final ArtistRepositoryPort artistRepository;
    private final PerformanceRepositoryPort performanceRepository;
    private final ZzimRepositoryPort zzimRepository;
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
     * 우선 로컬에서 작업중이므로 안에서 처리
     * @param signupRequestDto
     * @param multipartFile
     */
    @Override
    public boolean signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile) {
        if (checkEmailDup(signupRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_IS_EXIST.getMessage(), ErrorCode.EMAIL_IS_EXIST);
        }
        if (!checkPassword(signupRequestDto.getPassword(), signupRequestDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }
        // 회원가입
        User user = SignupRequestDto.toUser(signupRequestDto, passwordEncoder);
        // 기본 이미지 지정
        user.setProfileImageUrl(defaultImage);
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

        // zzim artist
        List<Long> artistIds = signupRequestDto.getArtists();
        List<Artist> artists = artistRepository.findByIds(artistIds);
        if (artists!=null) {
            List<ArtistZzim> zzims = new ArrayList<>();
            for (Artist artist : artists) {
                ArtistZzim artistZzim = ArtistZzim.builder().artist(artist).user(savedUser).build();
                artistZzim.setUser(savedUser);
                zzims.add(artistZzim);
            }
            zzimRepository.saveArtists(zzims);
        }
        // zzim performance
        List<Long> performanceIds = signupRequestDto.getPerformances();
        List<Performance> performances = performanceRepository.findByIds(performanceIds);
        if (performances!=null) {
            List<PerformanceZzim> zzims = new ArrayList<>();
            for (Performance performance : performances) {
                PerformanceZzim performanceZzim = PerformanceZzim.builder().performance(performance).user(savedUser).build();
                performanceZzim.setUser(savedUser);
                zzims.add(performanceZzim);
            }
            zzimRepository.savePerformances(zzims);
        }

        userRepository.save(savedUser);
        return true;
    }

    @Override
    public boolean logout(LogoutRequestDto logoutRequestDto) {
        // token 검증
        if (!tokenProvider.validateToken(logoutRequestDto.getAccessToken())) {
            throw new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID);
        }
        // get user id
        Authentication authentication = tokenProvider.getAuthentication(logoutRequestDto.getAccessToken());
        // 유저 토큰 확인후 존재하면 삭제
        if (redisTemplate.opsForValue().get(refreshTokenPrefix + authentication.getName()) != null) {
            redisTemplate.delete(refreshTokenPrefix+authentication.getName());
        }
        // 해당 토큰 블랙리스트 저장
        Long expiration = tokenProvider.getExpiration(logoutRequestDto.getAccessToken());
        redisTemplate.opsForValue().set(logoutRequestDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public boolean validateSignupRequest(SignupValidationDto signupValidationDto) {
        if (checkEmailDup(signupValidationDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_IS_EXIST.getMessage(), ErrorCode.EMAIL_IS_EXIST);
        }
        if (!checkPassword(signupValidationDto.getPassword(), signupValidationDto.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH.getMessage(), ErrorCode.PASSWORD_NOT_MATCH);
        }
        return true;
    }

    @Override
    public TokenDto reissue(TokenReissueDto tokenReissueDto) {
        return null;
    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        // 패스워드, 이메일 일치여부 확인
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        // password 불일치
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_INFO_NOT_MATCHED.getMessage(), ErrorCode.LOGIN_INFO_NOT_MATCHED);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), loginRequestDto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    /**
     * 비밀번호 재발급
     * @param passwordResetDto
     * @return
     */
    @Override
    public MailDto resetPassword(PasswordResetDto passwordResetDto) {
        // 해당 정보의 유저가 존재하는지 확인
        User user = userRepository.findUserForReset(passwordResetDto.getNickname(), passwordResetDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        // 비밀번호 변경
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        // 메일 정보 전송
        return MailDto.builder()
                .messageBody(newPassword)
                .receiver(user.getEmail())
                .subject("ArtOn - 임시 비밀번호 발급")
                .build();
    }

    public boolean checkEmailDup(String email){
        return userRepository.checkEmailDup(email);
    }

    public boolean checkPassword(String password, String checkPassword){
        return password.equals(checkPassword);
    }
}
