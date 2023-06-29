package com.arton.backend.review.application.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceQueryDslDetailDto {
    private Long id;
    private Long performanceId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String title;
    private float starScore;
    private LocalDateTime createdDate;
    private String content;
//    private List<String> images = new ArrayList<>();
    private Long hit;
//    private Long reviewCount;

    @Builder
    @QueryProjection
    public ReviewForPerformanceQueryDslDetailDto(Long id, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, float starScore, LocalDateTime createdDate, String content, Long hit) {
        this.id = id;
        this.performanceId = performanceId;
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.hit = hit;
    }

    @JsonIgnore
    public boolean isCompleted() {
        return !ObjectUtils.isEmpty(id);
    }
    private String getTextYearDay(LocalDateTime time) {
        String day = null;
        if (!ObjectUtils.isEmpty(time)) {
            String[] days = getSplitDate(time);
            day =  days[0];
        }
        return day;
    }

    private String[] getSplitDate(LocalDateTime time) {
        String day[] = {};
        if (!ObjectUtils.isEmpty(time)) {
            day =  Optional.ofNullable(time).orElseGet(null).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")).split(" ");
        }
        return day;
    }

    public CommonReviewDto toDto() {
        return CommonReviewDto.builder()
                .id(id)
                .performanceId(performanceId)
                .userId(userId)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .title(title)
                .starScore(starScore)
                .createdDate(createdDate != null ? getTextYearDay(createdDate) : "미정")
                .content(content)
                .images(new ArrayList<>())
                .hit(hit)
                .reviewCount(0L)
                .build();
    }
}