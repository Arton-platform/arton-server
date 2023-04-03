package com.arton.backend.performance.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static org.springframework.util.StringUtils.hasText;

public enum ShowCategory {
    AD("광고"),
    NORMAL("일반"),
    BANNED("신고");

    private String value;

    public String getValue() {
        return value;
    }

    ShowCategory(String value) {
        this.value = value;
    }

    private static final Map<String, ShowCategory> SHOW_CATEGORY_MAP;

    static {
        Map<String, ShowCategory> map = new ConcurrentHashMap<>();
        for (ShowCategory showCategory : ShowCategory.values()) {
            map.put(showCategory.getValue(), showCategory);
        }
        SHOW_CATEGORY_MAP = unmodifiableMap(map);
    }

    /**
     * 매칭되는 타입이 없으면 null 반환.
     * @param type
     * @return
     */
    public static ShowCategory get(String type) {
        return hasText(type) ?SHOW_CATEGORY_MAP.get(type):null;
    }
}
