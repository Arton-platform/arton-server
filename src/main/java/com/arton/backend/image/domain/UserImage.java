package com.arton.backend.image.domain;

import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user"})
public class UserImage {
    private Long id;
    private String imageUrl;
    private User user;
    /** 등록일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;

    @Builder
    public UserImage(Long id, String imageUrl, User user, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.user = user;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
