package com.arton.backend.artist.application.data;

import com.arton.backend.artist.domain.Artist;
import lombok.*;

/**
 * 찜하기에 보여주는 용도
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistInterestDto {
    /** 아티스트 id */
    private Long id;
    /** 아티스트 프로필 이미지 링크 */
    private String profileImageUrl;
    /** 아티스트 이름 */
    private String name;

    @Builder
    public ArtistInterestDto(Long id, String profileImageUrl, String name) {
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.name = name;
    }

    public static ArtistInterestDto of(Artist artist) {
        return ArtistInterestDto.builder()
                .id(artist.getId())
                .profileImageUrl(artist.getProfileImageUrl())
                .name(artist.getName())
                .build();
    }
}
