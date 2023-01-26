package com.arton.backend.follow.applicaion.port.in;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserFollowDto {
    /** 현재 유저의 ID */
    private Long id;
    /** 유저가 팔로잉 하는 사람의 수 */
    private Long followings;
    /** 유저의 팔로워 수 */
    private Long followers;
    /** 팔로잉 혹은 팔로워 */
    private List<UserShortDto> users = new ArrayList<>();
}
