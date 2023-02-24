package com.arton.backend.administer.search.application.port.data;

import com.arton.backend.search.application.data.RealTimeKeywordDtoV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminKeywordDto {
    @Schema(description = "순위")
    private int rank;
    @Schema(description = "키워드")
    private String keyword;
    @Schema(description = "특정 시간 동안 검색 횟수 증가 비율")
    private Double score;

    @Builder
    public AdminKeywordDto(int rank, String keyword, Double score) {
        this.rank = rank;
        this.keyword = keyword;
        this.score = score;
    }

    public static AdminKeywordDto toAdminDto(RealTimeKeywordDtoV2 realTimeKeywordDtoV2) {
        return AdminKeywordDto.builder()
                .keyword(realTimeKeywordDtoV2.getKeyword())
                .rank(realTimeKeywordDtoV2.getRank())
                .score(realTimeKeywordDtoV2.getScore())
                .build();
    }
}
