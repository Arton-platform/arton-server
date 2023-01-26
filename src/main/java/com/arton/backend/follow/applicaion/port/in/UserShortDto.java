package com.arton.backend.follow.applicaion.port.in;

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

    public static UserShortDto to(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .selfDescription(user.getSelfDescription())
                .build();
    }
}
