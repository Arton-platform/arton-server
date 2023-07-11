package com.arton.backend.aop;

import com.arton.backend.infra.shared.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class GlobalExceptionLog {

    @AfterReturning(pointcut = "execution(* com.arton.backend.infra.shared.exception.CustomExceptionHandler.*(..))", returning = "result")
    public void log(JoinPoint joinPoint, Object result) {

        ErrorResponse errorResponse;

        if (result instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) result;
            errorResponse = ErrorResponse.class.cast(responseEntity.getBody());
        } else if (result instanceof ErrorResponse) {
            ErrorResponse er = (ErrorResponse) result;
            errorResponse = er;
        }else {
            log.error("Exception log - result casting error");
            return;
        }
        HttpStatus httpStatus = HttpStatus.valueOf(errorResponse.getStatus());
        if (httpStatus.is5xxServerError() || httpStatus.is4xxClientError()) {
            Object[] args = joinPoint.getArgs();

            Arrays.stream(args)
                    .filter(Objects::nonNull)
                    .filter(this::isExceptionClass)
                    .findFirst()
                    .ifPresent(this::printStackTrace);
        }

    }

    private boolean isExceptionClass(Object object) {
        Class<?> aClass = object.getClass();
        while (aClass != null) {
            aClass = aClass.getSuperclass();
            if (aClass.getSuperclass().isAssignableFrom(RuntimeException.class)) {
                return true;
            }
        }
        return false;
    }

    private void printStackTrace(Object object) {
        Arrays.stream(object.getClass().getMethods())
                .filter(method -> method.getName().equals("printStackTrace"))
                .findFirst()
                .ifPresent(method -> {
                    try {
                        log.error(object.getClass().getName(), object);
                    } catch (Exception e) {
                        log.error(e.getClass().getName(), e);
                    }
                });
    }
}
