package com.arton.backend.follow.domain;

public enum FeedType {
    LIKE_MY_REVIEW("님이 나의 리뷰에 좋아요를 눌렸습니다."),
    COMMENT_MY_REVIEW("님이 나의 리뷰에 댓글을 등록 했습니다."),
    FOLLOW_REVIEW_REGIST("님이 리뷰를 등록 했습니다."),
    FOLLOW_COMMENT_REGIST("님이 댓글을 등록 했습니다."),
    FOLLOW_ME("님이 나를 팔로우 했습니다.")
    ;

    private String type;

    FeedType(String type) {
        this.type = type;
    }
}
