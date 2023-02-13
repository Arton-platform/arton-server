package com.arton.backend.search.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "실시간 키워드 정보 V2")
public class RealTimeKeywordDtoV2 {
    @Schema(description = "키워드")
    private String keyword;
    @Schema(description = "특정 시간 동안 검색 횟수 증가 비율")
    private Double score;

    @Builder
    public RealTimeKeywordDtoV2(String keyword, Double score) {
        this.keyword = keyword;
        this.score = score;
    }
}
