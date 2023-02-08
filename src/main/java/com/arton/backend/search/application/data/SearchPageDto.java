package com.arton.backend.search.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchPageDto {
    private String basedTime;
    private List<RealTimeKeywordDto> keywords = new ArrayList<>();

    @Builder
    public SearchPageDto(String basedTime, List<RealTimeKeywordDto> keywords) {
        this.basedTime = basedTime;
        this.keywords = keywords;
    }
}
