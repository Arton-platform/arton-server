package com.arton.backend.follow.applicaion.port.out;

import com.arton.backend.follow.applicaion.port.in.UserFollowSearchDto;
import com.arton.backend.follow.domain.Follow;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.*;
import jdk.jfr.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FollowRepositoryPortTest {
    @Autowired
    FollowRepositoryPort followRepository;
    @Autowired
    UserRepositoryPort userRepository;
    @Autowired
    EntityManager em;

    @Transactional
    @Commit
    @Test
    void followCountTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email(i == 0 ? "j67310@gmail.com" : "tempaa"+i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        Long followersCount = followRepository.getFollowersCount(base.getId());
        Assertions.assertThat(followersCount).isEqualTo(userList.size()-1);
        List<User> followerList = followRepository.getFollowerList(base.getId());
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }

    @Description("최신순 정렬 - 정렬된 리스트 출력")
    @Transactional
    @Commit
    @Test
    void SortValidTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email("temp@" + i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        UserFollowSearchDto searchDto = UserFollowSearchDto.builder()
                .sort("createdDate")
                .build();
        List<User> followerList = followRepository.getFollowerList(base.getId(), searchDto);
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }

    @Description("잘못된 정렬 조건 - 정렬되지 않은 값 출력")
    @Transactional
    @Commit
    @Test
    void SortInvalidTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email("temp@" + i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        UserFollowSearchDto searchDto = UserFollowSearchDto.builder()
                .sort("createdDatess")
                .build();
        List<User> followerList = followRepository.getFollowerList(base.getId(), searchDto);
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }

    @Description("닉네임 - 닉네임 일치 검색")
    @Transactional
    @Commit
    @Test
    void findNickNameTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email("temp@" + i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        UserFollowSearchDto searchDto = UserFollowSearchDto.builder()
                .sort("createdDatess")
                .nickname("temp1")
                .build();
        List<User> followerList = followRepository.getFollowerList(base.getId(), searchDto);
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }

    @Description("닉네임 - 닉네임 불일치 검색")
    @Transactional
    @Commit
    @Test
    void findNickNameFailTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email("temp@" + i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        UserFollowSearchDto searchDto = UserFollowSearchDto.builder()
                .sort("createdDatess")
                .nickname("temp1aa")
                .build();
        List<User> followerList = followRepository.getFollowerList(base.getId(), searchDto);
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }


    @Description("without query parameter")
    @Transactional
    @Commit
    @Test
    void withoutQueryParamTest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .email(i == 0 ? "j67310@gmail.com" : "tempaa"+i)
                    .password("temp")
                    .nickname("temp" + i)
                    .gender(i % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .ageRange(AgeRange.Age10_19)
                    .termsAgree(i % 2 == 0 ? "Y" : "N")
                    .signupType(SignupType.ARTON)
                    .auth(UserRole.NORMAL)
                    .build();
            userList.add(user);
            userRepository.save(user);
        }

        User base = userRepository.findById(1L).get();
        for (User user : userRepository.findAll()) {
            if (user.getId().equals(base.getId())) {
                continue;
            }
            Follow build = Follow.builder()
                    .toUser(base.getId())
                    .fromUser(user.getId())
                    .build();
            followRepository.add(build);
        }

        // user 1의 팔로워를 구하자
        UserFollowSearchDto searchDto = UserFollowSearchDto.builder().build();
        List<User> followerList = followRepository.getFollowerList(base.getId(), searchDto);
        for (User user : followerList) {
            System.out.println("user = " + user);
        }
    }

}