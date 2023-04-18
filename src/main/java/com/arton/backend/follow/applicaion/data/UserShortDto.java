package com.arton.backend.follow.applicaion.data;

import com.arton.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserShortDto {
    private Long id;
    private String nickname;
    private String selfDescription;
    // 팔로워 여부 추가
    @JsonProperty(value = "isFollow")
    private boolean isFollow;

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
