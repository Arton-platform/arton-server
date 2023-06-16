package com.arton.backend.artist.application.data;

import com.arton.backend.artist.domain.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "메인 페이지에 공통으로 사용할 아티스트 Dto")
public class CommonArtistDto {
    @Schema(description = "아티스트 ID")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "이미지 링크")
    private String imageUrl;

    @Builder
    @QueryProjection
    public CommonArtistDto(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static CommonArtistDto domainToDto(Artist artist) {
        return new CommonArtistDto(artist.getId(), artist.getName(), artist.getProfileImageUrl());
    }

    @JsonIgnore
    public boolean isCompleted() {
        return !ObjectUtils.isEmpty(name) && !ObjectUtils.isEmpty(imageUrl) && !ObjectUtils.isEmpty(id);
    }

}
