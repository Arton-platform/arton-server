package com.arton.backend.withdrawal.domain;

import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 탈퇴 사유 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Withdrawal {
    private Long id;
    private User user;
    /** 탈퇴 사유 */
    private String comment;
    /** 탈퇴일 */
    private LocalDateTime createdDate;
    /** 변경일 */
    private LocalDateTime updateDate;

    @Builder
    public Withdrawal(Long id, User user, String comment, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
