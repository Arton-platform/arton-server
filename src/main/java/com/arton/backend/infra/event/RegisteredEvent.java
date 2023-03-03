package com.arton.backend.infra.event;

import com.arton.backend.user.domain.User;
import lombok.Data;

@Data
public class RegisteredEvent {
    private User user;
}
