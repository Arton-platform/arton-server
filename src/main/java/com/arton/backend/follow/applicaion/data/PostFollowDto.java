package com.arton.backend.follow.applicaion.data;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostFollowDto {
    private Long user;
}
