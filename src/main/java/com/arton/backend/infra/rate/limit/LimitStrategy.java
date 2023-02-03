package com.arton.backend.infra.rate.limit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

public enum LimitStrategy {

    // 1일 10번만 가능
    FREE{
        public Bandwidth getLimit() {
            return Bandwidth.classic(10, Refill.intervally(10, Duration.ofDays(1)));
        }
    };

    public abstract Bandwidth getLimit();

    private static final Map<String, LimitStrategy> LIMIT_STRATEGY_MAP;

    static{
        Map<String, LimitStrategy> map = new ConcurrentHashMap<>();
        for (LimitStrategy limitStrategy : LimitStrategy.values()) {
            map.put(limitStrategy.name(), limitStrategy);
        }
        LIMIT_STRATEGY_MAP = Collections.unmodifiableMap(map);
    }

    public static LimitStrategy get(String name){
        return hasText(name)?LIMIT_STRATEGY_MAP.get(name.toUpperCase(Locale.ROOT)):null;
    }
}
