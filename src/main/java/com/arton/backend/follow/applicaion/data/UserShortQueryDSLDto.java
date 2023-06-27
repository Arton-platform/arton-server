package com.arton.backend.follow.applicaion.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserShortQueryDSLDto {
    private Long id;
    private String nickname;
    private String selfDescription;
    private String imageUrl;

    @Builder
    @QueryProjection
    public UserShortQueryDSLDto(Long id, String nickname, String selfDescription,String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.selfDescription = selfDescription;
        this.imageUrl = imageUrl;
    }
}
