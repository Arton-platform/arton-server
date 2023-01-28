package com.arton.backend.image.domain;

import com.arton.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserImage {
    private Long id;

    private String imageUrl;

    private User user;
}
