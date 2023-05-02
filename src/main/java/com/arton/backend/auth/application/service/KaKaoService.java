package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.data.OAuthSignupDto;
import com.arton.backend.auth.application.data.TokenDto;
import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.StringUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KaKaoService implements KaKaoUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.redirect.url}")
    private String redirectURL;
    @Value("${spring.default-image}")
    private String defaultImage;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;


    /**
     * token 발행
     * email, password 로 만들거임
     * 여기서 설정하는 값이 userdetails의 id password로 넘어감
     * 원래는 평문 password 여야 하지만 간편로그인 경우 password 입력이 없으므로.. 유일한 식별값으로 대체
     * 기존 인가코드 받아 토큰을 생성했지만 프론트에서 한번에 액세스 토큰 발급이 가능하므로
     * accessToken 받아서 진행으로 변경하자.
     * @return
     */
    @Override
    @Transactional
    public synchronized TokenDto login(HttpServletRequest request, OAuthSignupDto signupDto) {
        String accessToken = Optional.ofNullable(tokenProvider.parseBearerToken(request)).orElseThrow(() -> new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID));
        JsonNode userInfo = getUserInfo(accessToken);
        if (!userInfo.get("id").asText().equals(signupDto.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
        }
        User register = signup(signupDto);
        // Generate ArtOn JWT
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(register.getId(), String.valueOf(register.getKakaoId()));
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    /**
     * 동의한 데이터 가져오기
     *
     * @param accessToken
     * @return
     */
    private JsonNode getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class);

        String responseBody = response.getBody();
        log.info("responseBody for userInfo {}", responseBody);
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 카카오로 회원가입된 계정이 있으면 해당 유저 정보 return
     * 아니면 회원가입 진행 + return
     * kakao ageRange example
     * 0~9, 10~14, 15~19, 20~29, ...
     * @param signupDto
     * @return
     */
    private User signup(OAuthSignupDto signupDto) {
        long id = Long.parseLong(signupDto.getId());
        User user = userRepository.findByKakaoId(id).orElse(null);
        if (user == null) {
            /** password is user's own kakao id */
            String password = signupDto.getId();
            user = User.builder()
                    .email(hasText(signupDto.getEmail()) ? signupDto.getEmail() : "")
                    .gender(hasText(signupDto.getGender()) ? Gender.get(signupDto.getGender().toUpperCase(Locale.ROOT)) : Gender.ETC)
                    .password(passwordEncoder.encode(password))
                    .kakaoId(id)
                    .nickname(hasText(signupDto.getNickname()) ? signupDto.getNickname() : "")
                    .ageRange(hasText(signupDto.getAge()) ? AgeRange.get(Integer.parseInt(signupDto.getAge().substring(0, 1))) : AgeRange.ETC)
                    .auth(UserRole.ROLE_NORMAL)
                    .signupType(SignupType.KAKAO)
                    .userStatus(true)
                    .termsAgree("Y")
                    .build();
            user = userRepository.save(user);
            UserImage userImage = UserImage.builder().imageUrl(defaultImage).user(user).build();
            userImageSaveRepository.save(userImage);
        }
        return userRepository.findByKakaoId(id).orElseThrow(()->new CustomException(ErrorCode.KAKAO_SIMPLE_LOGIN_ERROR.getMessage(), ErrorCode.KAKAO_SIMPLE_LOGIN_ERROR));
    }


}
