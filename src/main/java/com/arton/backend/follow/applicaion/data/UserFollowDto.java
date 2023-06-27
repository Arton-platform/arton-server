package com.arton.backend.follow.applicaion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.Dec2Bin;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "팔로우/팔로잉 DTO")
public class UserFollowDto {
    /** 현재 유저의 ID */
    @Schema(description = "유저 ID")
    private Long id;
    /** 현재 유저의 이미지 링크 */
    @Schema(description = "유저의 이미지 링크")
    private String imageUrl;
    /** 유저가 팔로잉 하는 사람의 수 */
    @Schema(description = "유저가 팔로잉 하는 사람의 수")
    private Long followings;
    /** 유저의 팔로워 수 */
    @Schema(description = "유저의 팔로워 수")
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
