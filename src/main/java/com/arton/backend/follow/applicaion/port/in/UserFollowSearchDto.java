package com.arton.backend.follow.applicaion.port.in;

import lombok.*;

/**
 * 팔로잉, 팔로워 리스트 보여주기에 사용될 조건.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserFollowSearchDto {
    private String nickname;
    private String sort;
}
