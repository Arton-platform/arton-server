package com.arton.backend.user.application.data;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.performance.applicaiton.data.CommonPerformanceDto;
import com.arton.backend.performance.applicaiton.data.EndDateBasedPerformanceDto;
import com.arton.backend.performance.applicaiton.data.StartDateBasedPerformanceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "메인 페이지 제공 데이터")
public class MainPageDto {
    @Schema(description = "공연리스트(프론트에서 performanceType 필터 필요.)")
    private List<CommonPerformanceDto> performances = new ArrayList<>();
    @Schema(description = "유저의 찜한 리스트")
    private List<CommonPerformanceDto> zzims = new ArrayList<>();
    @Schema(description = "인기 공연")
    private List<CommonPerformanceDto> popular = new ArrayList<>();
    @Schema(description = "아티스트")
    private List<CommonArtistDto> artists = new ArrayList<>();
    @Schema(description = "곧 공연 시작")
    private List<StartDateBasedPerformanceDto> startingSoon = new ArrayList<>();
    @Schema(description = "곧 공연 종료")
    private List<EndDateBasedPerformanceDto> endingSoon = new ArrayList<>();

    @Builder
    public MainPageDto(List<CommonPerformanceDto> performances, List<CommonPerformanceDto> zzims, List<CommonPerformanceDto> popular, List<CommonArtistDto> artists, List<StartDateBasedPerformanceDto> startingSoon, List<EndDateBasedPerformanceDto> endingSoon) {
        this.performances = performances;
        this.zzims = zzims;
        this.popular = popular;
        this.artists = artists;
        this.startingSoon = startingSoon;
        this.endingSoon = endingSoon;
    }
}
