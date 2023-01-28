package com.arton.backend.auth.application.service;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.auth.application.port.in.*;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.file.FileUploadLocal;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.file.MD5Generator;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.mail.MailDto;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
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

/**
 * OnetoOne 자식 먼저 저장. 즉 유저 먼저 저장하고 이를 유저이미지에 사용
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final ArtistRepositoryPort artistRepository;
    private final PerformanceRepositoryPort performanceRepository;
    private final ZzimRepositoryPort zzimRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final FileUploadUtils fileUploadUtils;
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
        User savedUser = userRepository.save(user);
        Long id = savedUser.getId();
        // 기본 이미지 지정후 image 저장
        UserImage userImage = UserImage.builder().imageUrl(fileUploadUtils.getDefaultImageUrl()).user(savedUser).build();
        userImage = userImageSaveRepository.save(userImage);

        // 프로필 이미지 있다면 이미지 업데이트
        if (multipartFile != null) {
            String upload = fileUploadUtils.upload(multipartFile, "arton/image/profiles/" + id);
            userImage.updateImage(upload);
            userImageSaveRepository.save(userImage);
        }
        // 아티스트 찜하기
        List<Long> artistIds = signupRequestDto.getArtists();
        if (!artistIds.isEmpty()) {
            List<Artist> artists = artistRepository.findByIds(artistIds);
            if (artists!=null) {
                List<ArtistZzim> zzims = new ArrayList<>();
                for (Artist artist : artists) {
                    ArtistZzim artistZzim = ArtistZzim.builder().artist(artist.getId()).user(savedUser.getId()).build();
                    artistZzim.setUser(savedUser.getId());
                    zzims.add(artistZzim);
                }
                zzimRepository.saveArtists(zzims);
            }
        }
        // 공연 찜하기
        List<Long> performanceIds = signupRequestDto.getPerformances();
        if (!performanceIds.isEmpty()) {
            List<Performance> performances = performanceRepository.findByIds(performanceIds);
            if (performances!=null) {
                List<PerformanceZzim> zzims = new ArrayList<>();
                for (Performance performance : performances) {
                    PerformanceZzim performanceZzim = PerformanceZzim.builder().performanceId(performance.getPerformanceId()).userId(savedUser.getId()).build();
                    performanceZzim.setUser(savedUser.getId());
                    zzims.add(performanceZzim);
                }
                zzimRepository.savePerformances(zzims);
            }
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


    /**
     * 리프레쉬 토큰의 유효값을 체크하여 로그인 체크를 하자
     * 유효하면 메인
     * 유효하지 않다면 로그인 페이지로
     * @param tokenReissueDto
     * @return
     */
    @Override
    public TokenDto reissue(TokenReissueDto tokenReissueDto) {
        // 유효하지 않음 알림
        if (!tokenProvider.validateToken(tokenReissueDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID);
        }
        // 인증 정보
        Authentication authentication = tokenProvider.getAuthenticationByRefreshToken(tokenReissueDto.getRefreshToken());
        // 캐시에서 토큰 가져옴
        String refreshToken = (String) redisTemplate.opsForValue().get(refreshTokenPrefix + authentication.getName());
        // 일치여부 확인
        if (refreshToken == null || !refreshToken.equals(tokenReissueDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID);
        }
        // 리프레쉬 토큰 만료기간 확인
        long diffMinutes = tokenProvider.getExpiration(refreshToken) / 1000 / 60;
        TokenDto tokenDto = null;
        if (diffMinutes < 5) { // 5분 미만이면 리프레쉬 토큰도 재발급
            // 리프레쉬 토큰 만료면 리프레쉬 토큰도 같이 발급 후 업데이트
            tokenDto = tokenProvider.generateToken(authentication);
            redisTemplate.opsForValue().set(refreshTokenPrefix + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        } else {
            // 만료가 아니라면 액세스 토큰만 발급후 다시 현재 refresh token 저장후 발급.
            tokenDto = tokenProvider.generateAccessToken(authentication, refreshToken);
        }
        return tokenDto;
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
