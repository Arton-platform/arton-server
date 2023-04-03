package com.arton.backend.search.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "실시간 검색 결과")
public class SearchPageDtoV2 {
    @Schema(description = "검색 요청이 온 시간입니다.")
    private String basedTime;
    @Schema(description = "실시간 키워드")
    private List<RealTimeKeywordDtoV2> keywords = new ArrayList<>();

    @Builder
    public SearchPageDtoV2(String basedTime, List<RealTimeKeywordDtoV2> keywords) {
        this.basedTime = basedTime;
        this.keywords = keywords;
    }
}
