package com.arton.backend.test;


import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import com.arton.backend.artist.adapter.out.persistence.repository.ArtistRepository;
import com.arton.backend.follow.adapter.out.persistence.entity.FollowEntity;
import com.arton.backend.follow.adapter.out.persistence.repository.FollowRepository;
import com.arton.backend.image.adapter.out.persistence.entity.UserImageEntity;
import com.arton.backend.image.adapter.out.persistence.repository.UserImageRepository;
import com.arton.backend.performance.adapter.out.persistence.entity.PerformanceEntity;

import com.arton.backend.performance.adapter.out.persistence.repository.PerformanceRepository;
import com.arton.backend.performance.domain.PerformanceType;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;
import com.arton.backend.review.adapter.out.persistence.ReviewRepository;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import com.arton.backend.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 테스트 데이터 삽입용
 */
@RequiredArgsConstructor
public class TestDataInit {
    private final ArtistRepository artistRepository;
    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ReviewRepository reviewRepository;
    private final UserImageRepository userImageRepository;
    private final PasswordEncoder passwordEncoder;
    private String defaultImage = "image/profiles/default.png";

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Random random = new Random();
        List<ArtistEntity> artistList = new ArrayList<>();
        List<PerformanceEntity> performances = new ArrayList<>();
        List<ReviewEntity> reviews = new ArrayList<>();
        // test user data setting
        // user
        List<UserEntity> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserEntity user = UserEntity.builder()
                    .email(i == 0 ? "j6731000@gmail.com" : "tempaa"+i)
                    .password(passwordEncoder.encode("temp"))
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .userStatus(true)
                    .build();
            userList.add(user);
        }
        List<UserEntity> userEntities = userRepository.saveAll(userList);
        for (UserEntity userEntity : userEntities) {
            UserImageEntity build = UserImageEntity.builder().imageUrl(defaultImage).user(userEntity).build();
            userImageRepository.save(build);
        }

        UserEntity base = userRepository.findById(1L).get();
        for (UserEntity user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            FollowEntity build = FollowEntity.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            FollowEntity build2 = FollowEntity.builder()
                    .toUser(user.getId())
                    .fromUser(base.getId())
                    .build();
            followRepository.save(build);
            followRepository.save(build2);
        }
        String[] titles = {"이석훈", "이석훈 2021 연말 콘서트", "이석훈&SG워너비 동행", "김민수 연말콘서트"};
        String[] places = {"전주", "전라북도 전주", "서울", "서울 특별시"};
        String[] musicalDates = {"2022.02.13~2022.02.15", "2023.02.01~2023.02.11"};
        int[] ages = {8, 15, 19};
        LocalDateTime[] times = {LocalDateTime.now(), LocalDateTime.now().minusDays(5L), LocalDateTime.now().minusYears(1L), LocalDateTime.now().plusDays(2L)};
        for (int i = 0; i < 1000; i++) {
            ArtistEntity artist = ArtistEntity.builder()
                    .age(random.nextInt(30) + i)
                    .name("test" + i)
                    .profileImageUrl(defaultImage)
                    .snsId("abc" + i + "@naver.com")
                    .performances(new ArrayList<>())
                    .build();
            artistList.add(artist);

            PerformanceEntity performance = PerformanceEntity.builder()
                    .performanceType(i % 2 == 0 ? PerformanceType.MUSICAL : PerformanceType.CONCERT)
                    .description("감동의 콘서트로 초대합니다.")
                    .title(titles[random.nextInt(4)])
                    .performers(new ArrayList<>())
                    .priceGradeList(new ArrayList<>())
                    .place(places[random.nextInt(4)])
                    .startDate(times[random.nextInt(4)])
                    .ticketOpenDate(times[random.nextInt(4)].minusDays(3L))
                    .ticketEndDate(times[random.nextInt(4)].minusDays(3L))
                    .hit((long)random.nextInt(1000)+1)
                    .musicalDateTime(musicalDates[random.nextInt(2)])
                    .purchaseLimit(3)
                    .limitAge(ages[random.nextInt(3)])
                    .build();
            performances.add(performance);

            reviews.add(ReviewEntity.builder().performance(performance).user(base).content("재밌어요 ㅋㅋ").starScore(4f).build());
        }

        artistRepository.saveAll(artistList);
        performanceRepository.saveAll(performances);
        reviewRepository.saveAll(reviews);
    }
}
