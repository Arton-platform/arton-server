package com.arton.backend.infra.event;

import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 탈퇴 이벤트
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserWithdrewEvent {
    private User user;
}
