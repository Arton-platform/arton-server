package com.arton.backend.zzim.domain;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class ArtistZzim {
    private Long id;
    private Artist artist;
    private User user;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setUser(User user) {
        this.user = user;
    }
}
