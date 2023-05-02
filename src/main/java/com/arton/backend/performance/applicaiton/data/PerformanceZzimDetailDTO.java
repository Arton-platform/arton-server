package com.arton.backend.performance.applicaiton.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원가입시 공연 찜을 할 데이터 DTO")
public class PerformanceZzimDetailDTO {
    @Schema(description = "뮤지컬 리스트")
    private List<PerformanceInterestDto> musicals = new ArrayList<>();
    @Schema(description = "콘서트 리스트")
    private List<PerformanceInterestDto> concerts = new ArrayList<>();

    @Builder
    public PerformanceZzimDetailDTO(List<PerformanceInterestDto> musicals, List<PerformanceInterestDto> concerts) {
        this.musicals = musicals;
        this.concerts = concerts;
    }
}
