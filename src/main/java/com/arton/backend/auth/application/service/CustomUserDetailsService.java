package com.arton.backend.auth.application.service;

import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import com.arton.backend.user.adapter.out.persistence.mapper.UserMapper;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(username)).map(UserMapper::toEntity).map(this::createUserDetails).orElseThrow(()-> new UsernameNotFoundException(username + " -> not exist!"));
    }

    /**
     * email이 만약 변경 된다면..?
     * 불변 보장되면 id 대신 email로 세팅해도 될듯
     * @param user
     * @return
     */
    private UserDetails createUserDetails(UserEntity user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuth().toString());
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),
                user.getPassword(),
                Collections.singleton(authority));
    }
}
