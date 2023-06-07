package com.arton.backend.artist.application.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 찜하기에 보여주는 용도
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyRegistDTO {
    /**
     * 아티스트 이름
     */
    @NotBlank
    private String names;

    @Builder
    public SpotifyRegistDTO(String names) {
        this.names = names;
    }
}
