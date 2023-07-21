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
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonReviewQueryDslDto {
    private Long id;
    private Long parentId;
    private Long performanceId;
    private Long userId;
    private String profileImageUrl;
    private String nickname;
    private String title;
    private float starScore;
    private LocalDateTime createdDate;
    private String content;
    private Set<String> images = new LinkedHashSet<>();
    private Long hit;
//    private Long reviewCount;

    @Builder
    @QueryProjection
    public CommonReviewQueryDslDto(Long id, Long parentId, Long performanceId, Long userId, String profileImageUrl, String nickname, String title, float starScore, LocalDateTime createdDate, String content, Set<String> images, Long hit) {
        this.id = id;
        this.parentId = parentId;
        this.performanceId = performanceId;
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.title = title;
        this.starScore = starScore;
        this.createdDate = createdDate;
        this.content = content;
        this.images = images;
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
                .parentId(parentId)
                .childs(new ArrayList<>())
                .performanceId(performanceId)
                .userId(userId)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .title(title)
                .starScore((int) starScore)
                .createdDate(createdDate != null ? getTextYearDay(createdDate) : "미정")
                .content(content)
                .images(ObjectUtils.isEmpty(images) ? new ArrayList<>() :  new ArrayList<>(images))
                .hit(hit)
                .reviewCount(0L)
                .build();
    }

    public CommonReviewDtoWithOutChilds toDtoWithOutChilds() {
        return CommonReviewDtoWithOutChilds.builder()
                .id(id)
                .parentId(parentId)
                .performanceId(performanceId)
                .userId(userId)
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .title(title)
                .starScore((int) starScore)
                .createdDate(createdDate != null ? getTextYearDay(createdDate) : "미정")
                .content(content)
                .images(ObjectUtils.isEmpty(images) ? new ArrayList<>() : new ArrayList<>(images))
                .hit(hit)
                .reviewCount(0L)
                .build();
    }
}