package com.arton.backend.user.application.data;

import com.arton.backend.review.application.port.in.MyPageReviewDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
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
    /** 리뷰 갯수 */
    private Long reviewCount;
    /** 유저의 리뷰 목록 */
    private List<MyPageReviewDto> reviews = new ArrayList<>();
}
