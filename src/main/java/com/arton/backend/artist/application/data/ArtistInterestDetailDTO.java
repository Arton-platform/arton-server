package com.arton.backend.artist.application.data;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistInterestDetailDTO {
    private List<ArtistInterestDto> musicals = new ArrayList<>();
    private List<ArtistInterestDto> concerts = new ArrayList<>();

    @Builder
    public ArtistInterestDetailDTO(List<ArtistInterestDto> musicals, List<ArtistInterestDto> concerts) {
        this.musicals = musicals;
        this.concerts = concerts;
    }
}
