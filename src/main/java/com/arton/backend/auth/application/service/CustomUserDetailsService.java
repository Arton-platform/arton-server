package com.arton.backend.auth.application.service;

import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(this::createUserDetails).orElseThrow(()-> new UsernameNotFoundException(username + " -> not exist!"));
    }

    /**
     * email이 만약 변경 된다면..?
     * 불변 보장되면 email로 하자
     * @param user
     * @return
     */
    private UserDetails createUserDetails(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuth().toString());
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),
                user.getPassword(),
                Collections.singleton(authority));
    }
}
