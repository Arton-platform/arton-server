package com.arton.backend.search.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RealTimeKeywordDto {
    private String keyword;
    private Long count;

    @Builder
    public RealTimeKeywordDto(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    /**
     * bucket의 집계 결과를 실시간 결과로 매핑시켜 보내준다.
     * @param bucket
     * @return
     */
    public static RealTimeKeywordDto bucketToDTO(MultiBucketsAggregation.Bucket bucket) {
        return RealTimeKeywordDto.builder()
                .keyword(bucket.getKeyAsString())
                .count(bucket.getDocCount())
                .build();
    }

}
