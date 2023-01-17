package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.arton.backend.user.adapter.out.repository.UserMapper.toDomain;
import static com.arton.backend.user.adapter.out.repository.UserMapper.toEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserRepository userRepository;
    /**
     * 카카오 네이버와 구분시켜야됨.
     * @param email
     * @return
     */
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> response = userRepository.findByEmailAndSignupType(email, SignupType.ARTON);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<User> findByKakaoId(Long id) {
        Optional<UserEntity> response = userRepository.findByKakaoId(id);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<User> findByNaverId(String id) {
        Optional<UserEntity> response = userRepository.findByNaverId(id); if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> response = userRepository.findById(id);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean checkEmailDup(String email) {
        return userRepository.existsByEmailAndSignupType(email, SignupType.ARTON);
    }

    @Override
    public Optional<User> findUserForReset(String nickname, String email) {
        Optional<UserEntity> response = userRepository.findByNicknameAndEmailAndSignupType(nickname, email, SignupType.ARTON);
        if (response.isPresent()) {
            return Optional.ofNullable(toDomain(response.get()));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public User save(User user) {
        return toDomain(userRepository.save(toEntity(user)));
    }
}
