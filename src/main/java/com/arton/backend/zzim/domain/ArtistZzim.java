package com.arton.backend.zzim.domain;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.user.adapter.out.repository.UserEntity;
import com.arton.backend.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class ArtistZzim {
    private Long id;
    private ArtistEntity artist;
    private UserEntity user;
}
