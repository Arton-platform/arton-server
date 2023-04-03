package com.arton.backend.follow.domain;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

public enum SortCondition {
    CREATEDDATE("최신");

    private String value;

    SortCondition(String value) {
        this.value = value;
    }

    private static final Map<String, SortCondition> SORT_CONDITION_MAP;

    static {
        Map<String, SortCondition> map = new ConcurrentHashMap<>();
        for (SortCondition value : SortCondition.values()) {
            map.put(value.name(), value);
        }
        SORT_CONDITION_MAP = Collections.unmodifiableMap(map);
    }

    public static SortCondition get(String name){
        return hasText(name)?SORT_CONDITION_MAP.get(name):null;
    }
}
