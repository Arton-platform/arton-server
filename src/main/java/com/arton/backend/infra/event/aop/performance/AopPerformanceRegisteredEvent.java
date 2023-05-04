package com.arton.backend.infra.event.aop.performance;

import com.arton.backend.administer.performance.application.data.PerformanceAdminCreateResponseDto;
import com.arton.backend.infra.event.aop.EventValue;
import com.arton.backend.user.domain.User;
import lombok.Getter;

import javax.annotation.Nonnull;

public class AopPerformanceRegisteredEvent implements EventValue<PerformanceAdminCreateResponseDto> {
    @Getter
    private PerformanceAdminCreateResponseDto value;

    public AopPerformanceRegisteredEvent(@Nonnull PerformanceAdminCreateResponseDto result) {
        this.value = result;
    }
}
