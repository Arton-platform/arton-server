package com.arton.backend.zzim.application.port.in;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.user.domain.User;
import com.arton.backend.zzim.domain.ArtistZzim;
import lombok.*;

/**
 * artist, user를 artistZzim으로 반환
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArtistZzimDto {
    private ArtistEntity artist;
    private User user;

    public static ArtistZzim of(ArtistEntity artist, User user) {
        return ArtistZzim.builder()
                .artist(artist)
                .user(user)
                .build();
    }
}
