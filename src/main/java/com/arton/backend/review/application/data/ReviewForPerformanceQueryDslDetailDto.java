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
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewForPerformanceQueryDslDetailDto {
    private Long id;
    private Long userId;
    private String nickname;
    private float starScore;
    private LocalDateTime createdDate;
    private String content;
    private Long hit;
    private Long count;

    @Builder
    @QueryProjection
    public ReviewForPerformanceQueryDslDetailDto(Long id, Long userId, String nickname, float starScore, LocalDateTime createdDate, String content, Long hit, Long count) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.hit = hit;
        this.count = count;
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

    public ReviewForPerformanceDetailDto toDto() {
        return ReviewForPerformanceDetailDto.builder()
                .id(id)
                .userId(userId)
                .nickname(nickname)
                .starScore(starScore)
                .createdDate(createdDate != null ? getTextYearDay(createdDate) : "미정")
                .content(content)
                .hit(hit)
                .count(count)
                .build();
    }
}