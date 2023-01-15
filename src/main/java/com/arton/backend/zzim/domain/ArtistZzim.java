package com.arton.backend.zzim.domain;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ArtistZzim{
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

    @Builder
    public ArtistZzim(Long id, Artist artist, User user, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.artist = artist;
        this.user = user;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
