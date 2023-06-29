package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;
import com.arton.backend.performance.adapter.out.persistence.repository.PerformanceRepository;
import com.arton.backend.performance.adapter.out.persistence.repository.PerformanceFeedRepository;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.UserRole;
import com.arton.backend.zzim.adapter.out.persistence.entity.PerformanceZzimEntity;
import com.arton.backend.zzim.adapter.out.persistence.repository.PerformanceZzimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@Transactional
class PerformanceFeedServiceTest {

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PerformanceZzimRepository performanceZzimRepository;

    @Autowired
    PerformanceFeedRepository feedRepository;

    @Autowired
    private PerformanceFeedService service;

    @BeforeEach
    void createPerformance(){
        PerformanceEntity performanceEntity = PerformanceEntity.builder()
                .performanceType(PerformanceType.CONCERT)
                .description("performance" + UUID.randomUUID())
                .title("title" + UUID.randomUUID())
//                .ticketOpenDate(LocalDateTime.now().plusDays(3))
                .startDate(LocalDateTime.now().plusDays(5))
                .endDate(LocalDateTime.now().plusDays(10))
                .build();

        performanceRepository.save(performanceEntity);
    }

    @BeforeEach
    void createUser(){
        UserEntity user = UserEntity.builder()
                .gender(Gender.MALE)
                .auth(UserRole.ROLE_NORMAL)
                .nickname(UUID.randomUUID().toString())
                .email("email@" + UUID.randomUUID())
                .ageRange(AgeRange.Age30_39)
                .password(UUID.randomUUID().toString())
                .build();

        userRepository.save(user);
    }

    @BeforeEach
    void createPerformanceZzim() {
        PerformanceZzimEntity performanceZzimEntity = PerformanceZzimEntity.builder()
                .user(userRepository.findAll().get(new Random().nextInt(3)))
                .performance(performanceRepository.findAll().get(new Random().nextInt(3)))
                .build();

        performanceZzimRepository.save(performanceZzimEntity);
    }

    @Test
    void getFeedFromZzim() {
        service.getFeedFromZzim().stream().forEach(System.out::println);
    }

    @Test
    void saveAll() {
        service.saveAll(service.getFeedFromZzim());
        feedRepository.findAll().stream().forEach(System.out::println);
    }
}