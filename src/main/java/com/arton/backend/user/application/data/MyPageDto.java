package com.arton.backend.user.application.data;

import com.arton.backend.review.application.data.CommonReviewDtoWithOutChilds;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageDto {
    /** 현재 유저의 ID */
    private Long id;
    private String nickname;
    private String selfDescription;
    private String profileImageUrl;
    /** 유저가 팔로잉 하는 사람의 수 */
    private Long followings;
    /** 유저의 팔로워 수 */
    private Long followers;
    private Boolean isFollow;
    /** 리뷰 갯수 */
    private Long reviewCount;
    /** 유저의 리뷰 목록 */
    private List<CommonReviewDtoWithOutChilds> reviews = new ArrayList<>();

    @Builder
    public MyPageDto(Long id, String nickname, String selfDescription, String profileImageUrl, Long followings, Long followers, Boolean isFollow, Long reviewCount, List<CommonReviewDtoWithOutChilds> reviews) {
        this.id = id;
        this.nickname = nickname;
        this.selfDescription = selfDescription;
        this.profileImageUrl = profileImageUrl;
        this.followings = followings;
        this.followers = followers;
        this.isFollow = isFollow;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }
}
