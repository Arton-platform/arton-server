package com.arton.backend.infra.event.aop.register;

import com.arton.backend.infra.event.aop.EventValue;
import com.arton.backend.user.domain.User;
import lombok.Getter;

import javax.annotation.Nonnull;

public class AopUserWithdrewEvent implements EventValue<User> {
    @Getter
    private User value;

    public AopUserWithdrewEvent(@Nonnull User user) {
        this.value = user;
    }
}
