package com.arton.backend.user.adapter.out.persistence.repository;

import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.user.adapter.out.persistence.mapper.UserMapper.toDomain;
import static com.arton.backend.user.adapter.out.persistence.mapper.UserMapper.toEntity;

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
        return userRepository.getValidUserByEmailAndSignupType(email, SignupType.ARTON).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByPlatformId(String id, SignupType signupType) {
        return userRepository.findByPlatformIdAndSignupType(id, signupType).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public boolean checkEmailDup(String email) {
        return userRepository.checkEmailDup(email);
    }

    @Override
    public Optional<User> findUserForReset(String nickname, String email) {
        return userRepository.getUserForResetPassword(nickname, email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return toDomain(userRepository.save(toEntity(user)));
    }

    @Override
    public List<User> findAll() {
        return Optional.ofNullable(userRepository.findAll()).orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
}
