package com.arton.backend.follow.applicaion.data;

import com.arton.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "팔로우 또는 팔로워 정보")
public class UserShortDto {
    @Schema(description = "유저 ID")
    private Long id;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "자기소개")
    private String selfDescription;
    // 팔로워 여부 추가
    @JsonProperty(value = "isFollow")
    @Schema(description = "팔로워 여부")
    private boolean isFollow;
    @Schema(description = "이미지 url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public void setFollow(boolean isFollow){
        this.isFollow = isFollow;
    }

    public static UserShortDto to(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .selfDescription(user.getSelfDescription())
                .build();
    }

    public static UserShortDto to(User user, boolean isFollow) {
        return UserShortDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .selfDescription(user.getSelfDescription())
                .isFollow(isFollow)
                .build();
    }
}
