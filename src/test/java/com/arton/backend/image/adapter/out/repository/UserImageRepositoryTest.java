package com.arton.backend.image.adapter.out.repository;

import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class UserImageRepositoryTest {
    @Autowired
    UserImageRepository userImageRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Commit
    @Test
    void imageSaveTest() {
        UserImageEntity userImageEntity = UserImageEntity.builder()
                .imageUrl("default")
                .build();
        userImageRepository.save(userImageEntity);  // 자식 먼저 저장
        UserEntity tester = UserEntity.builder()
                .email("tester")
                .build();
        tester.setImage(userImageEntity);
        userRepository.save(tester);
        UserEntity userEntity = userRepository.findByEmail("tester").get();
        userRepository.findById(userEntity.getId());
    }
}