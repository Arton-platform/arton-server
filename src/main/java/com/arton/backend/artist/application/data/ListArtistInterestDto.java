package com.arton.backend.artist.application.data;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ListArtistInterestDto {
    private List<ArtistInterestDto> artists = new ArrayList<>();
}