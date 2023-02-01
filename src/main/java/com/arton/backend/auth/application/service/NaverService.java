package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.port.in.NaverUseCase;
import com.arton.backend.auth.application.port.in.OAuthSignupDto;
import com.arton.backend.auth.application.port.in.TokenDto;
import com.arton.backend.image.application.port.out.UserImageSaveRepositoryPort;
import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.*;
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

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NaverService implements NaverUseCase {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepositoryPort userRepository;
    private final UserImageSaveRepositoryPort userImageSaveRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Value("${naver.client.id}")
    private String clientId;
    @Value("${naver.client.secret}")
    private String clientSecret;
    @Value("${naver.redirect.url}")
    private String redirectURL;
    @Value("${spring.default-image}")
    private String defaultImage;
    @Value("${refresh.token.prefix}")
    private String refreshTokenPrefix;
    /**
     *  token 발행
     *  email, password 로 만들거임
     *  여기서 설정하는 값이 userdetails의 id password로 넘어감
     *  원래는 평문 password 여야 하지만 간편로그인 경우 password 입력이 없으므로.. 유일한 식별값으로 대체
     *
     * @return
     */
    @Override
    public TokenDto login(OAuthSignupDto oAuthSignupDto) {
        User register = signup(oAuthSignupDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(register.getId(), register.getNaverId());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateToken(authenticate);
        redisTemplate.opsForValue().set(refreshTokenPrefix+authenticate.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        return tokenDto;
    }

    private User signup(OAuthSignupDto oAuthSignupDto) {
        String id = oAuthSignupDto.getId();
        User user = userRepository.findByNaverId(id).orElse(null);
        if (user == null) {

            String nickName = oAuthSignupDto.getNickname();
            String email = oAuthSignupDto.getEmail();
            String ageRange = oAuthSignupDto.getAge();
            int age = Integer.parseInt(ageRange.substring(0, 1));
            String gender = oAuthSignupDto.getGender();
            /** password is user's own kakao id */
            String password = id;
            user = User.builder().email(email)
                    .gender(getGender(gender))
                    .password(passwordEncoder.encode(password))
                    .naverId(id)
                    .nickname(nickName)
                    .ageRange(AgeRange.get(age))
                    .auth(UserRole.NORMAL)
                    .signupType(SignupType.NAVER)
                    .userStatus(true)
                    .build();
            user = userRepository.save(user);
            UserImage userImage = UserImage.builder().imageUrl(defaultImage).user(user).build();
            userImageSaveRepository.save(userImage);
        }
        return userRepository.findByNaverId(id).orElseThrow(() -> new CustomException(ErrorCode.NAVER_SIMPLE_LOGIN_ERROR.getMessage(), ErrorCode.NAVER_SIMPLE_LOGIN_ERROR));
    }

    private Gender getGender(String gender){
        if (gender.equals("M") || gender.equals("m"))
            return Gender.MALE;
        else if(gender.equals("F") || gender.equals("f"))
            return Gender.FEMALE;
        return Gender.ETC;
    }


}
