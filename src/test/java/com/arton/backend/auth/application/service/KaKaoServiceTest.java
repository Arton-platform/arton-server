package com.arton.backend.auth.application.service;

import com.arton.backend.image.domain.UserImage;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KaKaoServiceTest {
    @Autowired
    UserRepositoryPort userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MockMvc mockMvc;

    @Test
    void asyncTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch (4);

        AtomicBoolean result1 = new AtomicBoolean(true);
        AtomicBoolean result2 = new AtomicBoolean(true);
        AtomicBoolean result3 = new AtomicBoolean(true);
        AtomicBoolean result4 = new AtomicBoolean(true);

        executorService.execute(() -> {
            result1.set(signup());
            latch.countDown();
        });
        executorService.execute(() -> {
            result2.set(signup());
            latch.countDown();
        });
        executorService.execute(() -> {
            result3.set(signup());
            latch.countDown();
        });
        executorService.execute(() -> {
            result4.set(signup());
            latch.countDown();
        });

    }

    private synchronized boolean signup() {
        long id = 11L;
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            user = User.builder().email("aa")
                    .gender(Gender.MALE)
                    .password(passwordEncoder.encode("aaaa"))
                    .kakaoId(id)
                    .nickname("aaa")
                    .ageRange(AgeRange.get(30))
                    .auth(UserRole.ROLE_NORMAL)
                    .signupType(SignupType.KAKAO)
                    .userStatus(true)
                    .build();
            user = userRepository.save(user);
        }
        return true;
    }
}