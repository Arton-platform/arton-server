package com.arton.backend.infra.event.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // life cycle Runtime 까지
@Target(ElementType.METHOD) // method level 붙이는 annotation
public @interface PublishEvent {
    /**
     * return이 비어있는 경우 new eventType()
     * params가 비어있는 경우 new eventType(returnValue)
     * params가 문자열인 경우 new event
     */
    Class<? extends EventValue> eventType();

    // 빈값, 문자열, SpEL('#{표현식}')을 사용할 수 있음
    String params() default "";
}
