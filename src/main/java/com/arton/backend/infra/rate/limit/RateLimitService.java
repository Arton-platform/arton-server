package com.arton.backend.infra.rate.limit;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final Map<String, Bucket> limitStrategyMap = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key) {
        return limitStrategyMap.computeIfAbsent(key, this::newBucket);
    }

    private Bucket newBucket(String key) {
        LimitStrategy limitStrategy = LimitStrategy.get(key);
        return Bucket.builder()
                .addLimit(limitStrategy.getLimit())
                .build();
    }


}
