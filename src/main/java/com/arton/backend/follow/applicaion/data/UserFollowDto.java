package com.arton.backend.follow.applicaion.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFollowDto {
    /** 현재 유저의 ID */
    private Long id;
    /** 현재 유저의 이미지 링크 */
    private String imageUrl;
    /** 유저가 팔로잉 하는 사람의 수 */
    private Long followings;
    /** 유저의 팔로워 수 */
    private Long followers;
    /** 팔로잉 혹은 팔로워 */
    private List<UserShortDto> users = new ArrayList<>();

    @Builder
    public UserFollowDto(Long id, String imageUrl, Long followings, Long followers, List<UserShortDto> users) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.followings = followings;
        this.followers = followers;
        this.users = users;
    }
}
