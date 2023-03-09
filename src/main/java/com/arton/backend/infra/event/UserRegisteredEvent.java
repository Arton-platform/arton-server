package com.arton.backend.infra.event;

import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 이벤트
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRegisteredEvent {
    private User user;
}
