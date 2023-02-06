package com.arton.backend.follow.domain;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 팔로워 팔로잉은 그들만의 관계이므로 User와 엮지 않고 독립적으로 일단은 분리했습니다.
 * 더 좋은 방안이 있다면 해당 방안으로 해결하고 싶습니디.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Follow {
    /** 팔로워 즉 팔로잉을 하는 사람 */
    private Long fromUser;
    /** 팔로잉을 받는 사람 */
    private Long toUser;
    /** 추가일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;

    @Builder
    public Follow(Long fromUser, Long toUser, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
