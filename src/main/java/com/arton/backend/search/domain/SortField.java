package com.arton.backend.search.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasText;

@Schema(description = "검색 정렬 조건입니다. 내림차순 정렬하여 결과를 반환합니다.")
public enum SortField {
    STARTDATE("startDate"), ENDDATE("endDate"), ID("id");

    private String value;

    SortField(String value) {
        this.value = value;
    }

    public static SortField get(String name){
        return hasText(name)?SORT_FIELD_MAP.get(name):null;
    }

    private final static Map<String, SortField> SORT_FIELD_MAP;

    static{
        Map<String, SortField> map = new ConcurrentHashMap<>();
        for (SortField sortField : SortField.values()) {
            map.put(sortField.value, sortField);
        }
        SORT_FIELD_MAP = Collections.unmodifiableMap(map);
    }
}
