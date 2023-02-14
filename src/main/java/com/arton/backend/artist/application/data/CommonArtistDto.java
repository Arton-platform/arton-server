package com.arton.backend.artist.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "메인 페이지에 공통으로 사용할 아티스트 Dto")
public class CommonArtistDto {
    @Schema(description = "아티스트 ID")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    @Builder
    public CommonArtistDto(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
