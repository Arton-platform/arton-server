package com.arton.backend.follow.applicaion.data;

import com.arton.backend.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserShortDto {
    private Long id;
    private String nickname;
    private String selfDescription;
    // 팔로워 여부 추가
    private boolean isFollow;

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

    public void setFollow(boolean isFollow){
        this.isFollow = isFollow;
    }
}
