package com.arton.backend.user.adapter.out.repository;

import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        return userRepository.findByEmailAndSignupType(email, SignupType.ARTON);
    }

    @Override
    public Optional<User> findByKakaoId(Long id) {
        return userRepository.findByKakaoId(id);
    }

    @Override
    public Optional<User> findByNaverId(String id) {
        return userRepository.findByNaverId(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean checkEmailDup(String email) {
        return userRepository.existsByEmailAndSignupType(email, SignupType.ARTON);
    }

    @Override
    public Optional<User> findUserForReset(String nickname, String email) {
        return userRepository.findByNicknameAndEmailAndSignupType(nickname, email, SignupType.ARTON);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
