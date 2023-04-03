package com.arton.backend.zzim.application.data;

import com.arton.backend.zzim.domain.ArtistZzim;
import lombok.Builder;
import lombok.Data;

@Data
public class ArtistZzimResponseDto {
    private Long artistId;
    private Long userId;

    @Builder
    public ArtistZzimResponseDto(Long artistId, Long userId) {
        this.artistId = artistId;
        this.userId = userId;
    }

    public static ArtistZzimResponseDto toDto(ArtistZzim artistZzim) {
        return ArtistZzimResponseDto.builder()
                .artistId(artistZzim.getArtistId())
                .userId(artistZzim.getUserId())
                .build();
    }
}
