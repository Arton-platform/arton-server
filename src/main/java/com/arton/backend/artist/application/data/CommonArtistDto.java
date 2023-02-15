package com.arton.backend.artist.application.data;

import com.arton.backend.artist.domain.Artist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "메인 페이지에 공통으로 사용할 아티스트 Dto")
public class CommonArtistDto {
    @Schema(description = "아티스트 ID")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    public static CommonArtistDto domainToDto(Artist artist) {
        return new CommonArtistDto(artist.getId(), artist.getName(), artist.getProfileImageUrl());
    }
}
