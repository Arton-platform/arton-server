package com.arton.backend.aop;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class PerformanceReviewAop {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final RedissonClient redisClient;
    private final static Logger log = LoggerFactory.getLogger(PerformanceReviewAop.class);

    @AfterReturning(pointcut = "execution(* com.arton.backend.review.application.service.ReviewService.regist(..)) || execution(* com.arton.backend.review.application.service.ReviewService.delete(..))", returning = "performanceId")
    public void log(JoinPoint joinPoint, Long performanceId) {
        log.info("Performance {} ReviewAop start", performanceId);
        RLock lock = redisClient.getLock("performance_" + performanceId);
        try {
            if (!lock.tryLock(10, 1, TimeUnit.SECONDS)) {
                log.error("Performance startScore update {} lock time out!!", performanceId);
            }
            performanceRepositoryPort.updatePerformanceStartScore(performanceId);
        } catch (InterruptedException e) {
            throw new CustomException(ErrorCode.LOCK_TIMEOUT.getMessage(), ErrorCode.LOCK_TIMEOUT);
        }finally {
            log.info("Performance {} ReviewAop end", performanceId);
            lock.unlock();
        }
        performanceRepositoryPort.updatePerformanceStartScore(performanceId);
    }
}
