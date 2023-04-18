package com.arton.backend.zzim.application.data;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.zzim.domain.ArtistZzim;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "아티스트 찜 목록에 반환할 Dto")
public class ArtistZzimResponseDtoV2 {
    @Schema(description = "아티스트 ID")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    public static ArtistZzimResponseDtoV2 domainToDto(Artist artist) {
        return new ArtistZzimResponseDtoV2(artist.getId(), artist.getName(), artist.getProfileImageUrl());
    }

}
