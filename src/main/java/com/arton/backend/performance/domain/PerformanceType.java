package com.arton.backend.performance.domain;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static org.springframework.util.StringUtils.hasText;

public enum PerformanceType {
    MUSICAL("뮤지컬"),
    CONCERT("콘서트");

    private String value;

    PerformanceType(String value) {
        this.value = value;
    }

    private static final Map<String, PerformanceType> PERFORMANCE_TYPE_MAP;

    static {
        Map<String, PerformanceType> map = new ConcurrentHashMap<>();
        for (PerformanceType performanceType : PerformanceType.values()) {
            map.put(performanceType.name(), performanceType);
        }
        PERFORMANCE_TYPE_MAP = unmodifiableMap(map);
    }

    /**
     * 매칭되는 타입이 없으면 null 반환.
     * @param type
     * @return
     */
    public static PerformanceType get(String type) {
        return hasText(type.toUpperCase(Locale.ROOT))?PERFORMANCE_TYPE_MAP.get(type):null;
    }
}
