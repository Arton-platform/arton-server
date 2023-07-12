package com.arton.backend.aop;

import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PerformanceReviewAop {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final static Logger log = LoggerFactory.getLogger(PerformanceReviewAop.class);

    @AfterReturning(pointcut = "execution(* com.arton.backend.review.application.service.ReviewService.regist(..)) || execution(* com.arton.backend.review.application.service.ReviewService.delete(..))", returning = "result")
    public void log(JoinPoint joinPoint, Object result) {
        log.info("PerformanceReviewAop start");
        if (result instanceof Long) {
            Long performanceId = (long) result;
            performanceRepositoryPort.updatePerformanceStartScore(performanceId);
        }else{
            log.error("PerformanceId Casting Fail...");
            return;
        }
        log.info("PerformanceReviewAop end");
    }
}
