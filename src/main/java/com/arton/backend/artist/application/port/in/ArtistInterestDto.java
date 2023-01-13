package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.domain.Artist;
import lombok.*;

/**
 * 찜하기에 보여주는 용도
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArtistInterestDto {
    /** 아티스트 id */
    private Long id;
    /** 아티스트 프로필 이미지 링크 */
    private String profileImageUrl;
    /** 아티스트 이름 */
    private String name;

    public static ArtistInterestDto of(Artist artist) {
        return ArtistInterestDto.builder()
                .id(artist.getId())
                .profileImageUrl(artist.getProfileImageUrl())
                .name(artist.getName())
                .build();
    }
}
