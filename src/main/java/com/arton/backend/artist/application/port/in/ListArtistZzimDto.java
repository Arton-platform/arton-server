package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.domain.Artist;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListArtistZzimDto {
    private List<ArtistZzimDto> artists = new ArrayList<>();
}
